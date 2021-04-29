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
import static nasiiCalendar.BasicCalendar.START_SAMI;

/**
 *
 * @author Hussam
 */
public class QazwiniCalendar extends LunerCalendar{
    private static final int startDate=-105 ;//- 4000 * 353;
    private static final long startLong= START_SAMI - 2*MONTH;//-METONIC353*4000 + ;
    private static final long METONIC_SMALL=MONTH*12*8+3*MONTH;
    final BasicYear Y_NORMAL=new BasicYear("LunerYear", false, false, new PeriodType[] {
                Months.RAMADHAN, Months.SHAWWAL, Months.JAMAD1, Months.JAMAD2, Months.RABEI1, Months.RABEI2, 
                Months.RAJAB, Months.THO_QIDAH, Months.THO_HIJA, Months.MUHARRAM, Months.SAFAR, Months.SHAABAN});
   final BasicYear Y5=new BasicYear("haram5", false, true, new PeriodType[] {
                Months.RAMADHAN, Months.SHAWWAL, Months.JAMAD1, Months.JAMAD2, Months.ALHARAM, Months.RABEI1, Months.RABEI2, 
                Months.RAJAB, Months.THO_QIDAH, Months.THO_HIJA, Months.MUHARRAM, Months.SAFAR, Months.SHAABAN});
   final BasicYear Y9=new BasicYear("haram9", false, true, new PeriodType[] {
                Months.RAMADHAN, Months.SHAWWAL, Months.JAMAD1, Months.JAMAD2, Months.RABEI1, Months.RABEI2, 
                Months.RAJAB, Months.THO_QIDAH, Months.ALHARAM, Months.THO_HIJA, Months.MUHARRAM, Months.SAFAR, Months.SHAABAN});
   final BasicYear Y13=new BasicYear("haram13", false, true, new PeriodType[] {
                Months.RAMADHAN, Months.SHAWWAL, Months.JAMAD1, Months.JAMAD2, Months.RABEI1, Months.RABEI2, 
                Months.RAJAB, Months.THO_QIDAH, Months.THO_HIJA, Months.MUHARRAM, Months.SAFAR, Months.ALHARAM, Months.SHAABAN});
   
    public QazwiniCalendar(ZoneId zone) {
        super(BasicCalendar.QAZWINI_ID, METONIC*8, 19*8,1067126400000L , 1383, zone);//1067201999999L-10800000L
        setupMap(new int[]{0,0,1,0,0,1,0,1});
    }
    
    @Override
    public boolean isLeapYear(int bd) {
       // MOD(O27*7+15,19)<7
        if((bd-1)%8==0)return true;
        if((bd-4)%8==0)return true;
        if((bd-6)%8==0)return true;
        
        return false;
    }

    @Override
    public BasicDate calculateDate(int year1, int month, int day){
        month--;
        day--;
        int year=year1-getStartYear();
        int cycle198=year/(8*19);
        year=year%(8*19);
        int cycle8 = (int) (year/8);
        year = year % 8;
        //hussam.println("longC: "+longCycles+" C19: "+cycles19 + " year: "+year);
        long time = cycle198* METONIC*8+cycle8* METONIC_SMALL + (long)LUNER_YEAR_DURATION.get(year) + month*MONTH + DAY * day + getStartTime();
        if(!isDateInRange(time))return getMaximumDate();

        MyDate sd= new MyDate(time, ++day, ++month, year1, this);
        return sd;
        
    }
    public BasicDate calculateDate2(int year1, int month, int day){
        month--;
        day--;
        int year=year1-getStartYear();
        int cycles19 = (int) (year/19);
        year = year % 19;
        //hussam.println("longC: "+longCycles+" C19: "+cycles19 + " year: "+year);
        long time = cycles19* METONIC + (long)LUNER_YEAR_DURATION.get(year) + month*MONTH + DAY * day + getStartTime();
        if(!isDateInRange(time))return getMaximumDate();

        MyDate sd= new MyDate(time, ++day, ++month, year1, this);
        return sd;
        
    }
    public BasicDate getDate(long t){
        t=cleanDate(t);
        if(!isDateInRange(t))return getMaximumDate();

        long rest = t-getStartTime();
        
        
        int cyclesLong = (int) (rest / (METONIC*8));
        rest = rest - cyclesLong*METONIC*8;
  //    
        
        int cycles = (int) (rest / METONIC_SMALL);
        rest = rest - cycles*METONIC_SMALL;
  //      hussam.println("rest:"+rest);
        int years = 0;
        Map.Entry<Long, Integer> floorEntry = LUNER_YEAR.floorEntry(rest);
        if (floorEntry != null)years= floorEntry.getValue();
    //    hussam.println("t: "+t+" bigCy:"+bigCycles+ " cycles: "+cycles+ " floor:"+floorEntry+ " rest: "+rest+ " Days:"+(rest/DAY)+" years:"+rest/DAY/365);
        rest = rest - floorEntry.getKey();
        
        int months=(int)(rest / MONTH);
        
        rest = rest - months*MONTH;
        
        int days= (int)(rest/DAY);
        
        int year =cyclesLong*8*19+ cycles * 8 + years  +getStartYear();
        return getDate(year, ++months, ++days);
        //MyDate sm = new MyDate(t, ++days, ++months, year, this);
        //return sm;
    }

    public BasicDate getDate2(long t){
        t=cleanDate(t);
        if(!isDateInRange(t))return getMaximumDate();

        long rest = t-getStartTime();
        
        int cycles = (int) (rest / METONIC);
        rest = rest - cycles*METONIC;
  //      hussam.println("rest:"+rest);
        int years = 0;
        Map.Entry<Long, Integer> floorEntry = LUNER_YEAR.floorEntry(rest);
        if (floorEntry != null)years= floorEntry.getValue();
    //    hussam.println("t: "+t+" bigCy:"+bigCycles+ " cycles: "+cycles+ " floor:"+floorEntry+ " rest: "+rest+ " Days:"+(rest/DAY)+" years:"+rest/DAY/365);
        rest = rest - floorEntry.getKey();
        
        int months=(int)(rest / MONTH);
        
        rest = rest - months*MONTH;
        
        int days= (int)(rest/DAY);
        
        int year = cycles * 19 + years  +getStartYear();
        return getDate(year, ++months, ++days);
        //MyDate sm = new MyDate(t, ++days, ++months, year, this);
        //return sm;
    }

    @Override
    public PeriodType getYearType(int bd) {
        if((bd-1)%8==0)return Y9;
        if((bd-4)%8==0)return Y5;
        if((bd-6)%8==0)return Y13;
        return Y_NORMAL;
        
    }

    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(new BasicYear[]{Y13, Y9, Y5, Y_NORMAL});
    }
    public String toString(){
       return "QazwC" ;
    }
    
}
