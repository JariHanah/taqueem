/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar.locationBasid;

import com.alhanah.webcalendar.info.ClientTimeZone;
import com.batoulapps.adhan.CalculationMethod;
import com.batoulapps.adhan.CalculationParameters;
import com.batoulapps.adhan.Coordinates;
import com.batoulapps.adhan.Madhab;
import com.batoulapps.adhan.PrayerTimes;
import com.batoulapps.adhan.data.DateComponents;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import nasiiCalendar.BasicCalendar;
import static nasiiCalendar.BasicCalendar.DAY;
import nasiiCalendar.CalendarFactory;
import nasiiCalendar.RSSNames;
import org.shredzone.commons.suncalc.MoonPhase;
import org.shredzone.commons.suncalc.MoonTimes;
import org.shredzone.commons.suncalc.SunTimes;

/**
 *
 * @author hmulh
 */
public class BlackFajrStandard extends AbstractBlackMoonMonth implements LunerIdentifier{
 
    
    public BlackFajrStandard(ZoneId zone) {
        super(zone);
    }

    public BlackFajrStandard(City city, ZoneId zone) {
        super(city, zone);
    }
    
    
    

    protected long getNextMonth(MoonPhase phase) {
        long blackPhaseDay = CalendarFactory.dayStart(getTime(phase), getZone());
        MoonTimes mt = MoonTimes.compute().on(new Date(blackPhaseDay)).at(city.getLat(), city.getLon()).execute();
        SunTimes st = SunTimes.compute().on(new Date(blackPhaseDay)).at(city.getLat(), city.getLon()).execute();
        //   if(st.getSet()==null)return CalendarFactory.cleanDate(time);//time;
        if (getTimeInstant(phase.getTime()) < getFajrDate(blackPhaseDay).getTime()){//st.getSet().getTimeInstant()) {

            return blackPhaseDay;

        } else {
            return blackPhaseDay+DAY;

        }
    }

    long getNewHilalDay(long time) {
        MoonTimes mt = MoonTimes.compute().on(new Date(time)).at(city.getLat(), city.getLon()).execute();
        SunTimes st = SunTimes.compute().on(new Date(time)).at(city.getLat(), city.getLon()).execute();
        if (getTimeInstant(mt.getSet()) >= getTimeInstant(st.getSet())) {

            return CalendarFactory.dayStart(time, getZone());
        } else {
            return getNewHilalDay(time + DAY);
        }
    }
     
    
    
    public Date getFajrDate(long time){
        Coordinates coordinates = new Coordinates(city.getLat(), city.getLon());
        DateComponents date = DateComponents.from(new Date(time));
//DateComponents date = DateComponents.from(new Date());
        CalculationParameters params
                = CalculationMethod.MUSLIM_WORLD_LEAGUE.getParameters();
        params.madhab = Madhab.HANAFI;
        params.adjustments.fajr = 2;
        
     //   formatter.setTimeZone(TimeZone.getTimeZone(ClientTimeZone.getClientZoneId()));
        PrayerTimes prayerTimes = new PrayerTimes(coordinates, date, params);

   //     System.err.println(formatter.format(prayerTimes.fajr));
        
        return prayerTimes.fajr;
    }

        
}
