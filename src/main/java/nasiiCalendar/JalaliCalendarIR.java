/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import static nasiiCalendar.BasicCalendar.DAY;
import static nasiiCalendar.Holder.devide;
import static nasiiCalendar.Holder.subtract;

/**
 *
 * @author hmulh
 */
public class JalaliCalendarIR extends SolarCalendar {

    private static final long CYCLENORMAL = (365) * DAY;
    private static final long CYCLELEAP = (366) * DAY;

    private static final long CYCLE4 = 3 * CYCLENORMAL + CYCLELEAP;
    private static final long CYCLE5 = CYCLE4 + CYCLENORMAL;
    private static final long CYCLE29 = CYCLE4 * 7 + CYCLENORMAL;
    private static final long CYCLE33 = CYCLE4 * 7 + CYCLE5;
    private static final long CYCLE37 = CYCLE4 * 8 + CYCLE5;
    private static final long CYCLE128 = 3 * CYCLE33 + CYCLE29;
    private static final long CYCLE132 = CYCLE33 * 2 + CYCLE37 + CYCLE29;
    private static final long CYCLE2820 = CYCLE128 * 21 + CYCLE132;

    private static final long START_MATCH = -27542332799000L;//-27542257200001L-10800000L;//-27573793200001L;// -27573793200001L;
    private static final int START_YEAR=476;
    public JalaliCalendarIR(ZoneId zone) {
        super(BasicCalendar.JALALI_IR_ID, CYCLE2820, 2820, START_MATCH, START_YEAR, zone);
    }

    public enum JalalYearType implements PeriodType {
        YNormal("JulianYear", false,  new PeriodType[]{Months.HAMAL, Months.SAWR, Months.JAWZA, Months.SARATAN, Months.ASAD, Months.SONBOLA,
            Months.MIZAN, Months.AQRAB, Months.QAWS, Months.JADI, Months.DALW, Months.HUT}),
        YLeap("JulianLeap", true, new PeriodType[]{Months.HAMAL, Months.SAWR, Months.JAWZA, Months.SARATAN, Months.ASAD, Months.SONBOLA,
            Months.MIZAN, Months.AQRAB, Months.QAWS, Months.JADI, Months.DALW, new LeapMonth(30,Months.HUT)});

        String englishName;
        PeriodType[] months;
        int length;
        boolean leap;
        private JalalYearType(String englishName, boolean leap, PeriodType[] months) {
            this.englishName = englishName;
            this.months = months;
            for(PeriodType p:months){
                length+=p.getTotalLengthInDays();
            }
            this.leap=leap;
        }

        @Override
        public String getName() {
            return englishName;
        }

        @Override
        public List<PeriodType> getSubPeriods() {
            return Arrays.asList(months);
        }
        
        @Override
        public boolean isBigLeap() {
            return false;
        }

        @Override
        public boolean isSmallLeap() {
            return leap;
        }

        @Override
        public int getTotalLengthInDays() {
            return length;
        }

        @Override
        public long getDurationInTime() {
            return getTotalLengthInDays()*DAY;
        }

        @Override
        public int getCountAsPeriods() {
            return 1;
        }
        
    }

    protected int[] getNormalYearMonthLength() {
        return new int[]{31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};
    }

    protected int[] getLeapYearMonthLength() {
        return new int[]{31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 30};
    }

    public boolean isLeapYear(int y) {
        int cycle=y-getStartYear()+getMatchYear();
 
        return isLeap(cycle);
    }

    public static boolean isLeap(int y) {
        
        int loopYear = y - START_YEAR ;
        return test2820(loopYear % 2820);
    }

    private static boolean test128(int c128) {
        if (c128 > 3 * 33) {
      //      hussam.print(">29>");
            return (c128 - 99 + 4) % 4 == 3;
        }
        if (c128 < 3 * 33) {
   //         hussam.print(">33>");
            return test33(c128 % 33);
        } else {
    //        hussam.print(">29>");
            return (c128 - 3 * 33) % 4 == 3;
        }
    }

    private static boolean test2820(int c2820) {
   //     hussam.print("2820>");
        if (c2820 < 21 * 128) {
  //          hussam.print(">128>");
            return test128(c2820 % 128);
        } else {
  //          hussam.print(">132>");
            int rest = c2820 - 21 * 128;
            if (rest < 67) {

                return test33(rest % 33);
            } else if (rest < 67 + 37) {
                //hussam.print(">leap_128>");
                return test37(rest - 66);
            } else {
       //         hussam.print(">29>");
                return (rest - (66 + 37)) % 4 == 3;
            }
        }

        //return false;
    }

    private static boolean test33(int c33) {
        if (c33 > 30) {
      //      hussam.print(">leap5>");
            return test33(c33 - 5);
        }
     //   hussam.print(">33>");
        return c33 % 4 == 3;

    }

    private static boolean test37(int rest) {
        if (rest < 33) {
    //        hussam.print(">37_normal>");
            return test33(rest);
        } else {
     //       hussam.print(">37_leap>");
            return test33(rest - 5);
        }
    }

    @Override
    protected BasicDate calculateDate(int y, int m, int d) {
        // getBase3();
      //  y=y-1;
        long result = 0L;
        int yc = y - getStartYear();
        int cycle2820 = yc / 2820;
        int rest = yc - cycle2820 * 2820;
        result += cycle2820 * CYCLE2820;
        if (rest > 128 * 21) {
            result += 21 * CYCLE128;
            rest -= 128 * 21;
            if (rest > 33 * 2 + 37) {
                result += CYCLE33 * 2 + CYCLE37;
                rest -= 33 * 2 + 37;

                int c4 = rest / 4;
                result += rest / 4 * CYCLE4;
                rest = rest % 4;
                result += rest * CYCLENORMAL;
                
            } else if (rest > 33 * 2) {

                if (rest > 33 * 2) {
                    result += CYCLE33 * 2;
                    rest -= 33 * 2;

                    int div = Math.min(8, rest / 4);
                    result += div * CYCLE4;
                    rest -= 4 * div;

                    result += rest * CYCLENORMAL;
                   
                
                } else {
                    result += CYCLE33 * rest / 33;
                    rest = rest % 33;
                    int div = Math.min(7, rest / 4);
                    result += div * CYCLE4;
                    rest -= 4 * div;

                    result += rest * CYCLENORMAL;
                  
                
                }
            }
        } else {
            result += rest / 128 * CYCLE128;
            rest = rest % 128;
//if(y==1404)hussam.println("f4 "+ rest);
            
            result += rest / 33 * CYCLE33;
            rest = rest % 33;

            int div = Math.min(7, rest / 4);
            result += div * CYCLE4;
            rest = rest - 4 * div;
       //     if(rest==0)result-=DAY;
    //        hussam.print("rest: "+rest+" ");
            result += rest * CYCLENORMAL;
  //          if(y==1404)hussam.println("f4 "+ rest);
                
               
        }
        TreeMap<Integer, Long> dur = null;
        if (isLeapYear(y)) {
            dur = LEAP_YEAR_DURATION;
        } else {
            dur = NORMAL_YEAR_DURATION;
        }
   //     hussam.println("\tMdur: "+dur.get(m-1)/DAY+"\tyeardur: "+result/DAY+ "\t"+y+" "+m+" "+d);
        
        result += dur.get(m - 1) + (d - 1) * DAY + getStartTime();

        if (!isDateInRange(result)) {
            return getMaximumDate();
        }

        MyDate md = new MyDate(result, d, m, y, this);
        return md;

    }

    
    

    @Override
    public BasicDate getDate(long t) {
        
        long time2 = cleanDate(t);
        if (!isDateInRange(time2)) {
            return getMaximumDate();
        }
        
        long time = time2 - getStartTime();
        Holder r=new Holder();
        r.timeDuration=time;
    //    hussam.println("starttime: "+new Date(getStartTime()));
    //    hussam.println("days: "+time/YEAR_TROPICAL);
        r.periodDuration=getStartYear();
        Holder base = devide(r, CYCLE2820, 2820, 0);
   //     hussam.println("years: "+base.rest/BasicCalendar.YEAR_TROPICAL);
     //   hussam.println("startYear: "+getStartYear());
       // hussam.println(""+base);
        r = subtract(base.clone(), CYCLE128 * 21, 128 * 21);
        if (r.equals(base)) {
            base = devide(base, CYCLE128, 128, 0);
  //          hussam.println(CYCLE128);
    //        hussam.println("after deviding 128"+base);
            r = subtract(base.clone(), CYCLE33 * 3, 99);
            if (base.equals(r)) {
                base = devide33(base);
   //         hussam.println(""+base);
            } else {
                base = devide29(r);
   //         hussam.println(""+base);
        }
        } else {
            base = r;
            r = subtract(base.clone(), CYCLE33 * 2 + CYCLE37, 33 * 2 + 37);
            if (base.equals(r)) {
                r = subtract(base.clone(), CYCLE33 * 2, 66);
                if (base.equals(r)) {
                    base = devide33(base);
//hussam.println(""+base);
        
                } else {
                    base = r;
                    r = subtract(base.clone(), CYCLE4 * 8, 4 * 8);
                    if (base.equals(r)) {
                        base = devide29(base);
   //         hussam.println(""+base);
                } else {
                        base = r;
                        base = devide(base, CYCLENORMAL, 1, 4);
   //         hussam.println(""+base);
                }
                }
            } else {
                base = devide29(r);
//hussam.println(""+base);
        
            }
        }

        int year = base.periodDuration ;//+ getStartYear();
        long rest = base.timeDuration;
        Map.Entry<Long, Integer> monthKey = (isLeapYear(year)) ? LEAP_YEAR.floorEntry(rest) : NORMAL_YEAR.floorEntry(rest);
        rest = rest - monthKey.getKey();
        int month = monthKey.getValue();
        int day = (int) (rest / DAY);
   //        hussam.println("foirst y1: " + year + " m:" + (month ) + " d:" + (day + 1));
   //     hussam.println("time: "+new Date(t)+ " "+year+" "+(month+1)+" "+(day+1)+" dur"+time/DAY);
        BasicDate d = getDate(year, month + 1, day + 1);
        return d;
    }

    private Holder devide29(Holder base) {
        base = devide(base, CYCLE4, 4, 0);
        base = devide(base, CYCLENORMAL, 1, 3);
        return base;
    }

    private Holder devide33(Holder base) {
        base = devide(base, CYCLE33, 33, 0);
        Holder r = subtract(base.clone(), CYCLE4 * 7, 4 * 7);
        if (base.equals(r)) {
            base = devide(base, CYCLE4, 4, 0);
            base = devide(base, CYCLENORMAL, 1, 3);
      //      hussam.println("div33 notmla");
        } else {
        //    hussam.println("div33 leap");
            base = r;
            base = devide(base, CYCLENORMAL, 1, 4);
        }
        return base;
    }
/*
    public BasicDate getDate2(long t) {

        long time2 = cleanDate(t);
        if (!isDateInRange(time2)) {
            return getDummyDate();
        }

        long time = time2 - getStartTime();

        int cycle2820 = (int) (time / CYCLE2820);
        long rest = time % CYCLE2820;
        int year = cycle2820 * 2820;
        int cycle128 = (int) (rest / CYCLE128);
        if (cycle128 >= 21) {
            cycle128 = 21;
            year += cycle128 * 128;
            rest = rest - cycle128 * 128;

            int cycle = (int) (rest % (2 * CYCLE33 + CYCLE37));
            if (cycle > 0) {
                year += 33 + 33 + 37;
                rest -= (2 * CYCLE33 + CYCLE37);

            }
        } else {
            year += cycle128 * 128;
            rest = rest % CYCLE128;

            int cycle33 = (int) (rest / CYCLE33);
            if (cycle33 < 3) {
                rest = rest % CYCLE33;
                year += cycle33 * 33;
                int cycle4 = (int) (rest / CYCLE4);
                int div = 3;
                if (cycle4 >= 7) {
                    cycle4 = 7;
                    div = 4;
                }
                rest = rest - cycle4 * CYCLE4;
                year += cycle4 * 4;
                int yp = (int) Math.min(div, rest / CYCLENORMAL);
                rest = rest - yp * CYCLENORMAL;
                year += yp;

            } else {
                rest = rest - CYCLE33 * 3;
                year += 33 * 3;
                int cycle4 = (int) (rest / CYCLE4);
                rest = rest % cycle4;
                year += cycle4 * 4;

            }
        }
        int cycle100 = (int) (rest / CYCLE100);
        rest = rest - cycle100 * CYCLE100;
        int cycle4 = (int) (rest / CYCLE4);
        rest = rest - (cycle4 * CYCLE4);
        int years = (int) Math.min(3, rest / (365 * DAY));
        rest = rest - years * ((365) * DAY);
        int y1 = cycle400 * 400 + cycle100 * 100 + cycle4 * 4 + years + getStartYear();
        //     hussam.println("t:"+new Date(t)+" ct:"+new Date(time2)+" dur: "+time/YEAR_TROPICAL+" rest:"+rest+" startYear:"+y1);
        Map.Entry<Long, Integer> monthKey = (years == 3) ? LEAP_YEAR.floorEntry(rest) : NORMAL_YEAR.floorEntry(rest);
        rest = rest - monthKey.getKey();
        int month = monthKey.getValue();
        int day = (int) (rest / DAY);
        //   hussam.println("foirst y1: " + y1 + " m:" + (month + 1) + " d:" + (day + 1));

        BasicDate d = getDate(y1, month + 1, day + 1);

        //   System.out.println("time: " + new Date(d.getDate())+" "+years);
    }//*/


    @Override
    public PeriodType getYearType(int year) {
        return isLeap(year) ? JalalYearType.YLeap : JalalYearType.YNormal;
    }

    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(JalalYearType.values());
    }

}
