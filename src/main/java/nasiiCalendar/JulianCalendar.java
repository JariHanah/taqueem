/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import static nasiiCalendar.SamiCalendar.METONIC353;

/**
 *
 * @author DEll
 */
public class JulianCalendar extends SolarCalendar{
    final private static long BASE_TIME=-126292535737200000L-10800000L-6*HOUR;//126292567402800000l;//-62135694000001l ;//- DAY*(365*3+366) *4000;
    final private static int BASE_YEAR=-3999999 ;//- (365*3+366)*4000;
    private static final long CYCLELENGTH= (365*3+366)*DAY;
    
    static final BasicYear Y_NORMAL=new BasicYear("JulianYear", false, false, new PeriodType[]{Months.JANUARY, Months.FEBUARY, Months.MARCH, Months.APRIL, Months.MAY, Months.JUNE,
            Months.JULY, Months.AUGUST, Months.SEPTEMBER, Months.OCTOBER, Months.NOVEMBER, Months.DECEMBER});
    static final BasicYear Y_LEAP=new BasicYear("JulianLeap", true, false, new PeriodType[]{Months.JANUARY, new LeapMonth(29,Months.FEBUARY), Months.MARCH, Months.APRIL, Months.MAY, Months.JUNE,
            Months.JULY, Months.AUGUST, Months.SEPTEMBER, Months.OCTOBER, Months.NOVEMBER, Months.DECEMBER});
    
    JulianCalendar(){
        this(BasicCalendar.JULIAN_ID);
    }
    JulianCalendar(String eng){
        super(eng, CYCLELENGTH, 4, BASE_TIME, BASE_YEAR);
    } 
    JulianCalendar(String eng, long cycleTime, int cycleYear, long matchTime, int matchYear){
        super(eng, cycleTime, cycleYear, matchTime, matchYear);
    
    }
    
    

    @Override
    public BasicDate getDate(long t) {
    //    hussam.print("cleaing: "+t);
        long time=CalendarFactory.cleanDate(t);
        if(!isDateInRange(time))return getMaximumDate();

    //    hussam.println(" cleaned: "+ new Date(time) +" same: "+(t==time));
        time-=getStartTime();
  //      hussam.println("startTime: "+new Date(getStartTime())+"\n"+getStartTime()+"\n"+time);
  //      hussam.println("AllDur: "+time/BasicCalendar.YEAR_TROPICAL);
        int cycle4= (int) (time / CYCLELENGTH);
        long rest = time- (cycle4*CYCLELENGTH);
  //      hussam.println("rest afterc4: "+rest/DAY);
        int years=(int)  Math.min((int)(rest / (365 *DAY)),3);
        rest=(long)(rest - years * ((365)*DAY));
 //       hussam.println("restAfter c1: "+rest+" "+rest/DAY);
  //     hussam.println("c4: "+cycle4+" y:"+years+" total:"+(cycle4+years));
    int y1= years + cycle4*4+getStartYear();
 //      hussam.println("Leap: "+y1+" "+isLeapYear(y1));
    //    hussam.println(LEAP_YEAR+" "+rest);
        Entry<Long,Integer> monthKey = (years==3)?LEAP_YEAR.floorEntry(rest):NORMAL_YEAR.floorEntry(rest);
        //hussam.println(NORMAL_YEAR);
        rest = rest - monthKey.getKey();
  //      hussam.println("yearstart: "+rest/DAY );
        int month=monthKey.getValue();
        int day=(int) (rest/DAY);
        
        BasicDate d=getDate(y1, month+1, day+1);
        return d;
    }
    
    public boolean isLeapYear(int y){
        return y%4==0;
    }
    
    @Override
    public BasicDate calculateDate(int y, int m, int d) {
       // getBase3();
        int yc=y-getStartYear();
        int cycle4=(yc)/4;
        int dy=yc-cycle4*4;
        long time = cycle4*DAY*(366+3*365);
        time+=dy*DAY*365;
        TreeMap<Integer, Long> dur=null;
        if(isLeapYear(y))dur=LEAP_YEAR_DURATION;else dur=NORMAL_YEAR_DURATION;
    //    hussam.println("Jul time: "+new Date(time)+" "+time);
        time+=dur.get(m-1)+(d-1)*DAY+getStartTime();
    //    hussam.println("Month: "+m+" map: "+dur);
        if(!isDateInRange(time))return getMaximumDate();

        MyDate md=new MyDate(CalendarFactory.cleanDate(time), d, m, y, this);
        return md;
        
    }
    
    @Override
    public PeriodType getYearType(int bd) {
        return isLeapYear(bd)?Y_LEAP:Y_NORMAL;
    }

    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(new BasicYear[]{Y_LEAP, Y_NORMAL});
    }

    
    
    
}
