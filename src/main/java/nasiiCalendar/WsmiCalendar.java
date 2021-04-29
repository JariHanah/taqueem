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
 * @author DEll
 */
public class WsmiCalendar extends LunerCalendar{
    private final static int STARTDATE=-105 ;//- 4000 * 19 * 18;
    private final static long STARTLONG= START_SAMI ;//-METONIC*4000*18 + START_SAMI;

    WsmiCalendar(ZoneId zone) {
        super(BasicCalendar.WSMI_ID, METONIC, 19, STARTLONG, STARTDATE, zone);
        setupMap(new int[]{1,0,0,1,0,0,1,0,1,0,0,1,0,0,1,0,1,0,0});
    }
    
    @Override
    public boolean isLeapYear(int bd) {
       int cycle=bd-getStartYear()+getMatchYear();
 
        return (cycle*7+15)%19<7;
    }

    @Override
    public BasicDate calculateDate(int year1, int month, int day){
        month--;
        day--;
        int year=year1-getStartYear();
        
        int cycles19 = (int) (year/19);
        year = year % 19;
        //hussam.println("longC: "+longCycles+" C19: "+cycles19 + " year: "+year);
        long time =  cycles19* METONIC + (long)LUNER_YEAR_DURATION.get(year) + month*MONTH + DAY * day + getStartTime();
        if(!isDateInRange(time))return getMaximumDate();

        MyDate sd= new MyDate(cleanDate(time), ++day, ++month, year1, this);
        return sd;
        
    }
    
    public BasicDate getDate(long t){
        t=cleanDate(t);
        if(!isDateInRange(t))return getMaximumDate();

        long rest = t-getStartTime();
        
        int cycles = (int) (rest / METONIC);
        rest = rest - cycles*METONIC;
    //   hussam.println("rest:"+rest/MONTH);
        int years = 0;
        Map.Entry<Long, Integer> floorEntry = LUNER_YEAR.floorEntry(rest);
        if (floorEntry != null)years= floorEntry.getValue();
        
        rest = rest - floorEntry.getKey();
        
        int months=(int)(rest / MONTH);
        
        rest = rest - months*MONTH;
        
        int days= (int)(rest/DAY);
        
        int year = cycles * 19 + years  +getStartYear();
     //   hussam.println("t: "+new Date(t)+" cycles: "+cycles+ " floor:"+floorEntry+ " rest: "+rest+ " Days:"+(rest/DAY)+" years:"+rest/DAY/365);
        return getDate(year, ++months, ++days);
        //MyDate sm = new MyDate(t, ++days, ++months, year, this);
        //return sm;
    }

    @Override
    public PeriodType getYearType(int bd) {
        int year=bd-getStartYear();
        int c19=year%19;
        BasicYear r=SamiCalendar.Y_NORMAL;
        switch(c19){
            case 0:case 8:case 16:r= SamiCalendar.Y13;break;
            case 3:case 11:r= SamiCalendar.Y9;break;
            case 6:case 14:r= SamiCalendar.Y5;break;
            default:r= SamiCalendar.Y_NORMAL;break;
            
        }
     //   hussam.println(bd+" "+r+" "+year+" "+c19);
        return r;
    }

    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(new BasicYear[]{SamiCalendar.Y13, SamiCalendar.Y5, SamiCalendar.Y_NORMAL, SamiCalendar.Y9});
    }
    
    
}
