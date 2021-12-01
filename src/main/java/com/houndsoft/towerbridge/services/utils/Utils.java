package com.houndsoft.towerbridge.services.utils;

import java.time.Instant;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;

public class Utils {

    public static YearMonth getYearMonthFromDate(Date date) {
        return YearMonth.from(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }


    public static Date getDateFromYearMonth(YearMonth yearMonth) {
        Calendar c = Calendar.getInstance();
        c.set(yearMonth.getYear(), yearMonth.getMonth().getValue() - 1, 1, 0, 0);
        return c.getTime();
    }

    public static MonthDay getMonthDayFromDate(Date date) {
        return MonthDay.from(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
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

    public static List<YearMonth> getMonthsBetweenDates(YearMonth fechaInscripcion){
        List<YearMonth> list = new ArrayList<>();
        YearMonth current = YearMonth.from(Instant.ofEpochMilli(new Date().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
        while(!fechaInscripcion.isAfter(current)){
            list.add(fechaInscripcion);
            fechaInscripcion = fechaInscripcion.plusMonths(1);
        }
        return list;
    }

    public static YearMonth getCurrentMonthYear(){
        return getYearMonthFromDate(new Date());
    }

//    public static void main(String[] args){
//        YearMonth yearMonth = YearMonth.of(2021,6);
//        getMonthsBetweenDates(yearMonth);
//    }
}

