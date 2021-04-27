/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static nasiiCalendar.BasicCalendar.DAY;
import static nasiiCalendar.BasicCalendar.MONTH;
import static nasiiCalendar.SamiCalendar.METONIC353;
import static nasiiCalendar.SamiCalendar.START_SAMI;

/**
 *
 * @author Hussam
 */
@Deprecated
public class OmariCalendar extends LunerCalendar {

    //int startDate=-109;
    public final static int STARTDATE = 1442;//-1455442;
    public final static long STARTLONG = 1597881600000L;// 1597957199999L-10800000L;//-METONIC353 * 4000 + START_SAMI +1 * MONTH;
    final BasicYear Y_NORMAL=new BasicYear("Luner Year",  false, false, new PeriodType[]{Months.MUHARRAM, Months.SAFAR, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA});
    final BasicYear Y_LEAP=new BasicYear("Luner Leap",  true, false, new PeriodType[]{Months.MUHARRAM, Months.SAFAR, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, new LeapMonth(30,Months.THO_HIJA)});
    /*
    public enum OmariYears implements PeriodType {
        Y0("Luner Year", new PeriodType[]{Months.MUHARRAM, Months.SAFAR, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA}),
        Y1("Luner Year",  new PeriodType[]{Months.MUHARRAM, Months.SAFAR, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA});
        String engName;
        List<PeriodType> months;
        OmariYears(String en,  PeriodType[] months) {
            engName = en;
            this.months = Arrays.asList(months);
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
    }
//*/
    @Override
    public BasicDate calculateDate(int year1, int month, int day) {
        month--;
        day--;
        int year = year1 - getStartYear();
        long time = year * MONTH * 12 + month * MONTH + DAY * day + getStartTime();
        if(!isDateInRange(time))return getMaximumDate();
        time=CalendarFactory.cleanDate(time);
        MyDate sd = new MyDate(time, ++day, ++month, year1, this);
        return sd;
    }
    @Override
    public BasicDate getDate(long time2) {
     //   hussam.println("CALLING.........................");
        long time = CalendarFactory.cleanDate(time2);
        if(!isDateInRange(time))return getMaximumDate();

        long rest = time - (getStartTime());
        int years = (int) (rest / (MONTH * 12));
        rest = rest % (MONTH * 12);
        int months = (int) (rest / MONTH);

        rest = rest % MONTH;
        int days = (int) (rest / DAY);
        return getDate(years + getStartYear(), months + 1, days + 1);

    }

    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(new BasicYear[]{Y_LEAP, Y_NORMAL});
    }

    OmariCalendar() {
        super(BasicCalendar.OMARI_ID,MONTH*12, 1, STARTLONG, STARTDATE);
        setupMap(new int[]{0});
    }

    @Override
    public boolean isLeapYear(int bd) {
        return false;
    }

    @Override
    public PeriodType getYearType(int bd) {
        long dur = bd- getStartYear();
        int x = (int) (dur / (12 * MONTH));
        int d = (int) (((x + 1) * MONTH * 12 - (x * MONTH * 12)) / DAY);
        //hussam.println(bd.getYear() + " this Omari year is: " + d + " Days");
        if (d == 354) {
            return Y_NORMAL;
        }
        return Y_LEAP;
    }

    public String toString() {
        return "OmariC";
    }

}
