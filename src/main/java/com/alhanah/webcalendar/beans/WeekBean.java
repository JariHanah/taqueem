package com.alhanah.webcalendar.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Entity;

import javax.persistence.Id;
import static nasiiCalendar.BasicCalendar.DAY;
import static nasiiCalendar.BasicCalendar.WEEK;
import nasiiCalendar.CalendarFactory;

//@Entity
public class WeekBean {

    @Id
    private Long id;
    private Long sunday;
    private Long monday;
    private Long tuesday;
    private Long wednesday;
    private Long thursday;
    private Long friday;
    private Long saturday;
    static Calendar greg = Calendar.getInstance();

    WeekBean(long sundayTime) {

        id = sundayTime;
    }

    public long getDay(int day) {
        switch (day) {
            case 1:
                return getSunday();
            case 2:
                return getMonday();
            case 3:
                return getTuesday();
            case 4:
                return getWednesday();
            case 5:
                return getThursday();
            case 6:
                return getFriday();
            default:
                return getSaturday();
        }
    }

    public static List<WeekBean> getWeeks(long start, int number) {
        List<WeekBean> list = new ArrayList<WeekBean>();
        int day = CalendarFactory.getWeekDay(start);
        start = start - (day - 1) * DAY;
        CalendarFactory.cleanDate(start);
        for (int i = 0; i < number; i++) {
            list.add(new WeekBean(start));
            start += WEEK;
        }
        return list;
    }

    /**
     * @return the sunday
     */
    public Long getSunday() {
        return getId();
    }

    /**
     * @param sunday the sunday to set
     */
    public void setSunday(Long sunday) {
        this.sunday = sunday;
    }

    /**
     * @return the monday
     */
    public Long getMonday() {
        return getSunday() + DAY * 1;
    }

    /**
     * @param monday the monday to set
     */
    public void setMonday(Long monday) {
        this.monday = monday;
    }

    /**
     * @return the tuesday
     */
    public Long getTuesday() {
        return getSunday() + DAY * 2;
    }

    /**
     * @param tuesday the tuesday to set
     */
    public void setTuesday(Long tuesday) {
        this.tuesday = tuesday;
    }

    /**
     * @return the wednesday
     */
    public Long getWednesday() {
        return getSunday() + DAY * 3;
    }

    /**
     * @param wednesday the wednesday to set
     */
    public void setWednesday(Long wednesday) {
        this.wednesday = wednesday;
    }

    /**
     * @return the thursday
     */
    public Long getThursday() {
        return getSunday() + DAY * 4;
    }

    /**
     * @param thursday the thursday to set
     */
    public void setThursday(Long thursday) {
        this.thursday = thursday;
    }

    /**
     * @return the friday
     */
    public Long getFriday() {
        return getSunday() + DAY * 5;
    }

    /**
     * @param friday the friday to set
     */
    public void setFriday(Long friday) {
        this.friday = friday;
    }

    /**
     * @return the saturday
     */
    public Long getSaturday() {
        return getSunday() + DAY * 6;
    }

    /**
     * @param saturday the saturday to set
     */
    public void setSaturday(Long saturday) {
        this.saturday = saturday;
    }
///extends AbstractEntity {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof WeekBean)) {
            return false; // null or other class
        }
        WeekBean other = (WeekBean) obj;

        if (id != null) {
            return id.equals(other.id);
        }
        return super.equals(other);
    }
}
