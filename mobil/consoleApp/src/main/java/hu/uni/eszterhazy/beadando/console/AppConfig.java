package hu.uni.eszterhazy.beadando.console;

import hu.uni.eszterhazy.beadando.controller.MobileController;
import hu.uni.eszterhazy.beadando.dao.MobileDao;
import hu.uni.eszterhazy.beadando.dao.impl.MobileDAOJSON;
import hu.uni.eszterhazy.beadando.service.impl.MobileServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AppConfig {

    @Bean
    public MobileDAOJSON mobileDao() throws IOException {

        return new MobileDAOJSON("mobile.json");
    }


        @Bean
        public MobileServiceImpl bookSearchService(MobileDao dao){
            MobileServiceImpl searchService = new MobileServiceImpl(dao);
            return searchService;
        }


        @Bean(name = {"mobileController", "aliasOfMobileController"})
        public MobileController mobileController(MobileServiceImpl searchService){
            MobileController controller = new MobileController(searchService);
            return controller;
        }
    }

