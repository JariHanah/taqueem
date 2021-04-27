/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.util.Date;

/**
 *
 * @author hmulh
 */
public class MyDate implements BasicDate {

    long time;
    int day;
    int month;
    int year;
    int minitues=0;
    BasicCalendar cal;

    public MyDate(long t, int day, int month, int year, BasicCalendar dt) {
        time = t;
        this.day = day;
        this.month = month;
        this.year = year;
        cal = dt;
    }
    public MyDate(long t, int day, int month, int year, BasicCalendar dt, int minutes) {
        this(t, day, month, year, dt);
        this.minitues=minutes;
    }
    @Override
    public int getMinute() {
        return minitues;
    }
    
    @Override
    public long getDate() {
        return time;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public BasicCalendar getCalendar() {
        return cal;
    }

    public String toString() {
        return getCalendar().toString() + " " + year + " " + month + " " + day + "(" + time + ")"+new Date(time);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BasicDate) {
            BasicDate b = (BasicDate) o;
            
            if (day == b.getDay() && month == b.getMonth() && year == b.getYear() && cal == b.getCalendar() && time == b.getDate()) {

                return true;
            }
            return false;
        }
        return false;
    }
}
