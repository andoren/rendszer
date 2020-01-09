package hu.uni.eszterhazy.beadando.service.impl;

import hu.uni.eszterhazy.beadando.dao.MobileDao;
import hu.uni.eszterhazy.beadando.exceptions.InvalidCameraNumberException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidImeiException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidMobileTypeException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidScreenSizeException;
import hu.uni.eszterhazy.beadando.model.Manucafterer;
import hu.uni.eszterhazy.beadando.model.Mobile;
import hu.uni.eszterhazy.beadando.service.exceptions.InvalidYearRangeException;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileAlreadyExistsException;
import hu.uni.eszterhazy.beadando.service.exceptions.MobileNotFoundException;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.*;
import org.junit.matchers.JUnitMatchers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;

import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.same;

public class MobileServiceImplTest {
    @Mock
    public MobileDao dao;
    @TestSubject
    private MobileServiceImpl service;

    Collection<Mobile> dummyDB;
    Mobile errorMobile ;
    Mobile goodMobile;
    @Before
    public void setUp() throws InvalidMobileTypeException, InvalidImeiException, InvalidScreenSizeException, InvalidCameraNumberException, IOException, MobileNotFoundException, MobileAlreadyExistsException {
        //Arrange
        dao = EasyMock.niceMock(MobileDao.class);
        this.service = new MobileServiceImpl(dao);
        errorMobile = new Mobile(Manucafterer.Apple, "Iphone 6", 2, LocalDate.of(2015,02,8), "868779036670818", false, 4.5,"blue");
        goodMobile = new Mobile(Manucafterer.Apple, "Iphone 6", 2, LocalDate.of(2015,02,8), "868779036670818", false, 4.5,"blue");
        dummyDB = Arrays.asList(new Mobile(Manucafterer.Apple, "Iphone 6", 2, LocalDate.of(2015,02,8), "868779036670818", false, 4.5,"blue"),
                new Mobile(Manucafterer.Samsung, "Galaxy S7", 2, LocalDate.of(2016,11,8), "358265014779323", false, 4.5,"white"),
                new Mobile(Manucafterer.Samsung, "Galaxy S8", 3, LocalDate.of(2017,05,8), "358265017799013", false, 5,"rosegold"),
                new Mobile(Manucafterer.Samsung, "Galaxy S9", 4, LocalDate.of(2018,07,8), "358265018769114", false, 6,"black"));
        EasyMock.expect(dao.ReadMobiles()).andReturn(dummyDB).anyTimes();
        EasyMock.expect(dao.ReadMobileByImei("868779036670818")).andReturn(new Mobile(Manucafterer.Samsung, "Galaxy S6", 2, LocalDate.now(), "868779036670818", false, 4.5,"blue")).andThrow(new MobileNotFoundException("Mock"));
        EasyMock.expect(dao.ReadMobileByImei("888888888888888")).andThrow(new MobileNotFoundException("Omg my first mocked expcetion :O"));
        EasyMock.expect(dao.AddMobile(goodMobile)).andReturn(true).andThrow(new MobileAlreadyExistsException("Omg my first mocked expcetion :O"));
        EasyMock.expect(dao.UpdateMobile(goodMobile)).andReturn(true).andThrow(new MobileNotFoundException("Omg my first mocked expcetion :O"));
        EasyMock.expect(dao.DeleteMobile("868779036670818")).andReturn(true);
        EasyMock.expect(dao.AddMobile(same(errorMobile))).andThrow(new MobileAlreadyExistsException("Meow"));
        EasyMock.expect(dao.UpdateMobile(same(errorMobile))).andThrow(new MobileNotFoundException("Omg my first mocked expcetion :O"));
        EasyMock.expect(dao.DeleteMobile("888888888888888")).andThrow(new MobileNotFoundException("Omg my first mocked expcetion :O"));

        EasyMock.replay(dao);
    }

    @Test
    public void ReadMobilesTest() throws IOException {
        //ACT
        Collection<Mobile> allMobiles = service.GetAllMobile();
        //ASSERT
        Assert.assertEquals(4, allMobiles.size());
    }
    @Test
    public void GetMobilesBetweenYearsTest() throws IOException, InvalidYearRangeException {
        Collection<Mobile> mobiles  = service.GetMobilesBetweenYears(2016,2017);
        Assert.assertEquals(2,mobiles.size());

    }
    @Test(expected = InvalidYearRangeException.class)
    public void GetMobilesBetweenYearsInvalidYear() throws IOException, InvalidYearRangeException {
        Collection<Mobile> mobiles  = service.GetMobilesBetweenYears(2016,2014);


    }
    @Test
    public void GetMobilesBetweenYearsTestZeromobile() throws IOException, InvalidYearRangeException {
        Collection<Mobile> mobiles  = service.GetMobilesBetweenYears(2005,2008);
        Assert.assertEquals(0,mobiles.size());

    }
    @Test
    public void GetMobilesBetweenYearsTestAllmobile() throws IOException, InvalidYearRangeException {
        Collection<Mobile> mobiles  = service.GetMobilesBetweenYears(2005,LocalDate.now().getYear());
        Assert.assertEquals(4,mobiles.size());

    }
    @Test
    public void GetMobilesByManufactererTest() throws IOException{
        Collection<Mobile> mobiles  = service.GetMobilesByManufacturer(Manucafterer.Huawei);
        Assert.assertEquals(0,mobiles.size());

    }
    @Test
    public void GetMobilesByManufactererTestApple() throws IOException{
        Collection<Mobile> mobiles  = service.GetMobilesByManufacturer(Manucafterer.Apple);
        Assert.assertEquals(1,mobiles.size());

    }
    @Test
    public void GetMobilesByManufactererTestSamsung() throws IOException{
        Collection<Mobile> mobiles  = service.GetMobilesByManufacturer(Manucafterer.Samsung);
        Assert.assertEquals(3,mobiles.size());

    }
    @Test
    public void MobileFoundTest() throws IOException, MobileNotFoundException {
        //Act
        Mobile mobile = service.GetMobileByImei("868779036670818");
        //ASSERT
        Assert.assertNotEquals(null,mobile);

    }
    @Test(expected = MobileNotFoundException.class)
    public void MobileNotFoundTest() throws IOException, MobileNotFoundException {

        //Act
        Mobile mobile = service.GetMobileByImei("888888888888888");
        //Assert

    }
    @Test(expected = MobileAlreadyExistsException.class)
    public void AddMobileTestMobileAlreadyExist() throws MobileAlreadyExistsException, IOException, MobileNotFoundException {

        //Act
        Mobile mobile = errorMobile;
        //Assert
        service.AddMobile(errorMobile);
    }
    @Test
    public void AddMobileTestMobileGood() throws MobileAlreadyExistsException, IOException, MobileNotFoundException {

        //Act
        Mobile mobile = goodMobile;
        boolean result = service.AddMobile(goodMobile);
        //Assert
        Assert.assertEquals(true,result);

    }
    @Test(expected = MobileNotFoundException.class)
    public void UpdateMobileTestMobileNotFound() throws  IOException, MobileNotFoundException {

        //Act
        service.UpdateMobile(errorMobile);
        //Assert

    }
    @Test
    public void UpdateMobileTest() throws  IOException, MobileNotFoundException {

        //Act
        boolean result =  service.UpdateMobile(goodMobile);
        //Assert
        Assert.assertEquals(true,result);

    }
    @Test
    public void DeleteMobileTest() throws IOException, MobileNotFoundException {
        //Act
        boolean result = service.DeleteMobile("868779036670818");
        //Assert
        Assert.assertEquals(true,result);
    }
    @Test(expected = MobileNotFoundException.class)
    public void DeleteMobileTestError() throws IOException, MobileNotFoundException {
        //Act
        boolean result = service.DeleteMobile("888888888888888");
        //Assert
        Assert.assertEquals(true,result);
    }

}
