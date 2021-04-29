/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import static nasiiCalendar.CalendarFactory.getGreg;

/**
 *
 * @author Hussam
 */
public abstract class MyBasicCalendar implements BasicCalendar, Comparable<BasicCalendar> {

    private final long matchTime;
    private final int matchYear;
    //   private long bigOfTime;
    private final long maxInstant;
    private final long minInstant;
    private final long cycleTime;
    private final int cycleYear;
    private final int startYear;
    private final long startTime;
    public static final long STARTTIME = -631158216466114979L; //-200M
    public static final long ENDTIME = 631132823551165020L; //200M
    private ZoneId zoneId;
    Calendar greg;
    protected MyBasicCalendar(long cycleTime, int cycleYear, long matchTime, int matchYear, ZoneId zone) {//,  long bigOfTime, long maxInstant) {
        //   this.factory=fac;
        this.zoneId = zone;
        this.matchTime = matchTime;
        this.matchYear = matchYear;
        this.cycleTime = cycleTime;
        this.cycleYear = cycleYear;
        this.minInstant = STARTTIME;
        this.maxInstant = ENDTIME;

        long bigOfTime = STARTTIME / 4;
        greg=Calendar.getInstance(TimeZone.getTimeZone(getZoneId().getId()));
        
        startYear = (int) (matchYear - ((matchTime - bigOfTime) / cycleTime) * cycleYear);
        startTime = matchTime - (matchYear - startYear) / cycleYear * cycleTime;// -3*HOUR;

    }

    public BasicDate getMaximumDate() {
        return getDate(getMaxInstant() - DAY);
    }

    @Override
    public BasicDate getMinimumDate() {
        return getDate(getMinInstant() + DAY);
    }

    /*  
    protected MyBasicCalendar(long cycleTime, int cycleYear, long matchTime, int matchYear) {
        this(cycleTime, cycleYear, matchTime, matchYear, STARTTIME/4, ENDTIME);
        
    }
      /**
     * @return the minInstant
     */
    public long getMinInstant() {
        return minInstant;
    }

    @Override
    public PeriodType getMonthName(BasicDate bd) {
        PeriodType p = getYearType(bd);
        p = p.getSubPeriods().get(bd.getMonth() - 1);
        return (PeriodType) p;
    }

    public BasicDate getDate(int y, PeriodType m, int d) throws DateConfigException {
        PeriodType p = getYearType(y);
        if (!p.getSubPeriods().contains(m)) {
            throw new DateConfigException("month selected does not belong to year type selected.");
        }
        return getDate(y, p.getSubPeriods().indexOf(m) + 1, d);
    }

    protected abstract BasicDate calculateDate(int y, int m, int d);

    public BasicDate getDate(int y, int m, int d) {
        BasicDate b = calculateDate(y, 1, 1);
        int ymc = b.getCalendar().getYearType(b).getSubPeriods().size();
        //     hussam.println("ymc: "+ymc+" year: "+y+" month: "+m+" day:"+d);
        if (ymc < m) {
            //    hussam.println("\tYear Correction: "+(y+1)+" "+(m-ymc)+" "+(d));
            return getDate(y + 1, m - ymc, d);
        }
        b = calculateDate(y, m, 1);
        int mdc = b.getCalendar().getMonthLength(b);
        if (mdc < d) {
            //    hussam.println("mdc: "+mdc+"\tMonthCorrection: "+y+" "+(m+1)+" "+(d-mdc));
            return getDate(y, m + 1, d - mdc);
        } else {
            return calculateDate(y, m, d);
        }
    }

    @Override
    public BasicDate getCycleStart(long time) {
        int x = (int) ((time - getStartTime()) / getLongCycleTime());
        long result = x * getLongCycleTime() + getStartTime();
        BasicDate g = getDate(result);
        //hussam.println(getName()+" Cycle Start: "+g);

        return getDate(result);
    }

    protected boolean isDateInRange(BasicDate bd) {
        return isDateInRange(bd.getDate());
    }

    protected boolean isDateInRange(long d) {
        return (d > getMinInstant() & d < getMaxInstant());
    }

    protected long getMaxInstant() {
        return maxInstant;
    }

    public long getMatchTime() {
        return matchTime;
    }

    public int getMatchYear() {
        return matchYear;
    }

    protected int getStartYear() {
        return startYear;
    }

    public long getStartTime() {
        return startTime + zoneId
                .getRules()
                .getOffset(Instant.ofEpochMilli(startTime))
                .getTotalSeconds() * SECONDS;
    }

    public long getLongCycleTime() {
        return cycleTime;
    }

    public int getLongCycleYear() {
        return cycleYear;
    }

    @Override
    public final boolean isLeapYear(BasicDate bd) {
        return isLeapYear(bd.getYear());
    }

    @Override
    public final PeriodType getYearType(BasicDate bd) {
        return getYearType(bd.getYear());
    }

    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MyBasicCalendar)) {
            return false;
        }
        MyBasicCalendar o = (MyBasicCalendar) obj;
        return (o.getName().equals(getName()));

        // return false;
    }

    @Override
    public int compareTo(BasicCalendar o) {

        return (o.getName().compareTo(getName()));

    }

    @Override
    public int hashCode() {
        return getName().hashCode(); //To change body of generated methods, choose Tools | Templates.
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public long cleanDate(long time2) {
        
        Date d = new Date(time2);
        greg.setTime(d);

        greg.set(Calendar.HOUR_OF_DAY, 12);
        greg.set(Calendar.MINUTE, 0);
        greg.set(Calendar.SECOND, 0);
        greg.set(Calendar.MILLISECOND, 0);//*/

        /*    c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);//*/
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

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }
//*/

}
