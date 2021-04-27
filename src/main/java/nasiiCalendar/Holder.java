/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

/**
 *
 * @author hmulh
 */
class Holder {

    long timeDuration;
    int periodDuration;
    int monthDuration;
    int dayDuration;
    int selectedPeriod;
    PeriodType type;
    //PeriodType subPeriod;
    Holder subHolder;
    
    @Override
    public boolean equals(Object obj) {
        Holder h = (Holder) obj;
        return (h.timeDuration == timeDuration && h.periodDuration == periodDuration);
    }

    protected Holder clone() {
        Holder h = new Holder(); //To change body of generated methods, choose Tools | Templates.
        h.timeDuration = timeDuration;
        h.periodDuration = periodDuration;
        return h;
    }

    public String toString() {
        return " PerTime:" + timeDuration+ " DurPer: " + periodDuration +  " M: "+monthDuration+" D:"+dayDuration;
    }

    public static Holder subtract(Holder r, long div, int length) {
        long x = r.timeDuration - div;
        if (x < 0) {
            return r;
        }
        r.timeDuration = x;
        r.periodDuration = r.periodDuration + length;
        return r;
    }

    public static Holder devide(Holder r, long div, int length, int max) {
        int x = (int) (r.timeDuration / div);
        if (max != 0 & x > max) {
            x = max;
        }
        if (x <= 0) {
            return r;
        }
        //    hussam.println("x: "+x + " length: "+length);
        r.timeDuration = r.timeDuration - div * x;
        r.periodDuration = r.periodDuration + length * x;

        return r;
    }
}
