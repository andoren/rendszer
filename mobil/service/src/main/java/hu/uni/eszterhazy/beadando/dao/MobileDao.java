package hu.uni.eszterhazy.beadando.dao;

import hu.uni.eszterhazy.beadando.model.*;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileAlreadyExistsException;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileNotFoundException;

import java.io.IOException;
import java.util.Collection;

public interface MobileDao {
    Collection<Mobile> ReadMobiles() throws IOException;
    Mobile ReadMobileByImei(String imei) throws IOException, MobileNotFoundException;
    void AddMobile(Mobile mobile) throws IOException, MobileNotFoundException, MobileAlreadyExistsException;
    void UpdateMobile(Mobile mobile) throws IOException, MobileNotFoundException;
    void DeleteMobile(String imei) throws IOException, MobileNotFoundException;
}
