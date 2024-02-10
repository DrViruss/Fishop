package com.example.fishop.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class DBUtils {
    static DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    static LocalDateTime now = LocalDateTime.now();

    static Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$");

    static Pattern LOCATION_PATTERN = Pattern.compile("^[a-zA-Z ]{2,56},[ a-zA-Z]{4,}$");
    static Pattern COUNTRY_PATTERN = Pattern.compile("^[a-zA-Z ]{2,56}$");
    static Pattern STATE_PATTERN = Pattern.compile("^[ a-zA-Z]{4,}$");
    public static String getCurrentDateTime(){
        return now.format(format);
    }


    public static boolean validatePassword(String pass)
    {
        return PASSWORD_PATTERN.matcher(pass).matches();
    }

    public static boolean validateLocation(String loc)
    {
        return LOCATION_PATTERN.matcher(loc).matches();
    }

    public static boolean validateCountry(String loc)
    {
        return COUNTRY_PATTERN.matcher(loc).matches();
    }

    public static boolean validateState(String loc)
    {
        return STATE_PATTERN.matcher(loc).matches();
    }
}
