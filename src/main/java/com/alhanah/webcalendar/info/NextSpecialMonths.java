/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.info;

import com.alhanah.webcalendar.info.FutureEventCalc;
import java.util.Arrays;
import java.util.List;
import nasiiCalendar.BasicDate;
import nasiiCalendar.Months;
import nasiiCalendar.PeriodType;

/**
 *
 * @author hmulh
 */
public class NextSpecialMonths implements FutureEventCalc {

    List<Months> month;

    public NextSpecialMonths(Months... month) {
        this.month = Arrays.asList(month);
    }

    @Override
    public BasicDate getNext(final BasicDate b) {
        int year = 0;
        BasicDate result = null;
        PeriodType yt = null;
        all:
        while (result == null) {
            for (Months m : month) {
                yt = b.getCalendar().getYearType(b.getYear() + year);
                if (yt.getSubPeriods().contains(m)) {
                    BasicDate future = b.getCalendar().getDate(b.getYear() + year, yt.getSubPeriods().indexOf(m) + 1, 1);
                    if (b.getDate() < future.getDate()) {
                        result = future;
                        break all;
                    }
                }

            }
            year++;
            //if(year>10)throw new RuntimeException("what is going on");
           // yt = b.getCalendar().getYearType(b.getYear() + year);

        }
        
        return result;
    }
}
