/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.util.List;
import java.util.TreeMap;
import static nasiiCalendar.LunerCalendar.START_SAMI;

/**
 *
 * @author Hussam
 */
public abstract class SolarCalendar extends MyBasicCalendar implements BasicCalendar{
    final TreeMap<Long, Integer> NORMAL_YEAR = new TreeMap<Long, Integer>();
    final TreeMap<Integer, Long> NORMAL_YEAR_DURATION = new TreeMap<Integer, Long>();
    final TreeMap<Long, Integer> LEAP_YEAR = new TreeMap<Long, Integer>();
    final TreeMap<Integer, Long> LEAP_YEAR_DURATION = new TreeMap<Integer, Long>();
    String engName;
   
    public SolarCalendar(String eng,  long cycleTime, int cycleYear, long matchTime, int matchYear) {
        super(cycleTime, cycleYear, matchTime, matchYear);
        this.engName=eng;
        setup();
    }

    
    
    public String getName() {
    return engName;
    }
    protected int[] getNormalYearMonthLength(){
        return new int[]{31,28,31,30,31,30,31,31,30,31,30, 31};
    }
    protected int[] getLeapYearMonthLength(){
        return new int[]{31,29,31,30,31,30,31,31,30,31,30, 31};
    } 
    protected void setup(){
            NORMAL_YEAR.clear();
            NORMAL_YEAR_DURATION.clear();
            LEAP_YEAR.clear();
            LEAP_YEAR_DURATION.clear();
        int  x=0;
        int month=0;
        
        x=addLength(x, month++);
        for(int y:getNormalYearMonthLength()){
            x=addLength(x+y, month++);
            
        }
        
        month=0;
        x=0;
        x=addLeapLength(x, month++);
        for(int y:getLeapYearMonthLength()){
            x=addLeapLength(x+y, month++);
            
        }
        
    }
    protected int addLeapLength(int time, int month){
        LEAP_YEAR.put(time*DAY, month);
        LEAP_YEAR_DURATION.put(month, time*DAY);
        return time;
    }
    protected int addLength(int time, int month){
        NORMAL_YEAR.put(time*DAY, month);
        NORMAL_YEAR_DURATION.put(month, time*DAY);
        return time;
    }

    
    @Override
    public int getMonthLength(BasicDate bd) {
        if(isLeapYear(bd)){
            return getLeapYearMonthLength()[bd.getMonth()-1];
        }else return getNormalYearMonthLength()[bd.getMonth()-1];
        /*
        switch (bd.getMonth()){
            case 1:case 3:case 5:case 7: case 8:case 10:case 12: return 31;
            case 4:case 6:case 9:case 11: return 30;
            default: return isLeapYear(bd)? 29: 28;
        }//*/
    }

    @Override
    public int getYearLength(BasicDate bd) {
        return isLeapYear(bd)?366:365;
    }

    @Override
    public BasicDate nextDay(BasicDate bd) {
        return getDate(bd.getDate()+DAY);
    }
 
    
    public String toString(){
        return getName();
    }
}
