package hu.uni.eszterhazy.beadando.service;

import hu.uni.eszterhazy.beadando.model.Manucafterer;
import hu.uni.eszterhazy.beadando.model.Mobile;
import hu.uni.eszterhazy.beadando.service.exceptions.InvalidYearRangeException;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileAlreadyExistsException;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileNotFoundException;

import java.io.IOException;
import java.util.Collection;

public interface MobileService {
    Collection<Mobile> GetAllMobile() throws IOException;
    Mobile GetMobileByImei(String imei) throws MobileNotFoundException, IOException;
    boolean AddMobile(Mobile mobile) throws MobileAlreadyExistsException, IOException, MobileNotFoundException;
    boolean UpdateMobile(Mobile mobile) throws MobileNotFoundException, IOException;
    boolean DeleteMobile(String imei) throws MobileNotFoundException, IOException;
    Collection<Mobile> GetMobilesByManufacturer(Manucafterer manucafterer) throws IOException;
    Collection<Mobile> GetMobilesBetweenYears(int fromYear, int toYear) throws IOException, InvalidYearRangeException;
}
