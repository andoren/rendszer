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

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

public class MobileServiceImplTest {
    @Mock
    public MobileDao dao;
    @TestSubject
    private MobileServiceImpl service;

    Collection<Mobile> dummyDB;

    @Before
    public void setUp() throws InvalidMobileTypeException, InvalidImeiException, InvalidScreenSizeException, InvalidCameraNumberException, IOException, MobileNotFoundException, MobileAlreadyExistsException {
        //Arrange
        dao = EasyMock.niceMock(MobileDao.class);
        this.service = new MobileServiceImpl(dao);
        dummyDB = Arrays.asList(new Mobile(Manucafterer.Samsung, "Galaxy S6", 2, LocalDate.now(), "868779036670818", false, 4.5,"blue"));
        EasyMock.expect(dao.ReadMobiles()).andReturn(dummyDB).anyTimes();
        EasyMock.expect(dao.ReadMobileByImei("868779036670818")).andReturn(new Mobile(Manucafterer.Samsung, "Galaxy S6", 2, LocalDate.now(), "868779036670818", false, 4.5,"blue")).andThrow(new MobileNotFoundException("Mock"));
        EasyMock.expect(dao.ReadMobileByImei("888888888888888")).andThrow(new MobileNotFoundException("Omg my first mocked expcetion :O"));

        EasyMock.replay(dao);
    }

    @Test
    public void ReadMobilesTest() throws IOException {
        //ACT
        Collection<Mobile> allMobiles = service.GetAllMobile();
        //ASSERT
        Assert.assertEquals(1, allMobiles.size());
    }
    @Test
    public void GetMobilesBetweenYearsTest() throws IOException, InvalidYearRangeException {
        Collection<Mobile> mobiles  = service.GetMobilesBetweenYears(2005,2019);
        Assert.assertEquals(1,mobiles.size());

    }
    @Test
    public void GetMobilesByManufactererTest() throws IOException{
        Collection<Mobile> mobiles  = service.GetMobilesByManufacturer(Manucafterer.Apple);
        Assert.assertEquals(0,mobiles.size());

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
    }
}
