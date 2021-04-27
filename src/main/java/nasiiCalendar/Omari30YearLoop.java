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
 * @author hmulh
 */
public class Omari30YearLoop extends SolarCalendar{
    private static long CYCLE=(354*19+355*11)*DAY;//30*12*MONTH;
    private static long START=-42521554800000L-9*HOUR;
    Map<Integer, Integer>yearDuration=new  TreeMap<Integer, Integer>();
    TreeMap<Long, Integer>durationYear=new  TreeMap<Long, Integer>();
    HijriCalc30 type;
    final BasicYear Y_NORMAL=new BasicYear("LunerYear", false, false, new PeriodType[]{Months.MUHARRAM, Months.SAFAR, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA});
    final BasicYear Y_LEAP=new BasicYear("LunerLeap",true, false, new PeriodType[]{Months.MUHARRAM, Months.SAFAR, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, new LeapMonth(30,Months.THO_HIJA)});
    public enum HijriCalc30{
        Type16(BasicCalendar.OMARI_ID_16,  new int[]{2, 5, 7, 10, 13, 16, 18, 21, 24, 26, 29}),
        Type15(BasicCalendar.OMARI_ID_15, new int[]{2, 5, 7, 10, 13, 15, 18, 21, 24, 26, 29}),
        TypeHindi(BasicCalendar.OMARI_ID_INDIAN, new int[]{2, 5, 8, 10, 13, 16, 19, 21, 24, 27, 29}),
        TypeHaseb(BasicCalendar.OMARI_ID_HABASH,new int[]{2, 5, 8, 11, 13, 16, 19, 21, 24, 27, 30});
        String name;
        int [] leapYears;
       
        private HijriCalc30(String name,int[] leapYears) {
            this.name = name;
            this.leapYears = leapYears;
            
        }

        public int[] getLeapYears() {
            return leapYears;
        }

        public String getName() {
            return name;
        }

        
    }
    //String eng,  String shE, long cycleTime, int cycleYear, long matchTime, int matchYear
    public Omari30YearLoop(HijriCalc30 type, ZoneId zone) {
        this(type.getName(),  type, START,1, zone);
        
    }
    protected Omari30YearLoop(String name, HijriCalc30 type, long matchTime, int matchYear, ZoneId zone){
        super(name, CYCLE, 30, matchTime, matchYear, zone);
        this.type=type;
        prepareYears();
    }
    public Omari30YearLoop(ZoneId zone) {
        this(HijriCalc30.Type16, zone);
    }
    
    private void prepareYears(){
        int total=0,days=0;
        for(int i=0;i<30;i++){
            yearDuration.put(i, total);
            durationYear.put(total*DAY, i);
            days=isLeapYear(i+1)?355:354;
            total+=days;
            
        }
    }
    /*
    public enum OmariYears implements PeriodType {
        Y0("Luner Year", new PeriodType[]{Months.MUHARRAM, Months.SAFAR, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
            Months.RAJAB, Months.SHAABAN, Months.RAMADHAN, Months.SHAWWAL, Months.THO_QIDAH, Months.THO_HIJA}),
        Y1("Luner Leap",  new PeriodType[]{Months.MUHARRAM, Months.SAFAR, Months.RABEI1, Months.RABEI2, Months.JAMAD1, Months.JAMAD2,
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
    protected int[] getLeapYearMonthLength() {
        return new int[]{30,29,30,29,30,29, 30, 29, 30, 29, 30, 30};
    }

    @Override
    protected int[] getNormalYearMonthLength() {
        return new int[]{30,29,30,29,30,29, 30, 29, 30, 29, 30, 29};
    }

    
    
    
    @Override
    public BasicDate calculateDate(int y, int m, int d) {
         // getBase3();
        int yc=y-getStartYear();
        int cycle30=(yc)/30;
        int dy=yc-cycle30*30;
        long time = cycle30*CYCLE;
        //int days=0;
       // hussam.println("dy: "+dy);
        time+=yearDuration.get(dy)*DAY;
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
    public boolean isLeapYear(int year) {
        year=year%30;
        //hussam.print("testing: "+year+" ");
        return Arrays.binarySearch(type.getLeapYears(), year )>=0;
       /* int cycle=year%30;
        switch(cycle){
        case 2:case 5:case 7:case 10:case 13:case 16:
        case 18: case 21: case 24: case 26: case 29: return true;
        }
        return false;//*/
    }

    @Override
    public BasicDate getDate(long time) {
        //    hussam.print("cleaing: "+t);
        time=CalendarFactory.cleanDate(time);
        if(!isDateInRange(time))return getMaximumDate();

    //    hussam.println(" cleaned: "+ new Date(time) +" same: "+(t==time));
        time-=getStartTime();
  //      hussam.println("startTime: "+new Date(getStartTime())+"\n"+getStartTime()+"\n"+time);
  //      hussam.println("AllDur: "+time/BasicCalendar.YEAR_TROPICAL);
        int cycle30= (int) (time / CYCLE);
        long rest = time- (cycle30*CYCLE);
  //      hussam.println("rest afterc4: "+rest/DAY);
        Map.Entry yk=durationYear.floorEntry(rest);
        rest-=(long)yk.getKey();
        
         int y1= (int)yk.getValue() + cycle30*30+getStartYear();
 //      hussam.println("Leap: "+y1+" "+isLeapYear(y1));
    //    hussam.println(LEAP_YEAR+" "+rest);
     //   hussam.println("y1: "+y1);
        Map.Entry<Long,Integer> monthKey = (isLeapYear(y1))?LEAP_YEAR.floorEntry(rest):NORMAL_YEAR.floorEntry(rest);
        //hussam.println(NORMAL_YEAR);
        rest = rest - monthKey.getKey();
  //      hussam.println("yearstart: "+rest/DAY );
        int month=monthKey.getValue();
        int day=(int) (rest/DAY);
        
        BasicDate d=getDate(y1, month+1, day+1);
        return d;
    }

    @Override
    public PeriodType getYearType(int year) {
     if(isLeapYear(year))return Y_LEAP;
     return Y_NORMAL;
    }

    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(new BasicYear[]{Y_LEAP, Y_NORMAL});
    }

    public BasicDate getDateByName(PeriodType bm, BasicDate bd) {
        //hussam.println("date: "+(getYearType(bd).getMonths().indexOf(bm)+1)+ " "+getYearType(bd).getMonths().toString());
        return new MyDate(bd.getDate(), bd.getDay(), getYearType(bd).getSubPeriods().indexOf(bm) + 1, bd.getYear(), this);
    }
}
