/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.util.List;

/**
 *
 * @author hmulh
 */
public class LeapMonth implements PeriodType{
    int days;
    Months month;

    public LeapMonth(int days, Months month) {
        this.days = days;
        this.month = month;
    }

    @Override
    public String getName() {
        return month.getName();
    }

    @Override
    public int getTotalLengthInDays() {
        return days;
    }

    @Override
    public long getDurationInTime() {
        return days*BasicCalendar.DAY;
    }

    @Override
    public boolean isBigLeap() {
        return false;
    }

    @Override
    public boolean isSmallLeap() {
        return true;
    }

    @Override
    public int getCountAsPeriods() {
        return 1;
    }

    @Override
    public List<PeriodType> getSubPeriods() {
        return null;
    }

    @Override
    public String toString() {
        return month.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    public Months getMonth() {
        return month;
    }
    
}