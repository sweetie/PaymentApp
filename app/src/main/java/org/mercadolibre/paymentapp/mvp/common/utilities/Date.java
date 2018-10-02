package org.mercadolibre.paymentapp.mvp.common.utilities;

import java.util.Calendar;

/**
 * Created by Enny Querales
 */

public class Date {

    /**
     * Attributes
     */
    private int year;
    private int month;
    private int day;
    private Calendar calendar;


    public Date() {
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
    }

    public Date(int year, int month, int day) {
        calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String toString() {
        String monthString = "";
        switch (month) {
            case 0:
                monthString = "Jan";
                break;
            case 1:
                monthString = "Feb";
                break;
            case 2:
                monthString = "Mar";
                break;
            case 3:
                monthString = "Apr";
                break;
            case 4:
                monthString = "May";
                break;
            case 5:
                monthString = "Jun";
                break;
            case 6:
                monthString = "Jul";
                break;
            case 7:
                monthString = "Aug";
                break;
            case 8:
                monthString = "Sep";
                break;
            case 9:
                monthString = "Oct";
                break;
            case 10:
                monthString = "Nov";
                break;
            case 11:
                monthString = "Dec";
                break;
        }
        return day + " " + monthString + ", " + year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    @SuppressWarnings("RedundantIfStatement")
    public boolean isValid() {
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);

        if (year > currentYear)
            return true;
        else if (year == currentYear) {
            if (month > currentMonth)
                return true;
            else if (month == currentMonth) {
                if (day >= currentDay)
                    return true;
                else
                    return false;
            }
        }

        return false;
    }

    public boolean isGreaterThan(Date date) {
        if (this.year == date.year) {
            if (this.month > date.month)
                return true;
            else if (this.month == date.month) {
                return this.day > date.day;
            }
        }
        return false;
    }
}


