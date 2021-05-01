/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

//import static com.alhanah.samicalapp.SamiApplication.getStore;
import com.alhanah.webcalendar.Application;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import nasiiCalendar.locationBasid.BlackFajrStandard;
import nasiiCalendar.locationBasid.GenericLunerCalendar;
import nasiiCalendar.locationBasid.HilalByMinutesStandard;
import nasiiCalendar.locationBasid.UmAlquraStandardV1419;
import nasiiCalendar.locationBasid.UmAlquraStandardV1423;

/**
 *
 * @author DEll
 */
public class CalendarFactory {

    static final SeasonIdentifier season128 = new SeasonSolar128();
    static final SeasonIdentifier seasonJalali = new SeasonJalali();
    static final SeasonIdentifier seasonfalak = new SeasonSunCalc();
    ZoneId zoneId;

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zone) {
        this.zoneId=zone;
        mySortedCals.forEach((BasicCalendar t) -> {
            t.setZoneId(zone);
        });
    }

    Calendar greg;
    List<BasicCalendar> mySortedCals = new ArrayList<>();
    Map<String, BasicCalendar> calMap = new HashMap<String, BasicCalendar>();
    SeasonIdentifier defaultSeasonIdentifier;

    static CalendarFactory instance;

    public static long dayEnd(long time2) {
        Calendar c = getGreg();
        Date d = new Date(time2);
        c.setTime(d);

        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);

        long time = c.getTime().getTime();
        return time;
    }

    /*
    public static CalendarFactory getInstance() {
        return instance;
    }//*/
    public String getName() {
        return zoneId.getId();
    }

    public CalendarFactory(ZoneId zone) {
        this.zoneId = zone;
        instance = this;
        defaultSeasonIdentifier = season128;
        greg = Calendar.getInstance(TimeZone.getTimeZone(zone.getId()));
        prepare();

    }

    protected void prepare() {

        addCalendar(new SamiFixed(zoneId));
        //addCalendar(new WsmiCalendar());
        addCalendar(new GenericLunerCalendar(new WsmiCalendar(zoneId), new UmAlquraStandardV1423(zoneId)));
        addCalendar(new GenericLunerCalendar(BasicCalendar.UMM_ALQURA_CALENDAR_V1423, new Omari30YearLoop(zoneId), new UmAlquraStandardV1423(zoneId)));

        addCalendar(new Omari30YearLoop(Omari30YearLoop.HijriCalc30.Type16, zoneId));
        addCalendar(new Omari30YearLoop(Omari30YearLoop.HijriCalc30.Type15, zoneId));
        addCalendar(new Omari30YearLoop(Omari30YearLoop.HijriCalc30.TypeHindi, zoneId));
        addCalendar(new Omari30YearLoop(Omari30YearLoop.HijriCalc30.TypeHaseb, zoneId));
        addCalendar(new FatimiCalendar(zoneId));
        addCalendar(new JahhafCalendar(zoneId));
        addCalendar(new GenericLunerCalendar(BasicCalendar.UMM_ALQURA_CALENDAR_V1420, new Omari30YearLoop(zoneId), new HilalByMinutesStandard(0, zoneId)));
        addCalendar(new GenericLunerCalendar(BasicCalendar.UMM_ALQURA_CALENDAR_V1419, new Omari30YearLoop(zoneId), new UmAlquraStandardV1419(zoneId)));

        addCalendar(new GenericLunerCalendar(new QazwiniCalendar(zoneId), new BlackFajrStandard(zoneId)));
        addCalendar(new ByroniCalendar(zoneId));
        addCalendar(new AdCalendar(zoneId));
        addCalendar(new GregoryCalendar(zoneId));

        addCalendar(new JulianCalendar(zoneId));
        //       addCalendar(new OmariCalendar());
        //    addCalendar(new QazwiniCalendar());
        addCalendar(new JalaliCalendarIR(zoneId));
        addCalendar(new Solar128Calendar(zoneId));
        addCalendar(new SolarStationsCalendar(zoneId));
        addCalendar(new Zodiac13Calendar(zoneId));
        addCalendar(new HebrewCalendar(zoneId));
        addCalendar(new CopticCalendar(zoneId));//addCalendar(new SamiCalendar());
    }

    public List<BasicCalendar> getCalendars() {
        List<BasicCalendar> cal = new ArrayList<BasicCalendar>(mySortedCals);
        //  System.err.println("contains Null? "+ cal.contains(null));
        /* return cal;
        
        cal.add(calMap.get(BasicCalendar.SAMI_LUNER_ID));
        cal.add(calMap.get(BasicCalendar.SAMI_FIXED_ID));
        cal.add(calMap.get(BasicCalendar.OMARI_ID_16));
        cal.add(calMap.get(BasicCalendar.AD_ID));
        cal.add(calMap.get(BasicCalendar.UMM_ALQURA_CALENDAR_V1423));
        cal.add(calMap.get(BasicCalendar.QAZWINI_FAJR_ID));
        
        cal.add(calMap.get(BasicCalendar.WSMI_ID));
    //    cal.add(calMap.get(BasicCalendar.QAZWINI_ID));
        cal.add(calMap.get(BasicCalendar.BYRONI_ID));
        cal.add(calMap.get(BasicCalendar.HEWBREW_ID));
        cal.add(calMap.get(BasicCalendar.SOLAR_STATIONS_ID));
        cal.add(calMap.get(BasicCalendar.ZODIAC13_ID));
        cal.add(calMap.get(BasicCalendar.JALALI_IR_ID));
        cal.add(calMap.get(BasicCalendar.GREG_ID));
        cal.add(calMap.get(BasicCalendar.JULIAN_ID));
        cal.add(calMap.get(BasicCalendar.SOLAR_128_ID));
        cal.add(calMap.get(BasicCalendar.OMARI_ID_15));
        cal.add(calMap.get(BasicCalendar.OMARI_ID_HABASH));
        cal.add(calMap.get(BasicCalendar.OMARI_ID_INDIAN));
        cal.add(calMap.get(BasicCalendar.SAMI_ID));
        cal.add(calMap.get(BasicCalendar.OMARI_ID));
        cal.removeAll(null);
        
        //cal.addAll(calMap.values());//*/
        return cal;
    }

    public static List<Long> getLunerStartDays(long start, long end) {
        List<Long> list = new ArrayList<Long>();

        for (long i = start; i < end; i += BasicCalendar.MONTH) {
            list.add(i);
        }
        return list;
    }

    public static List<Long> getLunerStartDays(long end) {
        return getLunerStartDays(BasicCalendar.CALENDAR_ZERO_START, end);
    }

    public static void setInstance(CalendarFactory fac) {
        instance = fac;
    }

    public static OmariCalendar getOmariCalendar() {
        return (OmariCalendar) instance.getCalendar(BasicCalendar.OMARI_ID);
    }

    public static SamiFixed getSamiCalendar() {

        return (SamiFixed) instance.getCalendar(BasicCalendar.SAMI_FIXED_ID);
    }

    public static WsmiCalendar getWsmiCalendar() {
        return (WsmiCalendar) instance.getCalendar(BasicCalendar.WSMI_ID);
    }

    public static QazwiniCalendar getQazwiniCalendar() {
        return (QazwiniCalendar) instance.getCalendar(BasicCalendar.QAZWINI_ID);
    }

    public static ByroniCalendar getByroniCalendar() {
        return (ByroniCalendar) instance.getCalendar(BasicCalendar.BYRONI_ID);
    }

    public static Calendar getGreg() {

        return instance.greg;
    }

    public static void test(CalendarFactory fac, CalendarTester test) {
        for (BasicCalendar cal : fac.getCalendars()) {
            test.test(cal);
        }
    }

    public static boolean test(BasicDate bd) {
        //    if(true)return;
        //    System.out.println("testnig "+bd +" with actualDate: "+new Date(bd.getDate()));
        BasicCalendar cal = bd.getCalendar();
        //    hussam.println("testing b1: ");
        BasicDate b1 = cal.getDate(bd.getDate());
        //  hussam.println("testing b2: ");
        BasicDate b2 = cal.getDate(b1.getYear(), b1.getMonth(), b1.getDay());
        //hussam.println("testing b3: ");
        BasicDate b3 = cal.getDate(b2.getDate());
        Date d1 = new Date(bd.getDate());
        Date d2 = new Date(b1.getDate());
        Date d3 = new Date(b2.getDate());
        Date d4 = new Date(b3.getDate());

        if (b2.equals(b1) && b2.equals(b3)) {
        } else {

            System.err.println("\nTesting for: " + bd);
            System.err.println("\tGregOrig: " + d1);
            System.err.println("\tGregDate: " + " " + d2 + " " + d3 + " " + d4);
            System.err.println("\tDates b1: " + b1.getDate() + " b2: " + b2.getDate() + " b3: " + b3.getDate());
            System.err.println("\tdays b1: " + b1.getDay() + " b2: " + b2.getDay() + " b3: " + b3.getDay());
            System.err.println("\tMonths b1: " + b1.getMonth() + " b2: " + b2.getMonth() + " b3: " + b3.getMonth());
            System.err.println("\tyears b1: " + b1.getYear() + " b2: " + b2.getYear() + " b3: " + b3.getYear());
            System.err.println("\tCalendars: " + b1.getCalendar() + " " + b2.getCalendar() + " " + b3.getCalendar());
            System.err.println("\tObjects: " + b1 + " " + b2 + " " + b3);
            //   throw new RuntimeException("Date Conversion Wrong");
            return true;
        }
        return false;

    }

    public static AdCalendar getAdCalendar() {
        return (AdCalendar) instance.getCalendar(BasicCalendar.AD_ID);
    }

    public static JulianCalendar getJulianCalendar() {
        return (JulianCalendar) instance.getCalendar(BasicCalendar.JULIAN_ID);
    }

    public static GregoryCalendar getGregoryCalendar() {
        return (GregoryCalendar) instance.getCalendar(BasicCalendar.GREG_ID);
    }

    public static Solar128Calendar getSolar128Calendar() {
        return (Solar128Calendar) instance.getCalendar(BasicCalendar.SOLAR_128_ID);
    }

    public static SolarStationsCalendar getLunerStationCalendar() {

        return (SolarStationsCalendar) instance.getCalendar(BasicCalendar.SOLAR_STATIONS_ID);

    }

    public static Zodiac13Calendar getZodiac13Calendar() {
        return (Zodiac13Calendar) instance.getCalendar(BasicCalendar.ZODIAC13_ID);
    }

    public static void testDate(BasicDate bd, int i) {
        getGreg().setTime(new Date(bd.getDate()));
        if (i == getGreg().get(Calendar.DAY_OF_WEEK) - 1) {
            System.out.println("Success");
        } else {
            System.out.println("Failed: " + bd + " Day:" + i + " should be: " + getGreg().getTime() + " Day:" + (getGreg().get(Calendar.DAY_OF_WEEK) - 1));
        }

    }

    private static List<BasicCalendar> createAllCalendars() {
        return instance.getCalendars();
    }

    public static String format(BasicDate selected) {
        return selected.getCalendar().getName() + " " + selected.getYear() + " " + selected.getCalendar().getMonthName(selected) + " " + selected.getDay();
    }

    public static HebrewCalendar getHebrewCalendar() {
        return (HebrewCalendar) instance.getCalendar(BasicCalendar.HEWBREW_ID);
    }

    public static BasicDate getCurrentDate() {
        return getSamiCalendar().getDate(new Date().getTime());
    }

    private static void check(MyBasicCalendar zc) {
        /* hussam.println("checking: "+zc.getName());
        hussam.println("\tBigOnTime: "+zc.getBigOfTime()+" date: "+ new Date(zc.getBigOfTime()));
        hussam.println("\tSTART: "+zc.getStartTime()+" date: "+ new Date(zc.getStartTime()));
        hussam.println("\tEND: "+zc.getMaxInstant()+" date: "+ new Date(zc.getMaxInstant()));//*/

    }

    public static int getWeekDay(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;

    }

    public static String getWeekDay(BasicDate selected) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(selected.getDate()));
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return "Special";
        }

    }

    public static JalaliCalendarIR getJalaliIR() {
        return (JalaliCalendarIR) instance.getCalendar(BasicCalendar.JALALI_IR_ID);
    }

    public List<Long> getLunerStartDays() {
        Calendar c = getGreg();
        return getLunerStartDays(c.getTime().getTime());
    }

    public static long getOffSet() {
        return getGreg().getTimeZone().getRawOffset();
    }

    public long cleanDate(long time2) {
        Date d = new Date(time2);
        greg.setTime(d);

        greg.set(Calendar.HOUR_OF_DAY, 12);
        greg.set(Calendar.MINUTE, 0);
        greg.set(Calendar.SECOND, 0);
        greg.set(Calendar.MILLISECOND, 0);//*/

        long time = greg.getTime().getTime();
        return time;
    }
    public static long dayStart(long time2, ZoneId zone) {
        Calendar greg=Calendar.getInstance(TimeZone.getTimeZone(zone.getId()));
        Date d = new Date(time2);
        greg.setTime(d);

        greg.set(Calendar.HOUR_OF_DAY, 0);
        greg.set(Calendar.MINUTE, 0);
        greg.set(Calendar.SECOND, 0);
        greg.set(Calendar.MILLISECOND, 0);

        long time = greg.getTime().getTime();
        return time;
    }
    public long dayStart(long time2) {

        Date d = new Date(time2);
        greg.setTime(d);

        greg.set(Calendar.HOUR_OF_DAY, 0);
        greg.set(Calendar.MINUTE, 0);
        greg.set(Calendar.SECOND, 0);
        greg.set(Calendar.MILLISECOND, 0);

        long time = greg.getTime().getTime();
        return time;
    }
//*/

    public static void print(long t) {
        Calendar c = getGreg();
        c.setTime(new Date(t));
        System.out.println("time: " + c.get(Calendar.YEAR) + " " + c.get(Calendar.MONTH) + " " + c.get(Calendar.DATE) + " " + new Date(t) + " long:" + t);
    }

    public void addCalendar(BasicCalendar cal) {
        calMap.put(cal.getName(), cal);
        mySortedCals.add(cal);
    }

    public BasicCalendar getCalendar(String key) {
        return calMap.get(key);
    }

    public static SeasonIdentifier getSeasonSolar128() {
        return season128;

    }

    public static SeasonIdentifier getSeasonJalali() {
        return seasonJalali;
    }

    public static SeasonIdentifier getSeasonCalc() {
        return seasonfalak;
    }

    public static void setDefaultSeasonIdentifier(SeasonIdentifier s) {
        instance.setDefault(s);
    }

    public void setDefault(SeasonIdentifier s) {
        defaultSeasonIdentifier = s;
    }

    public static SeasonIdentifier getDefaultSeasonIdentifier() {
        return instance.getSeasonIdentifier();
    }

    public SeasonIdentifier getSeasonIdentifier() {
        return defaultSeasonIdentifier;
    }
}

class SeasonJalali implements SeasonIdentifier {

    @Override
    public String getName() {
        return "seasonjalali";
    }

    @Override
    public Season getSeason(BasicDate bd2) {
        JalaliCalendarIR jc = (JalaliCalendarIR) CalendarFactory.getJalaliIR();//.getCalendar(BasicCalendar.JALALI_IR_ID);
        BasicDate bd = jc.getDate(bd2.getDate());
        if (bd.equals(bd.getCalendar().getMaximumDate())) {
            return null;
        }

        BasicDate s1 = jc.getDate(bd.getYear(), 1, 1);
        BasicDate sum1 = jc.getDate(bd.getYear(), 4, 1);
        BasicDate f1 = jc.getDate(bd.getYear(), 7, 1);
        BasicDate w2 = jc.getDate(bd.getYear(), 10, 1);
        // hussam.println(bd+" "+w1+" "+s1+" testing : "+(bd.getDate()>=w1.getDate())+" testing: "+(bd.getDate()<=s1.getDate()));
        if (bd.getDate() >= s1.getDate() && bd.getDate() < sum1.getDate()) {
            return Season.SPRING;
            //          hussam.println("blue: "+bd);
        }
        if (bd.getDate() >= sum1.getDate() && bd.getDate() < f1.getDate()) {
            //c.setUIID("SpringLabel");
            return Season.SUMMER;
            //           hussam.println("green: "+bd);
        }
        if (bd.getDate() >= f1.getDate() && bd.getDate() < w2.getDate()) {
            return Season.FALL;
        }
        if (bd.getDate() >= w2.getDate()) {
            return Season.WINTER;
        }

        return null;
    }

}

class SeasonSolar128 implements SeasonIdentifier {

    @Override
    public String getName() {
        return "season128";
    }

    public Season getSeason(BasicDate bd2) {
        Solar128Calendar sc = (Solar128Calendar) CalendarFactory.getSolar128Calendar();//.getCalendar(BasicCalendar.SOLAR_128_ID);
        BasicDate bd = sc.getDate(bd2.getDate());
        if (bd.equals(bd.getCalendar().getMaximumDate())) {
            System.err.println("limit season");
            return null;
        }

        BasicDate w1 = sc.getDate(bd.getYear(), 1, 1);
        BasicDate s1 = sc.getDate(bd.getYear(), 3, 20);
        BasicDate sum1 = sc.getDate(bd.getYear(), 6, 20);
        BasicDate f1 = sc.getDate(bd.getYear(), 9, 22);
        BasicDate w2 = sc.getDate(bd.getYear(), 12, 21);
        BasicDate w3 = sc.getDate(bd.getYear(), 12, 31);
        // hussam.println(bd+" "+w1+" "+s1+" testing : "+(bd.getDate()>=w1.getDate())+" testing: "+(bd.getDate()<=s1.getDate()));
        if (bd.getDate() >= w1.getDate() && bd.getDate() < s1.getDate()) {
            return Season.WINTER;
            //          hussam.println("blue: "+bd);
        }
        if (bd.getDate() >= s1.getDate() && bd.getDate() < sum1.getDate()) {
            //c.setUIID("SpringLabel");
            return Season.SPRING;
            //           hussam.println("green: "+bd);
        }
        if (bd.getDate() >= sum1.getDate() && bd.getDate() < f1.getDate()) {
            return Season.SUMMER;
        }
        if (bd.getDate() >= f1.getDate() && bd.getDate() < w2.getDate()) {
            return Season.FALL;
        }
        if (bd.getDate() >= w2.getDate()) {
            return Season.WINTER;

        }
        System.err.println("STRANGE NULL SEASON " + bd);
        return null;
    }
}

class SeasonSunCalc implements SeasonIdentifier {

    @Override
    public String getName() {
        return "seasonfalak";
    }

    public BasicDate getDate(Equinox.DateTime dt) {
        BasicDate e = CalendarFactory.getGregoryCalendar().getDate(dt.getYear(), dt.getMonth(), dt.getDay());
        return e;
    }

    @Override
    public Season getSeason(BasicDate bd) {
        bd = CalendarFactory.getGregoryCalendar().getDate(bd.getDate());
        Equinox e = new Equinox(bd.getYear());
        BasicDate s1 = getDate(e.getMarchEquinox());
        BasicDate f1 = getDate(e.getSeptemberEquinox());
        BasicDate sum1 = getDate(e.getJuneSolstice());
        BasicDate w2 = getDate(e.getDecemberSolstice());

        if (bd.getDate() < s1.getDate()) {
            return Season.WINTER;
        }
        if (bd.getDate() >= s1.getDate() && bd.getDate() < sum1.getDate()) {
            //c.setUIID("SpringLabel");
            return Season.SPRING;
            //           hussam.println("green: "+bd);
        }
        if (bd.getDate() >= sum1.getDate() && bd.getDate() < f1.getDate()) {
            return Season.SUMMER;
        }
        if (bd.getDate() >= f1.getDate() && bd.getDate() < w2.getDate()) {
            return Season.FALL;
        }
        if (bd.getDate() >= w2.getDate()) {
            return Season.WINTER;

        }
        return Season.WINTER;
    }

}

/* 
 * 
 * Sun Position Calculations
 * http://github.com/sualeh/sunposition
 * Copyright (c) 2007-2008, Sualeh Fatehi.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */
/**
 * Dates and times for the solstices and equinoxes.
 *
 * @author sfatehi
 */
class Equinox {

    /**
     * A date and a time.
     *
     * @author Sualeh Fatehi
     */
    public class DateTime
            implements Serializable {

        private static final long serialVersionUID = -8184768383256338584L;

        private final int year;
        private final int month;
        private final int day;
        private final int hours;
        private final int minutes;
        private final int seconds;

        DateTime(int year, int month, int day, int hour, int minute, int second) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.hours = hour;
            this.minutes = minute;
            this.seconds = second;
        }

        /**
         * Year.
         *
         * @return Year
         */
        public int getYear() {
            return year;
        }

        /**
         * Month.
         *
         * @return Month
         */
        public int getMonth() {
            return month;
        }

        /**
         * Day.
         *
         * @return Day
         */
        public int getDay() {
            return day;
        }

        /**
         * Hours.
         *
         * @return Hours
         */
        public int getHours() {
            return hours;
        }

        /**
         * Minute.
         *
         * @return Minutes
         */
        public int getMinutes() {
            return minutes;
        }

        /**
         * Seconds.
         *
         * @return Seconds
         */
        public int getSeconds() {
            return seconds;
        }

    }

    private final DateTime vernalEquinox;
    private final DateTime summerSolstice;
    private final DateTime autumnalEquinox;
    private final DateTime winterSolstice;

    /**
     * Calculate the dates and times for the solstices and equinoxes.
     *
     * @param year The year to calculate for,
     */
    public Equinox(final int year) {
        final double m, ve, ss, ae, ws;
        m = ((double) year - 2000) / 1000;
        final double m2 = m * m;
        final double m3 = m2 * m;
        final double m4 = m3 * m;
        ve = 2451623.80984 + 365242.37404 * m + 0.05169 * m2 - 0.00411 * m3
                - 0.00057 * m4;
        vernalEquinox = toDate(ve);
        ss = 2451716.56767 + 365241.62603 * m + 0.00325 * m2 + 0.00888 * m3
                - 0.00030 * m4;
        summerSolstice = toDate(ss);
        ae = 2451810.21715 + 365242.01767 * m - 0.11575 * m2 + 0.00337 * m3
                + 0.00078 * m4;
        autumnalEquinox = toDate(ae);
        ws = 2451900.05952 + 365242.74049 * m - 0.06223 * m2 - 0.00823 * m3
                + 0.00032 * m4;
        winterSolstice = toDate(ws);
    }

    /**
     * @return the autumnalEquinox
     */
    public DateTime getSeptemberEquinox() {
        return autumnalEquinox;
    }

    /**
     * @return the summerSolstice
     */
    public DateTime getJuneSolstice() {
        return summerSolstice;
    }

    /**
     * @return the vernalEquinox
     */
    public DateTime getMarchEquinox() {
        return vernalEquinox;
    }

    /**
     * @return the winterSolstice
     */
    public DateTime getDecemberSolstice() {
        return winterSolstice;
    }

    private DateTime toDate(final double jdn) {
        final double p = Math.floor(jdn + 0.5);
        final double s1 = p + 68569;
        final double n = Math.floor(4 * s1 / 146097);
        final double s2 = s1 - Math.floor((146097 * n + 3) / 4);
        final double i = Math.floor(4000 * (s2 + 1) / 1461001);
        final double s3 = s2 - Math.floor(1461 * i / 4) + 31;
        final double q = Math.floor(80 * s3 / 2447);
        final double e = s3 - Math.floor(2447 * q / 80);
        final double s4 = Math.floor(q / 11);

        final double mm = q + 2 - 12 * s4;
        final double yy = 100 * (n - 49) + i + s4;
        final double dd = e + jdn - p + 0.5;

        double hrs, min, sec, tm;

        tm = 24 * (dd - Math.floor(dd));
        hrs = Math.floor(tm);
        tm = 60 * (tm - hrs);
        min = Math.floor(tm);
        tm = 60 * (tm - min);
        sec = Math.round(tm);

        return new DateTime((int) yy,
                (int) mm,
                (int) dd,
                (int) hrs,
                (int) min,
                (int) sec);
    }

}
