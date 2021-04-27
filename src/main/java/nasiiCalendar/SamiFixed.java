/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static nasiiCalendar.BasicCalendar.DAY;
import static nasiiCalendar.BasicCalendar.START_SAMI;

public class SamiFixed extends SolarCalendar implements BasicCalendar {

    private static final int BIG_CYCLE = 353 * 382;
    private static final int STARTDATE = -105;
    static int L53 = 18709;
    static int L64 = 22592;
    static int L9 = 3177;
    static int L11 = 3883;

    //   public static final long METONIC353 = 128931 * DAY;//MONTH * 4366;
    public static long LONG_CYCLES = 4255308864000000L;//11650694400000L;
    private static final long STARTTIME = START_SAMI+1*DAY;

    PeriodStore store;
    static final BasicYear Y0_N = new BasicYear("LunerYear", false, false,
            new PeriodType[]{new LeapMonth(30, Months.SAFAR), Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
                Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA});
    static final BasicYear Y0_L = new BasicYear("LunerLeap",  true, false,
            new PeriodType[]{new LeapMonth(30, Months.SAFAR), Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
                Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, new LeapMonth(30, Months.THO_HIJA)});
    static final BasicYear Y13 = new BasicYear("samiharam13", false, true,
            new PeriodType[]{new LeapMonth(30, Months.SAFAR), Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
                Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA, Months.ALHARAM});
    static final BasicYear Y9 = new BasicYear("samiharam09", false, true,
            new PeriodType[]{new LeapMonth(30, Months.SAFAR), Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
                Months.RAJAB, Months.SHAABAN, Months.RAJAB_MODHAR, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA});
    static final BasicYear Y5 = new BasicYear("samiharam05", false, true,
            new PeriodType[]{new LeapMonth(30, Months.SAFAR), Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.RAJAB_RABEEIAH, Months.JAMAD1, Months.JAMAD2,
                Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA});

    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(new BasicYear[]{Y0_N, Y0_L, Y13, Y9, Y5});
    }

    @Override
    public PeriodType getYearType(int year) {
        int cBig = (year-getStartYear()) % BIG_CYCLE;
        PeriodType p1= store.getSubPeriod(cBig).getValue();
       // hussam.println("Type: "+p1);
        return p1;
            }

    SamiFixed() {
        this(BasicCalendar.SAMI_FIXED_ID, LONG_CYCLES, BIG_CYCLE, STARTTIME, STARTDATE);
        
    }

    static PeriodType[] getLeapCentury() {
        PeriodType[] types = getNormalCentury();
        types[353 - 2] = Y0_L;
        return types;
    }

    static PeriodType[] getNormalCentury() {
        PeriodType[] types = new PeriodType[353];
        for (int i = 0; i < 19; i++) {
            int x = 0;
            types[i * 19 + x++] = Y13;
            types[i * 19 + x++] = Y0_N;
            types[i * 19 + x++] = Y0_N;
            types[i * 19 + x++] = Y9;
            types[i * 19 + x++] = Y0_L;
            types[i * 19 + x++] = Y0_N;
            types[i * 19 + x++] = Y5;
            types[i * 19 + x++] = Y0_N;
            types[i * 19 + x++] = Y13;
            types[i * 19 + x++] = Y0_N;
            types[i * 19 + x++] = Y0_L;
            if (i * 19 + x >= 352) {
                break;
            }
            types[i * 19 + x++] = Y9;
            types[i * 19 + x++] = Y0_L;
            types[i * 19 + x++] = Y0_N;
            types[i * 19 + x++] = Y5;
            types[i * 19 + x++] = Y0_N;
            types[i * 19 + x++] = Y13;
            if (i % 3 == 0) {
                types[i * 19 + x++] = Y0_N;
            } else {
                types[i * 19 + x++] = Y0_L;
            }
            types[i * 19 + x++] = Y0_N;

        }
        return types;
    }

    void prepareYearTypes() {
//        hussam.println(Y0_L);
        PeriodType[] leap = getLeapCentury();
        PeriodType[] normal = getNormalCentury();
        int[] lengths = new int[353];
        Arrays.fill(lengths, 1);
        PeriodStore p353l = new PeriodStore(false, false, leap);
        PeriodStore p353n = new PeriodStore(true, false, normal);
        
        
        PeriodType[] c11 = new PeriodType[11];
        PeriodType[] c9 = new PeriodType[9];
        int i = 0;
        c11[i++] = p353n;
        c11[i++] = p353l;
        c9[i - 2] = c11[i++] = p353n;
        c9[i - 2] = c11[i++] = p353l;
        c9[i - 2] = c11[i++] = p353n;
        c9[i - 2] = c11[i++] = p353l;
        c9[i - 2] = c11[i++] = p353n;
        c9[i - 2] = c11[i++] = p353l;
        c9[i - 2] = c11[i++] = p353n;
        c9[i - 2] = c11[i++] = p353l;
        c9[i - 2] = c11[i++] = p353l;
        lengths = new int[11];
        Arrays.fill(lengths, 353);
        PeriodStore p11 = new PeriodStore(false, false, c11);
        PeriodStore p9 = new PeriodStore(false, true, c9);
        PeriodType[] c64 = new PeriodType[6];
        PeriodType[] c53 = new PeriodType[5];
        i = 0;
        c64[i++] = p11;
        c53[i - 1] = c64[i++] = p11;
        c53[i - 1] = c64[i++] = p11;
        c53[i - 1] = c64[i++] = p11;
        c53[i - 1] = c64[i++] = p11;
        c53[i - 1] = c64[i++] = p9;
        lengths = new int[6];
        Arrays.fill(lengths, 11 * 353);
        lengths[5] = 9 * 353;
        PeriodStore p64 = new PeriodStore(false, true, c64);
        lengths = Arrays.copyOfRange(lengths, 1, 5);
        PeriodStore p53 = new PeriodStore(false, false, c53);
        PeriodType[] c382 = new PeriodType[7];
        i = 0;
        c382[i++] = p53;
        c382[i++] = p53;
        c382[i++] = p53;
        c382[i++] = p53;
        c382[i++] = p53;
        c382[i++] = p53;
        c382[i++] = p64;
        lengths = new int[7];
        Arrays.fill(lengths, p53.getTotalLengthInDays());
        lengths[6] = p64.getTotalLengthInDays();
        store = new PeriodStore(false, false, c382);
    //    LONG_CYCLES = store.getTotalLengthInDays() * DAY;
        int L53 = p53.getCountAsPeriods();
        int L64 = p64.getCountAsPeriods();
        int L9 = p9.getCountAsPeriods();
        int L11 = p11.getCountAsPeriods();
        
    }

    private SamiFixed(String eng,long cycleTime, int cycleYear, long matchTime, int matchYear) {
        super(eng, cycleTime, cycleYear, matchTime, matchYear);
       
        prepareYearTypes();
        
    }

    @Override
    public boolean isLeapYear(int bd) {

        int cycle = bd - getStartYear() + getMatchYear();

        return (130 * (cycle) + 291) % 353 < 130;

    }

    @Override
    public int getMonthLength(BasicDate bd) {
        return bd.getCalendar().getYearType(bd).getSubPeriods().get(bd.getMonth() - 1).getTotalLengthInDays();
        //  return getYearType(bd.getYear()).getStore().getLengths()[bd.getMonth()];
    }
    long change=0;
    @Override
    public BasicDate calculateDate(int year1, int month, int day) {
        month--;
        day--;
        int years = year1 - getStartYear();
        int c382_353 = years / store.getCountAsPeriods();
        years -= c382_353 * store.getCountAsPeriods();
        //    hussam.println("year1: "+year1+" yearsDur:"+years +" cyc:"+c382_353);
        Holder all = new Holder();
        all.periodDuration = years;
        all.monthDuration = month;
        all.dayDuration = day;
     //   hussam.println("WHAT "+all);
        store.calcTimes(all);
         
        long t1 = getStartTime() + c382_353 * LONG_CYCLES;
        //     hussam.println("\tbefore adding parts: "+new Date(t1));
        //     hussam.println("\tAfter              : "+new Date(t1+all.timeDuration));
        long time = t1 + all.timeDuration;
        //System.err.println("start:\t"+time+"\t'=\t"+getStartTime()+"\t"+c382_353+"\t"+LONG_CYCLES+"\t"+all.timeDuration);
       time = CalendarFactory.cleanDate(time);
        MyDate sd = new MyDate(time, ++day, ++month, year1, this);
        //hussam.print("calc: ");
        //  printSimpleMD(time);
        if (!isDateInRange(sd)) {
            return getMaximumDate();
        }
        return sd;
    }

    public BasicDate getDate(long t) {
        if (!isDateInRange(t)) {
            //    hussam.println("something wrong!");
            return getMaximumDate();
        }
        t = CalendarFactory.cleanDate(t);
        long rest = t - getStartTime();
        //    hussam.println(getLongCycleTime()/DAY/366);
        int bigCycles = (int) (rest / getLongCycleTime());
        rest = rest % getLongCycleTime();
        Holder myDate = new Holder();
        myDate.timeDuration = rest;
        myDate = store.calcPeriod(myDate);
        int base = bigCycles * getLongCycleYear() + getStartYear();
        int year = base + myDate.periodDuration;
        //    hussam.println(bigCycles+" "+  getStartYear() +" "+"Base: "+base+" AddY:"+year+" "+"Holder: "+myDate+" rest:"+myDate.timeDuration/DAY);
        BasicDate s = getDate(year, ++myDate.monthDuration, ++myDate.dayDuration);
        return s;

    }

    @Override
    public int getYearLength(BasicDate bd) {
        return getYearType(bd).getTotalLengthInDays(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BasicDate nextDay(BasicDate bd) {
        return getDate(bd.getDate() + DAY);
    }

    public String toString() {
        return "SamiFixed";
    }
}
