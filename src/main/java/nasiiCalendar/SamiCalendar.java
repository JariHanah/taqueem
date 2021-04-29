/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Hussam 
 * @Deprecated
 * @param <SamiDate>
 */
@Deprecated
public class SamiCalendar extends LunerCalendar implements BasicCalendar{
    private static final int STARTDATE=-105 ;
    public static final long METONIC353=MONTH * 4366;
    private static final long STARTTIME =  START_SAMI;
    
   static final BasicYear Y_NORMAL=new BasicYear("Luner Year", false, false, new PeriodType[] {
            Months.SAFAR, Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2, 
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA});
   static final BasicYear Y5=new BasicYear("Rabeah Leap Year",  false, true, new PeriodType[] {
            Months.SAFAR, Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.RAJAB_RABEEIAH, Months.JAMAD1, Months.JAMAD2, 
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA});
   static final BasicYear Y9=new BasicYear("Modhar Leap Year", false, true, new PeriodType[] {
            Months.SAFAR, Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2, 
            Months.RAJAB, Months.SHAABAN, Months.RAJAB_MODHAR, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA});
   static final BasicYear Y13=new BasicYear("Big Hajj Leap Year",  false, true, new PeriodType[] {
            Months.SAFAR, Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2, 
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA, Months.ALHARAM});
   
    
    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(new BasicYear[]{Y_NORMAL, Y13, Y9, Y5});
    }

    @Override
    public PeriodType getYearType(int year) {
        
        int c353=(year-getStartYear())%353;
        int c19=c353%19;
        switch(c19){
            case 0:case 8:case 16:return Y13;
            case 3:case 11:return Y9;
            case 6:case 14:return Y5;
            default:return Y_NORMAL;
            
        }
    }
/*
    public enum SamiYears implements PeriodType{
            Y0("Luner Year", new PeriodType[] {Months.SAFAR, Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2, 
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA}),
            Y13("Big Hajj Leap Year", new PeriodType[] {Months.SAFAR, Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2, 
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA, Months.ALHARAM}),
            Y9("Modhar Leap Year",  new PeriodType[] {Months.SAFAR, Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2, 
            Months.RAJAB, Months.SHAABAN, Months.RAJAB_MODHAR, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA}),
            Y5("Rabeah Leap Year",  new PeriodType[] {Months.SAFAR, Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.RAJAB_RABEEIAH, Months.JAMAD1, Months.JAMAD2, 
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA}),
        ;
            String engName;
            List<PeriodType>months;
            SamiYears(String en, PeriodType[]months){
                engName=en;
                
                this.months=Arrays.asList(months);
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
    SamiCalendar(ZoneId zone) {
        super(BasicCalendar.SAMI_ID,  METONIC353, 353, STARTTIME, STARTDATE, zone);
        setupMap(new int[]{1,0,0,1,0,0,1,0,1,0,0,1,0,0,1,0,1,0,0});
    }
   
    @Override
    public boolean isLeapYear(int bd) {

      int cycle=bd-getStartYear()+getMatchYear();
 
      return (130*(cycle)+291)%353<130;
        
    }
    private static String printSimpleMD(long t){
        int m=(int) (t/MONTH);
        long ext=(long)(t%MONTH);
        int d=(int) ((t%MONTH)/DAY);
        return "M: "+ m+" D: "+d+" ex:"+ext+" "+MONTH;
    }
    
    @Override
    public BasicDate calculateDate(int year1, int month, int day){
        month--;
        day--;
        int year=year1-getStartYear();
        int longCycles=(int)(year/353);
        year = year % 353;
        int cycles19 = (int) (year/19);
        year = year % 19;
        //hussam.println("longC: "+longCycles+" C19: "+cycles19 + " year: "+year);
        
        long time = longCycles*METONIC353 + cycles19* METONIC + (long)LUNER_YEAR_DURATION.get(year) + month*MONTH + DAY * day + getStartTime();
        time=cleanDate(time);
        MyDate sd= new MyDate(time, ++day, ++month, year1, this);
        //hussam.print("calc: ");
      //  printSimpleMD(time);
        if(!isDateInRange(sd))return getMaximumDate();
        return sd;
    }
    
    public BasicDate getDate(long t){
    
        if(!isDateInRange(t)){
        //    hussam.println("something wrong!");
            return getMaximumDate();
        }
        t=cleanDate(t);
        long rest = t-getStartTime();

        int bigCycles = (int) (rest / METONIC353);
        rest = rest%METONIC353;
          int cycles = (int) (rest / METONIC);
        rest = rest%METONIC;
        
         int years = 0;
        Entry<Long, Integer> floorEntry = LUNER_YEAR.floorEntry(rest);
        if (floorEntry != null)years= floorEntry.getValue();
        rest = rest - floorEntry.getKey();
        int months=(int)(rest / MONTH);
        
        rest = rest%MONTH;
        
 //       hussam.println("\tmnth: "+rest/DAY + " months: "+months+" MONTH: "+MONTH);
        
 //       hussam.print("\tgetD: ");
 //       printSimpleMD(rest);
        int days= (int)(rest/DAY);
        rest = rest%DAY;
        int year = bigCycles *353 + cycles * 19 + years +getStartYear();
        BasicDate s= getDate(year, ++months, ++days);
       return s;
        //return sm;
    }
    @Override
    public BasicDate nextDay(BasicDate bd) {
        return getDate(bd.getDate()+DAY);
    }

    public String toString(){
       return "SamiC" ;
    }
}
