package nasiiCalendar.locationBasid;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import nasiiCalendar.BasicCalendar;
import static nasiiCalendar.BasicCalendar.DAY;
import static nasiiCalendar.BasicCalendar.MINUTE;
import nasiiCalendar.CalendarFactory;
import nasiiCalendar.RSSNames;
import org.shredzone.commons.suncalc.MoonPhase;
import org.shredzone.commons.suncalc.MoonTimes;
import org.shredzone.commons.suncalc.SunTimes;

public class UmAlquraStandardV1423 extends AbstractBlackMoonMonth implements LunerIdentifier {

    public UmAlquraStandardV1423(City differentCityThanMakkah) {
        super(differentCityThanMakkah);
    }

    public UmAlquraStandardV1423() {
        super();
    }

    
    @Override
    protected long getNextMonth(MoonPhase phase) {
        long blackPhaseDay = CalendarFactory.dayStart(getTimeInstant(phase.getTime()));
        MoonTimes mt = MoonTimes.compute().on(new Date(blackPhaseDay)).at(city.getLat(), city.getLon()).execute();
        SunTimes st = SunTimes.compute().on(new Date(blackPhaseDay)).at(city.getLat(), city.getLon()).execute();
        //   if(st.getSet()==null)return CalendarFactory.cleanDate(time);//time;
      //  System.err.println("phase: "+phase.getTimeInstant()+"\t"+st.getSet());
        if (getTimeInstant(phase.getTime()) < getTimeInstant(st.getSet())) {

            return getNewHilalDay(blackPhaseDay)+DAY;

        } else {
            return getNewHilalDay(blackPhaseDay + DAY)+DAY;

        }
    }

    long getNewHilalDay(long time) {
        MoonTimes mt = MoonTimes.compute().on(new Date(time)).at(city.getLat(), city.getLon()).execute();
        SunTimes st = SunTimes.compute().on(new Date(time)).at(city.getLat(), city.getLon()).execute();
        if (getTimeInstant(mt.getSet()) >= getTimeInstant(st.getSet())) {

            return CalendarFactory.dayStart(time);
        } else {
            return getNewHilalDay(time + DAY);
        }
    }

}
