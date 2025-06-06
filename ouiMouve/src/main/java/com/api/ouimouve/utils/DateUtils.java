package com.api.ouimouve.utils;

import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

    /**
     * Converts the given date to the end of the day (23:59:59).
     * 
     * @param date the original date
     * @return a new Date object set to 23:59:59 of the same day
     */
    public static Date toEndOfDay(Date date) {
        if (date == null) {
            return null;
        }

        return Date.from(date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}