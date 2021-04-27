/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import static nasiiCalendar.BasicCalendar.DAY;

/**
 *
 * @author DEll
 */
public class GregoryCalendar extends SolarCalendar {
static final long CYCLE400 = DAY * ((365 * 3 + 366) * 100 - 3);
    private static final long CYCLE100 = DAY * ((365 * 3 + 366) * 25 - 1);
    private static final long CYCLE4 = DAY * (365 * 3 + 366);

    private static final int BASE_YEAR1 = -3999999;//- 353*4000;
    private static final long BASE_TIME1 = -126289943564400000L;//-126289975230000000l;//-62135521200001l ;//- DAY*4000 * (((365*3+366)*100)-3);
    private static final int BASE_YEAR = 2001;//- 353*4000;
    public static final long BASE_TIME = 978339600000L-9*HOUR;//978382799999L-10800000L;//-126289975230000000l;//-62135521200001l ;//- DAY*4000 * (((365*3+366)*100)-3);
     
    public GregoryCalendar() {
        super(BasicCalendar.GREG_ID , CYCLE400, 400, BASE_TIME, BASE_YEAR);
  //      hussam.println("GregBase: "+new Date(BASE_TIME)+" Year: "+BASE_YEAR);
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR, 1999);
        c.set(Calendar.MONTH, 11);
        c.set(Calendar.DATE, 31);
        
    }

   

    @Override
    public BasicDate getDate(long t) {
        
        long time2 = CalendarFactory.cleanDate(t);
        if(!isDateInRange(time2))return getMaximumDate();

        long time = time2 - getStartTime();
        
        int cycle400 = (int) (time / CYCLE400);
        long rest = time - cycle400 * CYCLE400;
        int cycle100 = (int) (rest / CYCLE100);
        rest = rest - cycle100 * CYCLE100;
        int cycle4 = (int) (rest / CYCLE4);
        rest = rest - (cycle4 * CYCLE4);
        int years = (int) Math.min(3,rest / (365 * DAY));
        rest = rest - years * ((365) * DAY);
        int y1 = cycle400 * 400 + cycle100 * 100 + cycle4 * 4 + years + getStartYear();
   //     hussam.println("t:"+new Date(t)+" ct:"+new Date(time2)+" dur: "+time/YEAR_TROPICAL+" rest:"+rest+" startYear:"+y1);
        Map.Entry<Long, Integer> monthKey = (years == 3) ? LEAP_YEAR.floorEntry(rest) : NORMAL_YEAR.floorEntry(rest);
        rest = rest - monthKey.getKey();
        int month = monthKey.getValue();
        int day = (int) (rest / DAY);
        //   hussam.println("foirst y1: " + y1 + " m:" + (month + 1) + " d:" + (day + 1));

        BasicDate d = getDate(y1, month + 1, day + 1);

     //   System.out.println("time: " + new Date(d.getDate())+" "+years);
        return d;
    }

    public boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        }
        if (year % 100 == 0) {
            return false;
        }
        return year % 4 == 0;
    }

    @Override
    public BasicDate calculateDate(int y, int m, int d) {
        int yc = y - getStartYear();
        int cycle400 = yc / 400;
        int rest = yc - cycle400 * 400;
        int cycle100 = rest / 100;
        rest = rest - cycle100 * 100;

        int cycle4 = rest / 4;
        rest = rest - cycle4 * 4;

        long time = cycle400 * CYCLE400 + cycle100 * CYCLE100 + cycle4 * CYCLE4 + rest * 365 * DAY;
        time += (d - 1) * DAY;
        TreeMap<Integer, Long> dur = null;
     //   System.out.println("y: "+y+" "+isLeapYear(y));
        if (isLeapYear(y)) {
            dur = LEAP_YEAR_DURATION;
            
        } else {
            dur = NORMAL_YEAR_DURATION;
            
        }
       // System.out.println("dur: "+dur.get(m-1)/DAY);
        time += dur.get(m - 1) + getStartTime();
        if(!isDateInRange(time))return getMaximumDate();

        MyDate md = new MyDate(time, d, m, y, this);
     //   hussam.println("result:"+ md+" y: " + y + " yc: " + yc + " cycle400: " + cycle400 + " cycle100: " + cycle100 + " cycle4: " + cycle4 + " dy: " + rest + " time: " + new Date(time)+ "StartYear: "+getStartYear()+" StartTime: "+getStartTime());
        return md;

    }

    

    @Override
    public PeriodType getYearType(int bd) {
        return isLeapYear(bd) ? JulianCalendar.Y_LEAP : JulianCalendar.Y_NORMAL;
    }

    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(new BasicYear[]{JulianCalendar.Y_LEAP, JulianCalendar.Y_NORMAL});
    }

}
