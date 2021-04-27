/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.time.ZoneId;
import java.util.ArrayList;
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
public class ByroniCalendar extends LunerCalendar {

    int startDate = -105;//- 4000 * 18*19;
    static final long BYRONI_START = -45866649599000L;

    ByroniCalendar(ZoneId zone) {
        super(BasicCalendar.BYRONI_ID,  METONIC, 19, BYRONI_START, -105, zone);//START_SAMI+6*MONTH
        setupMap(new int[]{1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0});
    }

    public enum ByroniYears implements PeriodType {
        YNormal("LunerYear", false, new PeriodType[]{
            Months.MUHARRAM, Months.SAFAR, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA}),
        Y13("haram13", true, new PeriodType[]{
            Months.MUHARRAM, Months.SAFAR, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA, Months.NASEI}),;
        String engName;
        List<PeriodType> months;
        boolean leap;
        int length;

        ByroniYears(String en, boolean ar, PeriodType[] months) {
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
        //hussam.println("longC: "+longCycles+" C19: "+cycles19 + " year: "+year);
        long time = cycles19 * METONIC + (long) LUNER_YEAR_DURATION.get(year) + month * MONTH + DAY * day + getStartTime();
        if (!isDateInRange(time)) {
            return getMaximumDate();
        }

        MyDate sd = new MyDate(CalendarFactory.cleanDate(time), ++day, ++month, year1, this);
        return sd;

    }

    public BasicDate getDate(long t) {
        t = CalendarFactory.cleanDate(t);
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
        //    hussam.println("t: "+t+" cycles: "+cycles+ " floor:"+floorEntry+ " rest: "+rest+ " Days:"+(rest/DAY)+" years:"+rest/DAY/365);
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
            case 16:
                //    hussam.println("BY: "+ByroniYears.Y13.getMonths());
                return ByroniYears.Y13;

            default:
                //    hussam.println("BY: "+ByroniYears.YNormal.getMonths());
                return ByroniYears.YNormal;

        }
    }

    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(ByroniYears.values());
    }

}
