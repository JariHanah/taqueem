/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import static nasiiCalendar.BasicCalendar.DAY;

/**
 *
 * @author DEll
 */
public class Solar128Calendar extends JulianCalendar{
    static final long CYCLE128 = DAY * ((365 * 3 + 366) * 32 - 1);
    static final long CYCLE4 = DAY * (365 * 3 + 366);
//    private static final int STARTYEAR=-4000000-78;
//    private static final long STARTTIME=-CYCLE128*31266;
    private static final int BASE_YEAR = 2001;//- 353*4000;
    private static final long BASE_TIME =978307200000L;// 978382799999L-10800000L;//-126289975230000000l;//-62135521200001l ;//- DAY*4000 * (((365*3+366)*100)-3);
    
    public Solar128Calendar() {
        super(BasicCalendar.SOLAR_128_ID, CYCLE128, 128, BASE_TIME, BASE_YEAR);
    }
    
    

    
    
    @Override
    public BasicDate getDate(long t) {
        long time2 = CalendarFactory.cleanDate(t);
        if(!isDateInRange(time2))return getMaximumDate();
        
        long time = time2 - getStartTime();
        if(time<0){
            System.err.println("strange: "+getName());
            System.err.println("\tsatrt: "+getStartTime()+" start: "+new Date(getStartTime()));
            System.err.println("\tselec: "+time2+" start: "+new Date(time2));
            System.err.println("\trange: "+time+" start: "+new Date(time));
            
        }
        int cycle128 = (int) (time / CYCLE128);
        long rest = time - cycle128 * CYCLE128;
        int cycle4 = (int) (rest / CYCLE4);
        rest = rest - (cycle4 * CYCLE4);
        int years = (int) (rest / (365 * DAY));
        rest = rest - years * ((365) * DAY);
        int y1 = cycle128 * 128 + cycle4 * 4 + years + getStartYear();
    //    System.out.println("y1: "+y1+ " rest: "+rest/DAY);
        Map.Entry<Long, Integer> monthKey = isLeapYear(y1) ? LEAP_YEAR.floorEntry(rest) : NORMAL_YEAR.floorEntry(rest);
        rest = rest - monthKey.getKey();
        int month = monthKey.getValue();
        int day = (int) (rest / DAY);
     //   hussam.println("foirst y1: " + y1 + " m:" + (month + 1) + " d:" + (day + 1));

        BasicDate d = getDate(y1, month + 1, day + 1);

    //    hussam.println("second: " + d + "time: " + new Date(d.getDate()));

        return d;
    }

    public boolean isLeapYear(int year) {
        if(year%128==0)return false;
        return year % 4 == 0;
    }

    @Override
    public BasicDate calculateDate(int y, int m, int d) {
        int yc = y - getStartYear();
        int cycle128 = yc / 128;
        int rest = yc - cycle128 * 128;
        
        int cycle4 = rest / 4;
        rest = rest - cycle4 * 4;

        long time = cycle128 * CYCLE128 + cycle4 * CYCLE4 + rest * 365 * DAY;
        time += (d - 1) * DAY;
        TreeMap<Integer, Long> dur = null;
        if (isLeapYear(y)) {
            dur = LEAP_YEAR_DURATION;
        } else {
            dur = NORMAL_YEAR_DURATION;
        }
        time += dur.get(m - 1) + getStartTime();
        //hussam.println("y: " + y + " yc: " + yc + " cycle400: " + cycle400 + " cycle100: " + cycle100 + " cycle4: " + cycle4 + " dy: " + rest + " time: " + new Date(time));
        if(!isDateInRange(time))return getMaximumDate();
        MyDate md = new MyDate(time, d, m, y, this);
        return md;

    }


    
}
