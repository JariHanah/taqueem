/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.time.ZoneId;

/**
 *
 * @author hmulh
 */
public class FatimiCalendar extends Omari30YearLoop{

    public FatimiCalendar(ZoneId zone) {
        super(BasicCalendar.FATIMI_ID, HijriCalc30.TypeHindi, -24883199000L ,1389, zone);
    }
    
}
