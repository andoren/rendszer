package hu.uni.eszterhazy.beadando.controller;

import hu.uni.eszterhazy.beadando.dao.MobileDao;
import hu.uni.eszterhazy.beadando.exceptions.InvalidImeiException;
import hu.uni.eszterhazy.beadando.model.Mobile;
import hu.uni.eszterhazy.beadando.service.MobileService;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileAlreadyExistsException;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileNotFoundException;
import hu.uni.eszterhazy.beadando.service.impl.MobileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Collection;


@Controller
public class MobileController {
    MobileServiceImpl service;
    public MobileController(MobileServiceImpl service){
        this.service = service;
    }

    public Collection<Mobile> listOfMobiles() throws IOException {
        return service.GetAllMobile();
    }
    public String addMobile(Mobile mobile) throws IOException, MobileNotFoundException {
        try{
            if(service.AddMobile(mobile)){
                return "Telefon hozzáadva: "+mobile.getImei();
            }
            else {
                return "Ismeretlen hiba";
            }
        }catch (MobileAlreadyExistsException e){
            return "Ezzel az imei számmál már van telefon a raktárban! Imei: "+mobile.getImei();
        }
    }
    public String updateMobile(Mobile mobile) throws IOException{
        try {
            if(service.UpdateMobile(mobile)){
                return "Telefon módosítva: "+mobile.getImei();
            }
            else{
                return "Ismeretlen hiba.";
            }
        }catch (MobileNotFoundException e){
            return "Nincs ilyen telefon a raktárban! Imei: "+mobile.getImei();
        }
    }
    public String deleteMobile(String imei) throws IOException{
        try {
            if(service.DeleteMobile(imei)){
                return "Telefon törölve: "+imei;
            }
            else{
                return "Ismeretlen hiba.";
            }
        }catch (MobileNotFoundException e){
            return "Nincs ilyen telefon a raktárban! Imei: "+imei;
        }
    }
}