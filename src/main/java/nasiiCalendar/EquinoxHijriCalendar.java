/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import static nasiiCalendar.BasicCalendar.DAY;
import static nasiiCalendar.BasicCalendar.START_SAMI;

public abstract class EquinoxHijriCalendar extends SolarCalendar implements BasicCalendar {

    SamiFixed sami;
    JalaliCalendarIR jal;
    static final BasicYear Y0_N = new BasicYear("LunerYear", false, false,
            new PeriodType[]{new LeapMonth(30, Months.SAFAR), Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
                Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA});
    static final BasicYear Y0_L = new BasicYear("LunerLeap", true, false,
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
        BasicDate rab1=sami.getDate(year, 3, 1);
        
        BasicDate hamalYear=jal.getDate(rab1.getDate()+60*DAY);
        hamalYear=jal.getDate(hamalYear.getYear(), 1, 1);
        if(rab1.getDate()>hamalYear.getDate()){
            return Y5;
        }
        BasicDate shaaban=sami.getDate(year, 8, 29);
        BasicDate mizan=jal.getDate(hamalYear.getYear(), 7, 1);
        if(shaaban.getDate()>mizan.getDate()){
            return Y5;
        }
        
        return Y0_N;
    }

    

    

    /*
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
//*/
    private EquinoxHijriCalendar(String eng, long cycleTime, int cycleYear, long matchTime, int matchYear, ZoneId zone) {
        super(eng, cycleTime, cycleYear, matchTime, matchYear, zone);

     //   prepareYearTypes();

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
    long change = 0;

    
    
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
