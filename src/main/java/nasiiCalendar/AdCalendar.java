/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Hussam
 */
public class AdCalendar extends SolarCalendar{
    GregoryCalendar gregCalc;
    JulianCalendar julCalc;
    long  split;
    AdCalendar(ZoneId zone){
        super(BasicCalendar.AD_ID, BasicCalendar.YEAR_SIDEREAL, 1, 0 , 0, zone);
        
        gregCalc=new GregoryCalendar(zone);
        julCalc=new JulianCalendar(zone);
        BasicDate b=gregCalc.getDate(1582, 10, 14);
        split=b.getDate();
        //super.calcTimes();
    }


    @Override
    public boolean isLeapYear(int year) {
        if(year<1582){
            return julCalc.isLeapYear(year);
        }//*/
        return gregCalc.isLeapYear(year);
    }
   
    @Override
    public BasicDate getDate(long bd) {
        BasicDate b=null;
        if(bd<split){
            b= julCalc.getDate(bd);
        }else{
            b= gregCalc.getDate(bd);
        }
        MyDate md=new MyDate(b.getDate(), b.getDay(), b.getMonth(), b.getYear(), this);
        return md;
    }

    @Override
    public BasicDate calculateDate(int y, int m, int d) {
        BasicDate r=null;
        BasicDate bd= julCalc.getDate(y, m, d);
        if(bd.getDate()<split){
            r= bd;
        }else{
            r= gregCalc.getDate(y,m,d);
        }
        r=new MyDate(r.getDate(), r.getDay(), r.getMonth(), r.getYear(), this);
        return r;
        
    }

    

    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(new BasicYear[]{JulianCalendar.Y_LEAP, JulianCalendar.Y_NORMAL});
    }

    @Override
    public PeriodType getYearType(int year) {
        if(isLeapYear(year)) return JulianCalendar.Y_LEAP;
        return JulianCalendar.Y_NORMAL;
    }
    
}
