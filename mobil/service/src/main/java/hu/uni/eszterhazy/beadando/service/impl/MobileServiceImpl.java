package hu.uni.eszterhazy.beadando.service.impl;

import hu.uni.eszterhazy.beadando.dao.MobileDao;
import hu.uni.eszterhazy.beadando.model.Manucafterer;
import hu.uni.eszterhazy.beadando.model.Mobile;
import hu.uni.eszterhazy.beadando.service.MobileService;
import hu.uni.eszterhazy.beadando.service.exceptions.InvalidYearRangeException;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileAlreadyExistsException;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileNotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class MobileServiceImpl implements MobileService {

    public MobileServiceImpl(MobileDao dao) {
        this.dao = dao;
    }

    private MobileDao dao;
    public Collection<Mobile> GetAllMobile() throws IOException {
        return dao.ReadMobiles();
    }

    public Mobile GetMobileByImei(String imei) throws MobileNotFoundException, IOException {
        return dao.ReadMobileByImei(imei);
    }

    public void AddMobile(Mobile mobile) throws MobileAlreadyExistsException, IOException, MobileNotFoundException {
        dao.AddMobile(mobile);
    }

    public void UpdateMobile(Mobile mobile) throws MobileNotFoundException, IOException {
        dao.UpdateMobile(mobile);
    }

    public void DeleteMobile(String imei) throws MobileNotFoundException, IOException {
        dao.DeleteMobile(imei);
    }

    public Collection<Mobile> GetMobilesByManufacturer(Manucafterer manucafterer) throws IOException {
        Collection<Mobile> mobiles = GetAllMobile();
        Collection<Mobile> result = new ArrayList<Mobile>();
        for (Mobile m: mobiles){
            if(m.getManucafterer() == manucafterer){
                result.add(m);
            }
        }
        return  result;
    }

    public Collection<Mobile> GetMobilesBetweenYears(int fromYear, int toYear) throws IOException, InvalidYearRangeException {
        Collection<Mobile> mobiles = GetAllMobile();
        Collection<Mobile> result = new ArrayList<Mobile>();
        if(toYear<fromYear){
            throw new InvalidYearRangeException("The toYear:("+toYear+") cannot be lower than the fromYear:("+fromYear+")");
        }
        for (Mobile m: mobiles){
            if(m.getDate_of_manucafturing().getYear() >= fromYear && m.getDate_of_manucafturing().getYear() <= toYear){
                result.add(m);
            }
        }
        return result;
    }
}
