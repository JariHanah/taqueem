/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar.locationBasid;

import nasiiCalendar.LunerCalendar;
import nasiiCalendar.MyDate;
//import com.codename1.location.Location;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;
import nasiiCalendar.BasicYear;
import nasiiCalendar.CalendarFactory;
import nasiiCalendar.Months;
import nasiiCalendar.Omari30YearLoop;
import nasiiCalendar.PeriodType;
import static nasiiCalendar.BasicCalendar.DAY;
import static nasiiCalendar.BasicCalendar.MINUTE;

import static nasiiCalendar.BasicCalendar.MONTH;
import org.shredzone.commons.suncalc.MoonPhase;
import org.shredzone.commons.suncalc.MoonTimes;
import org.shredzone.commons.suncalc.SunTimes;
import static nasiiCalendar.locationBasid.AbstractBlackMoonMonth.getTimeInstant;
import static nasiiCalendar.locationBasid.AbstractBlackMoonMonth.getTime;

/**
 *
 * @author Hussam
 */
@Deprecated
public class LunerLocationCalendar extends LunerCalendar {

    private static final long FIRST_BLACK = -157789552418880000L;
    private final static int STARTDATE = -1455442;
    private final static long STARTLONG = FIRST_BLACK;//-METONIC353 * 4000 + START_SAMI +1 * MONTH-DAY;
//    MyLocation location;
    City city;
    BasicYear locationYear;
    Omari30YearLoop omari = new Omari30YearLoop();
    int minutes = 30;

    public LunerLocationCalendar(City city) {
        super(BasicCalendar.NEW_MOON_ID, MONTH, 1, STARTLONG, STARTDATE);
        this.city = city;
        locationYear = new LocationYears(city);
    //    this.location = MyLocationGenerator.getBase().getLocation(city.getLat(), city.getLon());//LocationManager.getLocationManager().getLastKnownLocation();

    }

    public City getCity() {
        return city;
    }
    
    public LunerLocationCalendar() {
        super(BasicCalendar.BLACK_MOON_ID, MONTH, 1, STARTLONG, STARTDATE);
        locationYear=new BasicYear("blackmoon", false, false, new PeriodType[]{Months.BLACK_MOON});
    }

    public class LocationYears extends BasicYear {

       
        City city;

        LocationYears(City c) {
            super(c.getName(), false, false, new PeriodType[]{Months.HILAL_MOON});
            city = c;
            //   months.addAll(this);
        }
        

        public City getCity() {
            return city;
        }

    }

    public MoonTimes getMoonTimes(long date) {
        MoonTimes mt = MoonTimes.compute()
                .on(new Date(date - DAY))
                .at(city.getLat(), city.getLon())
                .execute();

        return mt;

    }

    public MoonPhase getPreviousPhase(long date) {

        MoonPhase p = MoonPhase.compute().on(new Date(date - 33 * DAY)).execute();
        MoonPhase p2 = MoonPhase.compute().on(new Date(getTimeInstant(p.getTime()) + DAY)).execute();
        if (getTimeInstant(p2.getTime()) < date & date-getTimeInstant(p2.getTime())>DAY) {
            return p2;
        }

        return p;
    }

    SetInfo getDayOfNewMoon(long black) {

        TimeZone zone = TimeZone.getDefault();
        while (true) {
            MoonTimes m = MoonTimes.compute().timezone(zone).on(new Date(black)).midnight().at(city.getLat(), city.getLon()).execute();
            SunTimes s = SunTimes.compute().timezone(zone).on(new Date(black)).midnight().at(city.getLat(), city.getLon()).execute();
            black += DAY;
            if (m.getSet() == null | s.getSet() == null) {
                continue;
            }
            long result = getTimeInstant(m.getSet()) - getTimeInstant(s.getSet());
            if (result < MINUTE * 120 & result > minutes * MINUTE) {
                SetInfo k = new SetInfo();
                k.time = getTimeInstant(m.getSet()) + DAY;
                k.minute = (int) (result / MINUTE);
                return k;

            }

        }
    }

    

    public MoonPhase getNextPhase(long date) {
        return MoonPhase.compute().on(new Date(date)).execute();
    }

    // @Override
    public int getMonthLength(BasicDate bd) {
        if (true) {
            return 40;
        }
        long time = bd.getDate();
        MoonPhase black = MoonPhase.compute().on(new Date(time)).execute();
        MoonPhase prev = MoonPhase.compute().on(new Date(getTimeInstant(black.getTime()) - 35 * DAY)).execute();
        long day0 = getDayOfNewMoon(getTimeInstant(black.getTime())).time;
        long day1 = getDayOfNewMoon(getTimeInstant(prev.getTime())).time;

        int days = (int) ((day0 - day1) / DAY);
        return days;

    }

    @Override
    public BasicDate getDate(int y, int m, int d) {
        return calculateDate(y, m, d);
    }

    @Override
    protected BasicDate calculateDate(int y, int m, int d) {
        BasicDate b = omari.calculateDate(y, m, d);
        return getDate(b.getDate());

    }

    @Override
    public boolean isLeapYear(int year) {
        return false;
    }

    class SetInfo {

        long time;
        int minute;
    }

    @Override
    public BasicDate getDate(long time) {

        time = CalendarFactory.cleanDate(time);

        MoonPhase mp = getPreviousPhase(time);

        if (city == null) {
            long dur = time - getTimeInstant(mp.getTime());

            int days = (int) ((dur) / DAY);
            int m = 1;
            int y = (int) (time / DAY);
            return new MyDate(time, days, m, y, this);
        } else {

            SetInfo t = getDayOfNewMoon(getTimeInstant(mp.getTime()));
            if (t.time > time) {
                t = getDayOfNewMoon(getTimeInstant(getPreviousPhase(getTimeInstant(mp.getTime()) - DAY).getTime()));
            }
            long dur = time - t.time;

            int days = (int) ((dur) / DAY);
            //   if(days==0)y++;
            //   hussam.println("\nAllDays "+time/DAY+" Y: "+y+" d: "+(days+1)+" rest:"+(dur%MONTH)/DAY+"greg: "+new Date(time)+"\tPhase: "+mp.getTimeInstant());
            int m = 1;
            int y = (int) (time / DAY);
            return new MyDate(time, days + 1, m, y, this, t.minute);
        }

        //    BasicDate x=new MyDate( time, days, b.getMonth(), b.getYear(), this);
        //    return x;
    }

    @Override
    public PeriodType getYearType(int year) {
        return locationYear;
    }

    @Override
    public List<PeriodType> getYearTypes() {
        List<PeriodType> list = new ArrayList<>();
        list.add(locationYear);
        return list;
    }

    public String toString() {
        if(city==null)return super.getName();
        return city.getName();
    }
}

class CityMonth implements PeriodType {

    City city;

    CityMonth(City m) {
        city = m;
    }

    public City getCity() {
        return city;
    }

    @Override
    public String getName() {
        return city.getName();
    }

    @Override
    public int getTotalLengthInDays() {
        return Months.HILAL_MOON.getTotalLengthInDays();
    }

    @Override
    public long getDurationInTime() {
        return Months.HILAL_MOON.getDurationInTime();
    }

    @Override
    public int getCountAsPeriods() {
        return Months.HILAL_MOON.getCountAsPeriods();
    }

    @Override
    public List<PeriodType> getSubPeriods() {
        return Months.HILAL_MOON.getSubPeriods();
    }

    @Override
    public boolean isBigLeap() {
        return Months.HILAL_MOON.isBigLeap();
    }

    @Override
    public boolean isSmallLeap() {
        return Months.HILAL_MOON.isSmallLeap();
    }

}
