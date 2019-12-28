package hu.uni.eszterhazy.beadando.dao.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hu.uni.eszterhazy.beadando.dao.MobileDao;
import hu.uni.eszterhazy.beadando.model.Mobile;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileAlreadyExistsException;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileNotFoundException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MobileDAOJSON implements MobileDao {
    File jsonFile;
    ObjectMapper mapper;

    public MobileDAOJSON(String filepath) throws IOException {
        jsonFile = new File(filepath);

            jsonFile.createNewFile();
            FileWriter writer = new FileWriter(jsonFile);
            writer.write("[]");
            writer.close();



        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public Collection<Mobile> ReadMobiles() throws IOException {
        Collection<Mobile> result = new ArrayList<Mobile>();
        TypeReference type =new TypeReference<ArrayList<Mobile>>() {};
        result = mapper.readValue(jsonFile,type);
        return result;
    }

    public Mobile ReadMobileByImei(String imei) throws IOException, MobileNotFoundException {

        Collection<Mobile> mobiles = ReadMobiles();
        for (Mobile m:mobiles) {
            if(m.getImei().equals(imei)){

                return m;
            }
        }
        throw new MobileNotFoundException(imei);
    }


    public void AddMobile(Mobile mobile) throws IOException,  MobileAlreadyExistsException {
        Collection mobiles = ReadMobiles();
        try {
            ReadMobileByImei(mobile.getImei());
            throw new MobileAlreadyExistsException(mobile.getImei());
        }
        catch (MobileNotFoundException e){
            mobiles.add(mobile);
            mapper.writeValue(jsonFile,mobiles);
        }
    }

    public void UpdateMobile(Mobile mobile) throws IOException, MobileNotFoundException {
        Mobile m = ReadMobileByImei(mobile.getImei());
        System.out.printf(m.getImei());
        Collection<Mobile> mobiles = ReadMobiles();
        List<Mobile> tempmobiles  = new ArrayList<>(mobiles);
        int index = 0;
        for (int i = 0; i < tempmobiles.size() -1; i++){
            if(tempmobiles.get(i).getImei().equals(m.getImei())) index = i;
        }
        tempmobiles.set(index,mobile);
        mapper.writeValue(jsonFile,tempmobiles);
    }

    public void DeleteMobile(String imei) throws IOException, MobileNotFoundException {
        Collection<Mobile> mobiles = ReadMobiles();
        Mobile m = ReadMobileByImei(imei);
        Collection<Mobile> resultMobiles = new ArrayList<Mobile>();
        for (Mobile mobile:mobiles){
            if(!mobile.getImei().equals(imei)){
                System.out.printf("Mobile imei: "+mobile.getImei()+" Searched imei: "+imei+"\n");
                resultMobiles.add(mobile);
            }
        }
        mapper.writeValue(jsonFile,resultMobiles);
    }
}
