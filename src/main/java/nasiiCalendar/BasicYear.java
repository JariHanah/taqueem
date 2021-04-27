/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static nasiiCalendar.BasicCalendar.DAY;

/**
 *
 * @author hmulh
 */
public class BasicYear extends PeriodStore implements PeriodType {

    String engName;
    List<PeriodType> months;
    int length;

    public BasicYear(String en, boolean smallLeap, boolean bigLeap, PeriodType[] months) {
        super(en, smallLeap, bigLeap, months);
        engName = en;
       
        this.months = Arrays.asList(months);
        for (PeriodType p : months) {
            length += p.getTotalLengthInDays();
        }
    }
    public Holder calcTimes(Holder h){
        Map.Entry<Integer, Long> entry=ids.floorEntry(h.monthDuration);
        Map.Entry<Integer, PeriodType> entryPeriod=periodIds.floorEntry(h.monthDuration);
        
        h.monthDuration-=entry.getKey();
        h.timeDuration+=entry.getValue();
        
        h.timeDuration+=h.dayDuration*DAY;
        h.dayDuration=0;
        return h;
        
    }

    @Override
    public Holder calcPeriod(Holder h) {
        Map.Entry<Long, Integer> entry=times.floorEntry(h.timeDuration);
        Map.Entry<Long, PeriodType> entryPeriod=periodDuration.floorEntry(h.timeDuration);
        
        h.monthDuration+=entry.getValue();
        h.timeDuration-=entry.getKey();
        
        int x=(int) (h.timeDuration/DAY);
        h.dayDuration+=x;
        h.timeDuration-=x*DAY;
        return h;
    }
    
    
    @Override
    public int getCountAsPeriods() {
        return 1;
    }

}
