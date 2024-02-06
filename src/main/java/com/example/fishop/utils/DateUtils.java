package com.example.fishop.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    static DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    static LocalDateTime now = LocalDateTime.now();

    public static String getCurrentDateTime(){
        return now.format(format);
    }
}
