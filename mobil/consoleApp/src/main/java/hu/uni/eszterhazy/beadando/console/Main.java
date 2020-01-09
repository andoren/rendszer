package hu.uni.eszterhazy.beadando.console;

import hu.uni.eszterhazy.beadando.controller.MobileController;
import hu.uni.eszterhazy.beadando.exceptions.InvalidCameraNumberException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidImeiException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidMobileTypeException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidScreenSizeException;
import hu.uni.eszterhazy.beadando.model.Manucafterer;
import hu.uni.eszterhazy.beadando.model.Mobile;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileAlreadyExistsException;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
public class Main {

    public static void main(String[] args) throws InvalidMobileTypeException, InvalidImeiException, InvalidScreenSizeException, InvalidCameraNumberException, MobileAlreadyExistsException, IOException, MobileNotFoundException {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MobileController controller = (MobileController) context.getBean("aliasOfMobileController");
        System.out.println(controller.addMobile( new Mobile(Manucafterer.Apple, "Iphone 6", 2, LocalDate.of(2015,02,8), "868779036670818", false, 4.5,"blue")));
        System.out.println(controller.addMobile( new Mobile(Manucafterer.Apple, "Iphone 6", 2, LocalDate.of(2015,02,8), "868779036670818", false, 4.5,"blue")));
        for(Mobile mobile: controller.listOfMobiles()){
            System.out.println("\nTelefon imei száma: "+mobile.getImei()+" Telefon tipusa: "+mobile.getType()+" Telefon szine: "+mobile.getColor());
        }
        System.out.println(controller.updateMobile( new Mobile(Manucafterer.Apple, "Iphone 7", 2, LocalDate.of(2015,02,8), "868779036670818", false, 4.5,"blue")));
        System.out.println(controller.updateMobile( new Mobile(Manucafterer.Apple, "Iphone 7", 2, LocalDate.of(2015,02,8), "358265018769114", false, 4.5,"blue")));
        System.out.println(controller.deleteMobile("868779036670818"));
        System.out.println(controller.deleteMobile("358265018769114"));
    }

}
