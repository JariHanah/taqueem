/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar.locationBasid;

import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
import static nasiiCalendar.BasicCalendar.DAY;
import static nasiiCalendar.BasicCalendar.MINUTE;
import nasiiCalendar.CalendarFactory;
import org.shredzone.commons.suncalc.MoonPhase;
import org.shredzone.commons.suncalc.MoonTimes;
import org.shredzone.commons.suncalc.SunTimes;

/**
 *
 * @author hmulh
 */
public class HilalByMinutesStandard extends AbstractBlackMoonMonth implements LunerIdentifier {

    int min;

    public HilalByMinutesStandard(int min, City differentCityThanMakkah, ZoneId zone) {
        super(differentCityThanMakkah, zone);
     //   this.max = max;
        this.min = min;
    }

    public HilalByMinutesStandard(City c, ZoneId zone) {
        this(30,  c, zone);
    }

    public HilalByMinutesStandard(ZoneId zone) {
        this(30, City.MAKKA, zone);
    }

    public HilalByMinutesStandard(int min, ZoneId zone) {
        this(min, City.MAKKA, zone);
    }

    

    @Override
    protected long getNextMonth(MoonPhase phase) {
        long dayOfBlackMoon = CalendarFactory.dayStart(getTime(phase), getZone());
        TimeZone zone = TimeZone.getDefault();

        MoonTimes m = MoonTimes.compute().timezone(zone).on(new Date(dayOfBlackMoon)).midnight().at(city.getLat(), city.getLon()).execute();
        SunTimes s = SunTimes.compute().timezone(zone).on(new Date(dayOfBlackMoon)).midnight().at(city.getLat(), city.getLon()).execute();

        long newmoon = dayOfBlackMoon;
        while (true) {

            if (isMoonSetAfterSun(newmoon)) {
                return CalendarFactory.dayStart(newmoon + DAY, getZone());
            }
            newmoon += DAY;
        }
    }

    private boolean isMoonSetAfterSun(long time) {
        TimeZone zone = TimeZone.getDefault();

        MoonTimes m = MoonTimes.compute().timezone(zone).on(new Date(time)).midnight().at(city.getLat(), city.getLon()).execute();
        SunTimes s = SunTimes.compute().timezone(zone).on(new Date(time)).midnight().at(city.getLat(), city.getLon()).execute();
        if (m.getSet() == null | s.getSet() == null) {
            return false;
        }
        long result = getTimeInstant(m.getSet()) - getTimeInstant(s.getSet());//.getTimeInstant();
        return (result > min * MINUTE);
    }
   
}
