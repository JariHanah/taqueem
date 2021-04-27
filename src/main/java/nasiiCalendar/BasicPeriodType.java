/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.util.Arrays;
import java.util.List;
import static nasiiCalendar.SamiFixed.getNormalCentury;

/**
 *
 * @author hmulh
 */
public class BasicPeriodType {//implements PeriodType {
/*
    String name;
    PeriodType[] periods;
    int[] periodLengths;
    boolean bigLeap;
    boolean smallLeap;
    PeriodStore store;

    private BasicPeriodType(String name, boolean bigLeap, boolean smallLeap, PeriodType[] pt, int[]lengths) {
        this.name = name;
        this.periods = pt;
        this.periodLengths = lengths;
        this.bigLeap = bigLeap;
        this.smallLeap = smallLeap;
        this.store = new PeriodStore(pt, periodLengths);
    }
    private BasicPeriodType(String name, boolean bigLeap, boolean smallLeap, PeriodType[] pt) {
        this.name = name;
        this.periods = pt;
        periodLengths=new int[pt.length+1];
        
        this.bigLeap = bigLeap;
        this.smallLeap = smallLeap;
        this.store = new PeriodStore(pt, periodLengths);
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<PeriodType> getSubPeriods() {
        return Arrays.asList(periods);
    }

    @Override
    public int[] getPeriodLengths() {
        return periodLengths;
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
    public PeriodStore getStore() {
        return store;
    }
//*/
}
