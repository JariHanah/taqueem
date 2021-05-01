/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar.locationBasid;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;
import static nasiiCalendar.BasicCalendar.DAY;
import nasiiCalendar.CalendarFactory;
import org.shredzone.commons.suncalc.MoonPhase;

/**
 *
 * @author hmulh
 */
public abstract class AbstractBlackMoonMonth implements LunerIdentifier{
 
     City city;
     ZoneId zone;

    public void setZone(ZoneId zone) {
        this.zone = zone;
    }
    private static final Logger LOG = Logger.getLogger(AbstractBlackMoonMonth.class.getName());

    public ZoneId getZone() {
        return zone;
    }
    public AbstractBlackMoonMonth(ZoneId zone) {
        this(City.MAKKA, zone);
       
    }

    public AbstractBlackMoonMonth(City city, ZoneId zone) {
        this.city = city;
         this.zone=zone;
    }
    
    public long getNextMonth(long time) {
        time = CalendarFactory.dayStart(time, zone);
        TimeZone zone = TimeZone.getDefault();

        MoonPhase phase = MoonPhase.compute().timezone(zone).on(new Date(time - 5 * DAY)).midnight().execute();

        long newMonth = getNextMonth(phase);
        if (newMonth <= time) {
        //    System.err.println("it is smaller new Month: "+new Date(newMonth)+"\ttime: "+new Date(time));
            newMonth = getNextMonth(MoonPhase.compute().timezone(zone).on(new Date(time)).midnight().execute());
        }
     //   System.err.println("\thilal: "+new Date(newMonth-DAY)+"\tNewMonth: "+new Date(newMonth)+"\ttime: "+new Date(time));
        return newMonth;

    }

    protected abstract long getNextMonth(MoonPhase phase);

    @Override
    public long getPreviousMonth(long time) {
        long prev1 = getNextMonth(time - 31 * DAY);
        long prev2 = getNextMonth(prev1 + DAY);
   //     System.err.println("time: "+new Date(time)+"\tprev1: "+new Date(prev1)+"\tprev2: "+new Date(prev2));
        if (time >= prev2) {
            return prev2;
        }
        return prev1;

    }
    
    
    @Override
    public City getCity() {
        return City.MAKKA;
    }
    public void setCity(City c){
        this.city=c;
    }
    @Override
    public boolean isLocationBased() {
        return true;
    }

    static long getTime(MoonPhase phase){
        return phase.getTime().toInstant().toEpochMilli();
    } 
    public static long getTimeInstant(ZonedDateTime zoned){
        return zoned.toInstant().toEpochMilli();//.get.getTimeInstant().toInstant().toEpochMilli();
    } 
}
