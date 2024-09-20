package com.example.progettoisw.utils;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class DateUtils {
    public static void setDate(DatePicker datePicker, TextField hour, TextField min) {
        datePicker.setValue(LocalDate.now());
        LocalTime now = LocalTime.now();
        hour.setText(now.getHour() + "");

        int minNow = now.getMinute();
        min.setText((minNow < 10 ? "0" : "") + minNow);
    }

    public static boolean hoursLegal(int... hours) {
        for(int hour : hours)
            if(hour < 0 || hour >= 24)
                return false;

        return true;
    }

    public static boolean minsLegal(int... mins) {
        for(int min : mins)
            if(min < 0 || min >= 60)
                return false;

        return true;
    }

    public static java.sql.Date getDateFromPicker(DatePicker datePicker, int hour, int min) {
        LocalDate ld = datePicker.getValue();
        Calendar c = Calendar.getInstance();
        c.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth(), hour, min);
        return new java.sql.Date(c.getTime().getTime());
    }

    public static String getToString(java.sql.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        return sdf.format(date);
    }

    // Funzioni di DateUtils di Java di base
    public static boolean isSameDay(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new DateTimeException("Null Date");
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    public static boolean isSameDay(final Calendar cal1, final Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new DateTimeException("Null Date");
        }
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
