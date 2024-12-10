package com.est.curdsample.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    public static LocalDate convertToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return LocalDate.parse(date, formatter);
    }

    public static String convertToStr(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    public static String convertToStr(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

}
