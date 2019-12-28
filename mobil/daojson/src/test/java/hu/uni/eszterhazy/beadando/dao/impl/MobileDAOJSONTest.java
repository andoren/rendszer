package hu.uni.eszterhazy.beadando.dao.impl;

import hu.uni.eszterhazy.beadando.exceptions.InvalidCameraNumberException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidImeiException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidMobileTypeException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidScreenSizeException;
import hu.uni.eszterhazy.beadando.model.Manucafterer;
import hu.uni.eszterhazy.beadando.model.Mobile;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileAlreadyExistsException;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

public class MobileDAOJSONTest {

    MobileDAOJSON dao;
    public MobileDAOJSONTest() throws IOException {
       dao = new MobileDAOJSON("mobiles");
    }
    @Before
    public void setUp() throws IOException, InvalidMobileTypeException, InvalidImeiException, InvalidScreenSizeException, InvalidCameraNumberException, MobileAlreadyExistsException {
        Mobile mobile = new Mobile(Manucafterer.Samsung, "Galaxy S6", 2, LocalDate.now(), "868779036670818", false, 4.5,"blue");
        dao.AddMobile(mobile);
    }
    @After
    public void AfterTest() throws IOException {
        dao = new MobileDAOJSON("mobiles");
    }
    @Test
    public void AddMobileTest() throws InvalidMobileTypeException, InvalidImeiException, InvalidScreenSizeException, InvalidCameraNumberException, IOException, MobileAlreadyExistsException {
        Mobile mobile = new Mobile(Manucafterer.Apple, "Iphone X", 4, LocalDate.of(2017,11,3), "354607076427917", true, 5.8,"White");
        dao.AddMobile(mobile);
        Assert.assertEquals(2,dao.ReadMobiles().size());
    }
    @Test
    public void UpdateMobileTest() throws InvalidMobileTypeException, InvalidImeiException, InvalidScreenSizeException, InvalidCameraNumberException, IOException,MobileNotFoundException {
        Mobile mobile = new Mobile(Manucafterer.Samsung, "Galaxy S9", 2, LocalDate.now(), "868779036670818", false, 4.5,"blue");
        dao.UpdateMobile(mobile);
        Collection<Mobile> mobiles = dao.ReadMobiles();
        Assert.assertEquals("Galaxy S9",((Mobile) mobiles.toArray()[0]).getType());
    }
    @Test(expected = InvalidImeiException.class)
    public void WrongImeiTestWhileUpdateing() throws InvalidMobileTypeException, InvalidImeiException, InvalidScreenSizeException, InvalidCameraNumberException, IOException, MobileNotFoundException {
        Mobile mobile = new Mobile(Manucafterer.Samsung, "Galaxy S9", 2, LocalDate.now(), "888888888888888", false, 4.5,"blue");
        dao.UpdateMobile(mobile);
    }
    @Test(expected = MobileNotFoundException.class)
    public void MobileNotFoundTestWhileUpdateing() throws InvalidMobileTypeException, InvalidImeiException, InvalidScreenSizeException, InvalidCameraNumberException, IOException, MobileNotFoundException {
        Mobile mobile = new Mobile(Manucafterer.Samsung, "Galaxy S9", 2, LocalDate.now(), "354607076427917", false, 4.5,"blue");
        dao.UpdateMobile(mobile);
    }

    @Test
    public void DeleteMobileTest() throws IOException, MobileNotFoundException {
        dao.DeleteMobile("868779036670818");
        Assert.assertEquals(0,dao.ReadMobiles().size());
    }
    @Test(expected = MobileNotFoundException.class)
    public void MobileNotFOundWhileDeletingTest() throws IOException, MobileNotFoundException {
        dao.DeleteMobile("354607076427917");
        Assert.assertEquals(0,dao.ReadMobiles().size());
    }

    @Test(expected = MobileNotFoundException.class)
    public void MobileNotFoundByImei() throws IOException, MobileNotFoundException {
        dao.ReadMobileByImei("354607076427917");

    }
    @Test
    public void MobileFoundByImei() throws IOException, MobileNotFoundException {
        Mobile mobile = dao.ReadMobileByImei("868779036670818");
        Assert.assertNotEquals(null,mobile);
    }
}
