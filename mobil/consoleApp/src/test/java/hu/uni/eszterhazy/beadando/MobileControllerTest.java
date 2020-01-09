package hu.uni.eszterhazy.beadando;


import hu.uni.eszterhazy.beadando.console.AppConfig;
import hu.uni.eszterhazy.beadando.controller.MobileController;
import hu.uni.eszterhazy.beadando.exceptions.InvalidCameraNumberException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidImeiException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidMobileTypeException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidScreenSizeException;
import hu.uni.eszterhazy.beadando.model.Manucafterer;
import hu.uni.eszterhazy.beadando.model.Mobile;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileNotFoundException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.awt.print.Book;
import java.io.IOException;
import java.time.LocalDate;

@ContextConfiguration(classes= AppConfig.class)
public class MobileControllerTest {
    @Autowired

    ApplicationContext context ;
    MobileController controller;
    @Before
    public void setUpt(){
        context=  new AnnotationConfigApplicationContext(AppConfig.class);
        controller = (MobileController) context.getBean("aliasOfMobileController");
    }
    @Test
    public void addMobileTest() throws IOException, MobileNotFoundException, InvalidMobileTypeException, InvalidImeiException, InvalidScreenSizeException, InvalidCameraNumberException {
        //Act
        String result = controller.addMobile(new Mobile(Manucafterer.Apple, "Iphone 6", 2, LocalDate.of(2015,02,8), "868779036670818", false, 4.5,"blue"));

        //Assert
        Assert.assertEquals(true,result.equals("Telefon hozzáadva: 868779036670818"));
    }
    @Test
    public void addMobileTestError() throws IOException, MobileNotFoundException, InvalidMobileTypeException, InvalidImeiException, InvalidScreenSizeException, InvalidCameraNumberException {
        //Act
        String result = controller.addMobile(new Mobile(Manucafterer.Apple, "Iphone 6", 2, LocalDate.of(2015,02,8), "868779036670818", false, 4.5,"blue"));
        String result2 = controller.addMobile(new Mobile(Manucafterer.Apple, "Iphone 6", 2, LocalDate.of(2015,02,8), "868779036670818", false, 4.5,"blue"));

        //Assert
        Assert.assertEquals(true,result2.equals("Ezzel az imei számmál már van telefon a raktárban! Imei: 868779036670818"));
    }
    @Test
    public void updateMobileTestError() throws IOException, MobileNotFoundException, InvalidMobileTypeException, InvalidImeiException, InvalidScreenSizeException, InvalidCameraNumberException {
        //Act
        String result = controller.updateMobile(new Mobile(Manucafterer.Apple, "Iphone 6", 2, LocalDate.of(2015,02,8), "868779036670818", false, 4.5,"blue"));
        //Assert
        Assert.assertEquals(true,result.equals("Nincs ilyen telefon a raktárban! Imei: 868779036670818"));
    }
    @Test
    public void updateMobileTest() throws IOException, MobileNotFoundException, InvalidMobileTypeException, InvalidImeiException, InvalidScreenSizeException, InvalidCameraNumberException {
        //Act
        controller.addMobile(new Mobile(Manucafterer.Apple, "Iphone 6", 2, LocalDate.of(2015,02,8), "868779036670818", false, 4.5,"blue"));
        String result = controller.updateMobile(new Mobile(Manucafterer.Apple, "Iphone 6", 2, LocalDate.of(2015,02,8), "868779036670818", false, 4.5,"blue"));
        //Assert
        Assert.assertEquals(true,result.equals("Telefon módosítva: 868779036670818"));
    }
    @Test
    public void deleteMobileTestError() throws IOException, MobileNotFoundException {
        //Act
        String result = controller.deleteMobile("358265018769114");
        //Assert
        Assert.assertEquals(true,result.equals("Nincs ilyen telefon a raktárban! Imei: 358265018769114"));
    }
    @Test
    public void deleteMobileTest() throws IOException, MobileNotFoundException, InvalidMobileTypeException, InvalidImeiException, InvalidScreenSizeException, InvalidCameraNumberException {
        //Act
        controller.addMobile(new Mobile(Manucafterer.Apple, "Iphone 6", 2, LocalDate.of(2015,02,8), "868779036670818", false, 4.5,"blue"));
        String result = controller.deleteMobile("868779036670818");
        //Assert
        Assert.assertEquals(true,result.equals("Telefon törölve: "+"868779036670818"));
    }
}
