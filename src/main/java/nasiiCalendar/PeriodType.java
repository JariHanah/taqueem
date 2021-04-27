/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.util.List;

/**
 *
 * @author DEll
 */
public interface PeriodType{
    public String getName();
    
    public int getTotalLengthInDays();
    public long getDurationInTime();
    
    public int getCountAsPeriods();
    public List<PeriodType> getSubPeriods();
    
    boolean isBigLeap();
    boolean isSmallLeap();
    
}
