/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.time.ZoneId;
import java.util.List;

/**
 *
 * @author Hussam Almulhim
 */
public interface BasicCalendar {
    
    public static final String PACKAGE="com.alhanah.sami.";
    public static final String SKU_FULL_VERSION = "full";
    public static final String SAMI_ID = "sami";
    public static final String SAMI_FIXED_ID="sami.fixed";
    public static final String SAMI_LUNER_ID="sami.luner";
    public static final String GREG_ID = "greg";
    public static final String JULIAN_ID = "julian";
    public static final String JALALI_IR_ID="jalili.ir";
    public static final String JALALI_SA_ID="jalili.sa";
    public static final String FATIMI_ID="fatimi";
    public static final String SOLAR_128_ID = "solar128";
    public static final String SOLAR_STATIONS_ID = "solarstations";
    public static final String ZODIAC13_ID = "zodiac";
    public static final String AD_ID = "ad";
    public static final String BYRONI_ID = "byroni";
    public static final String QAZWINI_ID = "qazwini";
    public static final String WSMI_ID = "wsmi";
    public static final String HEWBREW_ID = "hebrew";
    public static final String COMPTIC_ID = "coptic";

    public static final String NEW_MOON_ID = "newmoon";
    public static final String BLACK_MOON_ID = "blackmoon";

    public static final String SKU_METONIC_VIEW = "metonicview";
    public static final String SKU_YEAR_VIEW = "yearview";
    public static final String SKU_MONTH_VIEW = "monthview";
    public static final String SKU_DAY_VIEW = "dayview";
    
    public static final String QAZWINI_FAJR_ID="qazwini.fajr";
    
    
    public static final String OMARI_ID = "omari";
    public static final String OMARI_ID_16 = "omari.16";
    public static final String OMARI_ID_15 = "omari.15";
    public static final String OMARI_ID_INDIAN = "omari.indian";
    public static final String OMARI_ID_HABASH = "omari.habash";
    public static final String JAHHAF_ID = "omari.jahhaf";
    
    public static final String UMM_ALQURA_CALENDAR_V1423="umalqura1423";
    public static final String UMM_ALQURA_CALENDAR_V1420="umalqura1420";
    public static final String UMM_ALQURA_CALENDAR_V1419="umalqura1419";
    
    
    public static final long CALENDAR_ZERO_START = -42564970799000L;
 //   public static final long CALENDAR_START = -42534385199000L;
  //  public static final long CALENDAR_METONIC_START = -42281751599000L;
    
    public static final long SECONDS = 1000L;
    public static final long MINUTE = SECONDS * 60;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;
    public static final long WEEK = DAY * 7;     //29.530587981
    public static final long MONTH = (long) (DAY * 29.530587981);//29.53059
    public static final long YEAR_TROPICAL = (long) (DAY * 365.242189 );
    public static final long YEAR_SIDEREAL = (long) (DAY * 365.256363);
    public static final long METONIC = MONTH * 235;
    
    public static final long START_SAMI = -45881942399000L;//-45881963999752L ;// -45881866800001L;//-45881953200001L;//-45881953199752l ;//-4000*METONIC;
    public static final String INFO_NAME=".name";
    public static final String INFO_DESC=".desc";
    public static final String INFO_URL=".url";
    public static final String INFO_REF=".ref";
    public static final String INFO_SHORT=".short";
    public static final String INFO_CLASS=".type";
    
                                           
    public BasicDate getMaximumDate();
    public BasicDate getMinimumDate();
    
    public BasicDate getCycleStart(long cycleLength);
 //   public BasicCalendarFactory getFactory();
    boolean isLeapYear(BasicDate bd);
    boolean isLeapYear(int year);
    public int getMonthLength(BasicDate bd);
    public int getYearLength(BasicDate bd);
    public BasicDate getDate(long time);
    public BasicDate getDate(int y, int m, int d);
    public BasicDate getDate(int y, PeriodType m, int d)throws DateConfigException;
    public BasicDate nextDay(BasicDate bd);
    public PeriodType getYearType(BasicDate bd);
    public PeriodType getYearType(int year);
    public List<PeriodType> getYearTypes();
    
    public PeriodType getMonthName(BasicDate bd);
   
    //public BasicDate getDateByName(PeriodType bm, BasicDate bd);
    public long getLongCycleTime();
    public int getLongCycleYear();
    
    public String getName();
    public ZoneId getZoneId();
    public void setZoneId(ZoneId zone);
    
}
