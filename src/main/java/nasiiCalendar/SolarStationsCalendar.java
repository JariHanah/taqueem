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

/**
 *
 * @author Hussam
 */
public class SolarStationsCalendar extends SolarCalendar {
    private static final long STARTTIME=981763200000L+DAY;//978382799999L-10800000L+40*DAY;
    private static final int STARTYEAR=2001;
    BasicYear Y_NORMAL=new BasicYear("JulianYear", false, false, new PeriodType[]{
            Months.L01, Months.L02, Months.L03, Months.L04, Months.L05, Months.L06, Months.L07, Months.L08, Months.L09, Months.L10,
            Months.L11, Months.L12, Months.L13, Months.L14, Months.L15, Months.L16, Months.L17, Months.L18, Months.L19, Months.L20,
            Months.L21, Months.L22, Months.L23, Months.L24, Months.L25, Months.L26, Months.L27, Months.L28});
    BasicYear Y_LEAP=new BasicYear("JulianLeap", true, false, new PeriodType[]{
            Months.L01, Months.L02, Months.L03, Months.L04, Months.L05, Months.L06, Months.L07, Months.L08, Months.L09, Months.L10,
            Months.L11, Months.L12, Months.L13, Months.L14, Months.L15, Months.L16, Months.L17, Months.L18, Months.L19, Months.L20,
            Months.L21, Months.L22, Months.L23, Months.L24, new LeapMonth(14,Months.L25), Months.L26, Months.L27, Months.L28});
    /*
    enum SolarStationsYearType implements PeriodType {
        YNormal("Stations Year", new PeriodType[]{
            Months.L01, Months.L02, Months.L03, Months.L04, Months.L05, Months.L06, Months.L07, Months.L08, Months.L09, Months.L10,
            Months.L11, Months.L12, Months.L13, Months.L14, Months.L15, Months.L16, Months.L17, Months.L18, Months.L19, Months.L20,
            Months.L21, Months.L22, Months.L23, Months.L24, Months.L25, Months.L26, Months.L27, Months.L28}),
        YLeap("Stations Leap Year", new PeriodType[]{
            Months.L01, Months.L02, Months.L03, Months.L04, Months.L05, Months.L06, Months.L07, Months.L08, Months.L09, Months.L10,
            Months.L11, Months.L12, Months.L13, Months.L14, Months.L15, Months.L16, Months.L17, Months.L18, Months.L19, Months.L20,
            Months.L21, Months.L22, Months.L23, Months.L24, new LeapMonth(14,Months.L25), Months.L26, Months.L27, Months.L28});

        PeriodType[] months;
        String eng;

        private SolarStationsYearType(String eng, PeriodType[] months) {
            this.eng = eng;
            this.months = months;
        }

        @Override
        public String getName() {
            return eng;
        }


        @Override
        public List<PeriodType> getSubPeriods() {
            return Arrays.asList(months);
        }

        @Override
        public int[] getPeriodLengths() {
            return null;
        }

        @Override
        public boolean isBigLeap() {
            return false;
        }

        @Override
        public boolean isSmallLeap() {
            return false;
        }
        
        @Override
        public PeriodStore getStore() {
            return null;
        }

        @Override
        public String getShortName() {
            return getName();
        }

        @Override
        public int getTotalLengthInDays() {
            return days;
        }

        @Override
        public long getDurationInTime() {
            return getTotalLengthInDays()*DAY;
        }
        
    }
//*/
    public SolarStationsCalendar(ZoneId zone) {
        super(BasicCalendar.SOLAR_STATIONS_ID,  BasicCalendar.YEAR_SIDEREAL*4,4,STARTTIME, STARTYEAR, zone);
    }
    
    
    @Override
    public int getMonthLength(BasicDate bd) {
        if(isLeapYear(bd) && bd.getMonth()==25)return 14;
        if(bd.getMonth()==17)return 14;
        return 13;
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
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 14, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);
        x = addLength(x + 13, month++);

        month = 0;
        x = 0;
        x = addLeapLength(x, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 14, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 14, month++);
        x = addLeapLength(x + 13, month++);
        x = addLeapLength(x + 13, month++);

    }

    

    

    @Override
    public BasicDate getDate(long t) {
        long time = CalendarFactory.cleanDate(t);
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
        if(!isDateInRange(time))return getMaximumDate();

        MyDate md = new MyDate(CalendarFactory.cleanDate(time), d, m, y, this);
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
