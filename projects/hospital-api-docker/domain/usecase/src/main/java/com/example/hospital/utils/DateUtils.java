package com.example.hospital.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {


    public static String toIsoString(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toString();
    }

}


