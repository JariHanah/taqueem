/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import static nasiiCalendar.BasicCalendar.DAY;


public class Zodiac13Calendar extends SolarCalendar {
 
    private static final long STARTTIME= 977011200000L+DAY;
    private static final int STARTYEAR= 2001;
    
    static final BasicYear Y_NORMAL=new BasicYear("JulianYear",false, false, new PeriodType[]{
            Months.Z01, Months.Z02, Months.Z03, Months.Z04, Months.Z05, Months.Z06,
            Months.Z07, Months.Z08, Months.Z09, Months.Z10,
            Months.Z11, Months.Z12, Months.Z13});
    static final BasicYear Y_LEAP=new BasicYear("JulianLeap",  true, false, new PeriodType[]{
            Months.Z01, Months.Z02, Months.Z03, new LeapMonth(39,Months.Z04), Months.Z05, Months.Z06,
            Months.Z07, Months.Z08, Months.Z09, Months.Z10,
            Months.Z11, Months.Z12, Months.Z13});
    
    @Override
    public int getMonthLength(BasicDate bd) {
        switch(bd.getMonth()){
            case 1: return 32;
            case 2: return 28;
            case 3: return 24;
            case 4: return isLeapYear(bd.getYear())?39:38;
            case 5: return 25;
            case 6: return 37;
            case 7: return 31;
            case 8: return 20;
            case 9: return 37;
            case 10: return 45;
            case 11: return 25;
            case 12: return 7;
            case 13:default: return 18;
        }
        
    }
    public Zodiac13Calendar(ZoneId zone) {
        super(BasicCalendar.ZODIAC13_ID,BasicCalendar.YEAR_SIDEREAL*4, 4, STARTTIME, STARTYEAR, zone);

    }

    @Override
    protected void setup() {
        
        NORMAL_YEAR.clear();
        NORMAL_YEAR_DURATION.clear();
        LEAP_YEAR.clear();
        LEAP_YEAR_DURATION.clear();
        int x = 0;
        int month = 0;
        x = addLength(x, month++);
        x = addLength(x + 32, month++);
        x = addLength(x + 28, month++);
        x = addLength(x + 24, month++);
        x = addLength(x + 38, month++);
        x = addLength(x + 25, month++);
        x = addLength(x + 37, month++);
        x = addLength(x + 31, month++);
        x = addLength(x + 20, month++);
        x = addLength(x + 37, month++);
        x = addLength(x + 45, month++);
        x = addLength(x + 23, month++);
        x = addLength(x + 7 , month++);
  //      x = addLength(x + 18, month++);
        
        month = 0;
        x = 0;
        
        x = addLeapLength(x, month++);
        x = addLeapLength(x + 32, month++);
        x = addLeapLength(x + 28, month++);
        x = addLeapLength(x + 24, month++);
        x = addLeapLength(x + 39, month++);
        x = addLeapLength(x + 25, month++);
        x = addLeapLength(x + 37, month++);
        x = addLeapLength(x + 31, month++);
        x = addLeapLength(x + 20, month++);
        x = addLeapLength(x + 37, month++);
        x = addLeapLength(x + 45, month++);
        x = addLeapLength(x + 23, month++);
        x = addLeapLength(x + 7 , month++);
        
        
        
    }

       @Override
    public BasicDate getDate(long t) {
        long time = cleanDate(t);
        if(!isDateInRange(time))return getMaximumDate();

        time -= getStartTime();
        int years = (int) (time / (BasicCalendar.YEAR_SIDEREAL)) ;

        long rest = time - years * BasicCalendar.YEAR_SIDEREAL;

        years+=getStartYear();
        Map.Entry<Long, Integer> monthKey = isLeapYear(years) ? LEAP_YEAR.floorEntry(rest) : NORMAL_YEAR.floorEntry(rest);
        //  hussam.println(NORMAL_YEAR);
        rest = rest - monthKey.getKey();
    //    hussam.println("time: " + time + " yers: " + years + " rest: " + rest / DAY);
        int month = monthKey.getValue();
        int day = (int) (rest / DAY);

        BasicDate d = getDate(years, month + 1, day + 1);
        return d;
    }

    public boolean isLeapYear(int y) {
        return y % 4 == 0;
    }

    @Override
    public BasicDate calculateDate(int y, int m, int d) {
        int yc = y - getStartYear();

        long time = yc * BasicCalendar.YEAR_SIDEREAL;
        TreeMap<Integer, Long> dur = null;
        if (isLeapYear(y)) {
            dur = LEAP_YEAR_DURATION;
        } else {
            dur = NORMAL_YEAR_DURATION;
        }
        time += dur.get(m - 1) + (d - 1) * DAY + getStartTime();
        MyDate md = new MyDate(cleanDate(time), d, m, y, this);
        
        return md;

    }

    @Override
    public PeriodType getYearType(int bd) {
        if(isLeapYear(bd))return Y_LEAP;
        return Y_NORMAL;
    }

    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(new BasicYear[]{Y_LEAP, Y_NORMAL});
    }


}
