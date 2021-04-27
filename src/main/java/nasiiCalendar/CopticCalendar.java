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
 * @author hmulh
 */
public class CopticCalendar extends JulianCalendar{
    private static final long CYCLELENGTH= (365*3+366)*DAY;
    
    static final BasicYear CY_NORMAL=new BasicYear("LunerYear", false, false, new PeriodType[]{Months.COP_1, Months.COP_2, Months.COP_3, Months.COP_4, Months.COP_5, Months.COP_6,
            Months.COP_7, Months.COP_8, Months.COP_9, Months.COP_10, Months.COP_11, Months.COP_12, Months.COP_13});
    static final BasicYear CY_LEAP=new BasicYear("LunerLeap", true, false, new PeriodType[]{Months.COP_1, Months.COP_2, Months.COP_3, Months.COP_4, Months.COP_5, Months.COP_6,
            Months.COP_7, Months.COP_8, Months.COP_9, Months.COP_10, Months.COP_11, Months.COP_12, new LeapMonth(6,Months.COP_13)});
    
    public CopticCalendar(ZoneId zone) {
        super(BasicCalendar.COMPTIC_ID, CYCLELENGTH,4, 1063324800000L,1720, zone);
    }
    
    protected int[] getNormalYearMonthLength(){
        return new int[]{30,30,30,30,30,30,30,30,30,30,30,30, 5};
    }
    protected int[] getLeapYearMonthLength(){
        return new int[]{30,30,30,30,30,30,30,30,30,30,30,30, 6};
    } 
    
    public boolean isLeapYear(int y){
        return (y-2003)%4==0;
    }


    @Override
    public PeriodType getYearType(int bd) {
        return isLeapYear(bd)?CY_LEAP:CY_NORMAL;
    }

    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(new BasicYear[]{CY_LEAP, CY_NORMAL});
    }
}
