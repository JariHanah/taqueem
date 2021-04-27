/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import static nasiiCalendar.BasicCalendar.DAY;

/**
 *
 * @author hmulh
 */
public abstract class MonthLengthGenerator {
    
    int bigCycle;
    long cycleLength;
    long cycleStart;  
    
    int [][]test={{5,6,7,8},{10,12}};

    public int[][] getMonthCycle(){
        return test;
    }
    public int getMonthLength(int year, int month){
        int[][]months=getMonthCycle();
        year=year%bigCycle;
        if(month>=months[year].length-1){
            return getMonthLength(year+1, month-months[year].length-1);
        }
        return months[year][month+1];
    }
    public int getMonthLength(int month){
        return getMonthLength(0, month);
    }
    
    public abstract PeriodType getYearType(int year);
    
    
    public int getMonthLength(long t){
        long dur=t-cycleStart;
        dur=dur%cycleLength;
        for(int i=0;i<getMonthCycle().length;i++){
            long yearLength=getMonthCycle()[i][0]*DAY;
            if(yearLength<dur){
                dur-=yearLength;
            }else{
                int []monthLengths=getMonthCycle()[i];
                for(int o=1;o<monthLengths.length;o++){
                    long monthL=monthLengths[o]*DAY;
                    if(monthL<dur){
                        dur-=monthL;
                    }else{
                        return monthLengths[o];
                    }
              
                }
            }
        }
        return 0;
    }
}
