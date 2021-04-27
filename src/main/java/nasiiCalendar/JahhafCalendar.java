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
public class JahhafCalendar extends Omari30YearLoop{

    public JahhafCalendar(ZoneId zone) {
        super(BasicCalendar.JAHHAF_ID,  Omari30YearLoop.HijriCalc30.Type15, -8658921599000L ,1107, zone);
    }
    
}