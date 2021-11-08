package com.houndsoft.towerbridge.services.utils;

import java.text.DateFormatSymbols;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static YearMonth getFromDate(Date date) {
        return YearMonth.from(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public static String getMonthSpanish(YearMonth yearMonth){
//        final DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(Locale.forLanguageTag("es-ES"));
        return yearMonth.getMonth().getDisplayName(TextStyle.FULL,Locale.forLanguageTag("es-ES"));
    }

    public static Date getFixedDate(Date date){
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }
}

