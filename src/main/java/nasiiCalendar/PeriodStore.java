/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import static nasiiCalendar.BasicCalendar.DAY;

/**
 *
 * @author hmulh
 */
public class PeriodStore implements PeriodType {

    TreeMap<Integer, Long> ids = new TreeMap<>();
    TreeMap<Long, Integer> times = new TreeMap<>();
    TreeMap<Integer, PeriodType> periodIds = new TreeMap<>();
    TreeMap<Long, PeriodType> periodDuration = new TreeMap<>();

    final int daysLength;
    final int periodLength;
    final long duration;
    String name;
    final boolean smallLeap, bigLeap;

    public PeriodStore(String name, boolean sl, boolean bl, PeriodType[] array) {
        this.smallLeap = sl;
        this.bigLeap = bl;
        int totalDays = 0;
        int totalPeriods = 0;
        long dur = 0;
        for (int i = 0; i < array.length; i++) {
            PeriodType p = array[i];
            int x = p.getTotalLengthInDays();
            long d = p.getDurationInTime();
            int per = p.getCountAsPeriods();
            dur += d;
            totalDays += x;
            totalPeriods += per;
        }
        periodLength = totalPeriods;
        daysLength = totalDays;
        duration = dur;
        prepare(array);
        this.name = name;
        
    }

    public PeriodStore(boolean sl, boolean bl, PeriodType[] array) {
        this("group",sl, bl, array);
    }
    
    public int getTotalLengthInDays() {
        return daysLength;
    }

    public Holder calcPeriod(Holder h) {
        Map.Entry<Long, Integer> entry = times.floorEntry(h.timeDuration);//.
        Map.Entry<Long, PeriodType> entryTimes = periodDuration.floorEntry(h.timeDuration);//.
        h.timeDuration -= entry.getKey();
        h.periodDuration += entry.getValue();
        PeriodType sub = entryTimes.getValue();
        if (sub instanceof PeriodStore) {
            PeriodStore ps = (PeriodStore) sub;
            //Holder h2=new Holder();
            ps.calcPeriod(h);
        }

        return h;
    }

    /**
     * Returns the time required given years
     *
     * @param h
     * @return
     */
    public Holder calcTimes(Holder h) {
        Map.Entry<Integer, Long> entry = ids.floorEntry(h.periodDuration);
        Map.Entry<Integer, PeriodType> entryPeriod = periodIds.floorEntry(h.periodDuration);

        h.periodDuration -= entry.getKey();
        h.timeDuration += entry.getValue();
        PeriodType sub = entryPeriod.getValue();
        if (sub instanceof PeriodStore) {
            PeriodStore p = (PeriodStore) sub;
            p.calcTimes(h);
        } else {

        }
        return h;

    }

    public Map.Entry<Integer, PeriodType> getSubPeriod(int x) {
        //hussam.println(x+"\t"+periodIds);
        Map.Entry<Integer, PeriodType> t = periodIds.floorEntry(x);
        x -= t.getKey();
        if (t.getValue() instanceof BasicYear) {
            return t;

        } else {
            PeriodStore p = (PeriodStore) t.getValue();
            return p.getSubPeriod(x);
        }
    }

    public Map.Entry<Long, Integer> getPeriod(long time) {
        return times.floorEntry(time);
    }

    public Map.Entry<Integer, Long> getPeriod(int x) {
        return ids.floorEntry(x);
    }

    protected void prepare(PeriodType[] periodList) {
        int x = 0;
        long time = 0;

        for (int i = 0; i < periodList.length; i++) {
            PeriodType t = periodList[i];
            int len = t.getTotalLengthInDays();
            time = add(t, x, time) + len * DAY;
            x += t.getCountAsPeriods();
        }

    }

    protected long add(PeriodType t, int x, long time) {
        ids.put(x, time);
        times.put(time, x);
        periodDuration.put(time, t);
        periodIds.put(x, t);
        //  periodList.add(t);//time);
        //  time+=t.getPeriodLengths()[0]*DAY;
        //    hussam.println("time add: "+time/(DAY*345));
        return time;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<PeriodType> getSubPeriods() {
        return new ArrayList<>(periodIds.values());
    }

    @Override
    public boolean isBigLeap() {
        return bigLeap;
    }

    @Override
    public boolean isSmallLeap() {
        return smallLeap;
    }

    @Override
    public long getDurationInTime() {
        return duration;
    }


    @Override
    public int getCountAsPeriods() {
        return periodLength;
    }

    @Override
    public String toString() {
        return getName();
    }

}
