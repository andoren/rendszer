package hu.uni.eszterhazy.beadando.controller;

import hu.uni.eszterhazy.beadando.model.Manucafterer;
import hu.uni.eszterhazy.beadando.model.Mobile;
import hu.uni.eszterhazy.beadando.service.MobileService;
import hu.uni.eszterhazy.beadando.service.exceptions.InvalidYearRangeException;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileAlreadyExistsException;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.Collection;


@RestController
class MobileController {
    MobileService mobileService;

    public MobileController(@Autowired MobileService mobileService) {
        this.mobileService = mobileService;
    }
    @RequestMapping(value="/")
    //ha ezt kihagyuk akkor mvc.
    public String hello(){
        return "Pekár Mihály TYH60S";
    }

    @RequestMapping(value="/mobileNumbers")
    public int getMobileNumbersInStorage() throws IOException {
        return mobileService.GetAllMobile().size();
    }

    @RequestMapping(value="/getMobiles")
    public Collection<Mobile> getAllMobiles() throws IOException {
        return mobileService.GetAllMobile();
    }

    @RequestMapping(value="/addMobile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addMobile(@RequestBody Mobile mobile) throws MobileNotFoundException, MobileAlreadyExistsException, IOException {
        mobileService.AddMobile(mobile);
        return "New mobile has been added: "+mobile.getImei();
    }

    @RequestMapping(value="/getMobile/{imei}")
    public Mobile gettMobileByImei(@PathVariable String imei) throws MobileNotFoundException, IOException {
        return mobileService.GetMobileByImei(imei);
    }

    @RequestMapping(value="/updateMobile",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mobile updateMobile(@RequestBody Mobile mobile) throws IOException, MobileNotFoundException {
        mobileService.UpdateMobile(mobile);
        return mobileService.GetMobileByImei(mobile.getImei());
    }

    @RequestMapping(value="/deleteMobile/{imei}", method = RequestMethod.DELETE)
    public String deleteMobile(@PathVariable String imei) throws IOException, MobileNotFoundException {
        mobileService.DeleteMobile(imei);
        return "The following mobile phone with this imei: "+imei+" has been deleted";
    }

    @RequestMapping(value="/getMobilesBetweenYears/{fromYear}-{toYear}")
    public Collection<Mobile> getMobilesBetweenYears(@PathVariable int fromYear, @PathVariable int toYear) throws IOException, InvalidYearRangeException {
        return mobileService.GetMobilesBetweenYears(fromYear,toYear);
    }

    @RequestMapping(value="getMobilesByManufacterer/{manufacterer}")
    public Collection<Mobile> getMobilesByManufacterer(@PathVariable Manucafterer manufacterer) throws IOException,IllegalArgumentException, MethodArgumentTypeMismatchException {
        return mobileService.GetMobilesByManufacturer(manufacterer);
    }

}
