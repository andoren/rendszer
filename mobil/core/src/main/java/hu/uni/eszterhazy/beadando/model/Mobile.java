package hu.uni.eszterhazy.beadando.model;
import hu.uni.eszterhazy.beadando.exceptions.InvalidCameraNumberException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidImeiException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidMobileTypeException;
import hu.uni.eszterhazy.beadando.exceptions.InvalidScreenSizeException;

import java.time.LocalDate;

public class Mobile {
    public Mobile(){

    }
    public Mobile(Manucafterer manucafterer, String type, int cameranumber, LocalDate date_of_manucafturing, String imei, boolean isnew, double screensize, String color) throws InvalidImeiException, InvalidMobileTypeException, InvalidCameraNumberException, InvalidScreenSizeException {
        this.manucafterer = manucafterer;
        setType(type);
        setCameranumber(cameranumber);
        this.date_of_manucafturing = date_of_manucafturing;
        setImei(imei);
        this.isnew = isnew;
        setScreensize(screensize);
        this.color = color;
    }

    private Manucafterer manucafterer;
    private String type;
    private int cameranumber;
    private LocalDate date_of_manucafturing;
    private String imei;
    private boolean isnew;
    private double screensize;
    private String color;

    public Manucafterer getManucafterer() {
        return manucafterer;
    }

    public void setManucafterer(Manucafterer manucafterer) {
        if(manucafterer == null) throw new NullPointerException();
        this.manucafterer = manucafterer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) throws InvalidMobileTypeException {
        if(type.length() < 1 || type.length() > 30) throw new InvalidMobileTypeException(type);
        this.type = type;
    }

    public int getCameranumber() {
        return cameranumber;
    }

    public void setCameranumber(int cameranumber) throws InvalidCameraNumberException {
        if(cameranumber < 0 || cameranumber > 8) throw new InvalidCameraNumberException(cameranumber);
        this.cameranumber = cameranumber;
    }

    public LocalDate getDate_of_manucafturing() {
        return date_of_manucafturing;
    }

    public void setDate_of_manucafturing(LocalDate date_of_manucafturing) {
        this.date_of_manucafturing = date_of_manucafturing;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) throws InvalidImeiException {

        int len = imei.length();

        if (len != 15)
            throw new InvalidImeiException(imei);

        int sum = 0;
        Long n = Long.parseLong(imei);
        for (int i = len; i >= 1; i--)
        {
            int d = (int)(n % 10);
            if (i % 2 == 0)
                d = 2 * d;
            sum += sumDig(d);
            n = n / 10;
        }
        System.out.printf("");
        if (sum % 10 != 0) throw new InvalidImeiException(imei);
        this.imei = imei;
    }

    public boolean isIsnew() {
        return isnew;
    }

    public void setIsnew(boolean isnew) {
        this.isnew = isnew;
    }

    public double getScreensize() {
        return screensize;
    }

    public void setScreensize(double screensize) throws InvalidScreenSizeException {
        if (screensize <= 3 || screensize > 10) throw new InvalidScreenSizeException(screensize);
        this.screensize = screensize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    int sumDig(int n)
    {
        int a = 0;
        while (n > 0)
        {
            a = a + n % 10;
            n = n / 10;
        }
        return a;
    }
}
