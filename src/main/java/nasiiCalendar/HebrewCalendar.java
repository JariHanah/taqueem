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
import static nasiiCalendar.BasicCalendar.DAY;
import static nasiiCalendar.BasicCalendar.METONIC;
import static nasiiCalendar.BasicCalendar.MONTH;

/**
 *
 * @author Hussam
 */
public class HebrewCalendar extends LunerCalendar {

    int startDate = 4275;//- 4000 * 353; // 45925228800000L;
    private static final long START_LONG =-45925325999000L+3*HOUR;//START_SAMI - 17 * MONTH ;//-METONIC353*4000 + ;
    static final BasicYear Y_NORMAL=new BasicYear("LunerYear",false, false, new PeriodType[]{
            Months.TISHREI, Months.CHESHVAN, Months.KISLEV, Months.TEVET, Months.SHEVAT, Months.ADAR,
            Months.NISAN, Months.IYAR, Months.SIVAN, Months.TAMMUZ, Months.AV, Months.ELUH});
    static final BasicYear Y_LEAP=new BasicYear("LunerLeap", false, true, new PeriodType[]{
            Months.TISHREI, Months.CHESHVAN, Months.KISLEV, Months.TEVET, Months.SHEVAT, Months.ADAR, Months.ADAR2,
            Months.NISAN, Months.IYAR, Months.SIVAN, Months.TAMMUZ, Months.AV, Months.ELUH});
    HebrewCalendar(ZoneId zone) {
        super(BasicCalendar.HEWBREW_ID, METONIC, 19, START_LONG, 4275, zone);
        setupMap(new int[]{1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0});
    }
/*
    public enum HebrewYears implements PeriodType {
        YNormal("Luner Year", false, new PeriodType[]{Months.TISHREI, Months.CHESHVAN, Months.KISLEV, Months.TEVET, Months.SHEVAT, Months.ADAR,
            Months.NISAN, Months.IYAR, Months.SIVAN, Months.TAMMUZ, Months.AV, Months.ELUH}),
        Y13("Luner Year", true, new PeriodType[]{Months.TISHREI, Months.CHESHVAN, Months.KISLEV, Months.TEVET, Months.SHEVAT, Months.ADAR, Months.ADAR2,
            Months.NISAN, Months.IYAR, Months.SIVAN, Months.TAMMUZ, Months.AV, Months.ELUH}),;
        String engName, araName;
        List<PeriodType> months;
        boolean leap;
        int length;

        HebrewYears(String en, boolean ar, PeriodType[] months) {
            engName = en;
            leap = ar;
            this.months = Arrays.asList(months);
            for (PeriodType p : months) {
                length += p.getTotalLengthInDays();
            }
        }

        @Override
        public String getName() {
            return engName;
        }

        @Override
        public List<PeriodType> getSubPeriods() {
            return new ArrayList<PeriodType>(months);
        }

        @Override
        public boolean isBigLeap() {
            return leap;
        }

        @Override
        public boolean isSmallLeap() {
            return false;
        }

        @Override
        public String getShortName() {
            return getName();
        }

        @Override
        public int getTotalLengthInDays() {
            return length;
        }

        @Override
        public long getDurationInTime() {
            return getTotalLengthInDays() * DAY;
        }

        @Override
        public int getCountAsPeriods() {
            return 1;
        }

    }
//*/
    @Override
    public boolean isLeapYear(int year) {
        // MOD(O27*7+15,19)<7
        return (year * 7 + 15) % 19 < 7;
    }

    @Override
    public BasicDate calculateDate(int year1, int month, int day) {
        month--;
        day--;
        int year = year1 - getStartYear();
        int cycles19 = (int) (year / 19);
        year = year % 19;

        long time = cycles19 * METONIC + (long) LUNER_YEAR_DURATION.get(year) + month * MONTH + DAY * day + getStartTime();
        if (!isDateInRange(time)) {
            return getMaximumDate();
        }

        MyDate sd = new MyDate(cleanDate(time), ++day, ++month, year1, this);

        //  hussam.println(" C19: "+cycles19 + " year: "+year1 + " m:"+month+" d:"+day +" date: "+new Date(time));
        return sd;

    }

    public BasicDate getDate(long t) {
        t = cleanDate(t);
        if (!isDateInRange(t)) {
            return getMaximumDate();
        }

        long rest = t - getStartTime();
        int cycles = (int) (rest / METONIC);
        rest = rest - cycles * METONIC;
        //      hussam.println("rest:"+rest);
        int years = 0;
        Map.Entry<Long, Integer> floorEntry = LUNER_YEAR.floorEntry(rest);
        if (floorEntry != null) {
            years = floorEntry.getValue();
        }
        //    hussam.println("START "+new Date(START_SAMI)+" t: "+new Date(t)+" cycles: "+cycles+ " floor:"+floorEntry+ " rest: "+rest+ " Days:"+(rest/DAY)+" years:"+rest/DAY/365);
        //    hussam.println(LUNER_YEAR);
        rest = rest - floorEntry.getKey();

        int months = (int) (rest / MONTH);

        rest = rest - months * MONTH;

        int days = (int) (rest / DAY);

        int year = cycles * 19 + years + getStartYear();
        return getDate(year, ++months, ++days);
        //MyDate sm = new MyDate(t, ++days, ++months, year, this);
        //return sm;
    }

    @Override
    public PeriodType getYearType(int bd) {
        int year = bd - getStartYear();
        int c19 = year % 19;
        switch (c19) {
            case 0:
            case 3:
            case 6:
            case 8:
            case 11:
            case 14:
            case 17:
                return Y_LEAP;
            default:
                return Y_NORMAL;

        }
    }

    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(new BasicYear[]{Y_LEAP, Y_NORMAL});
    }

}
