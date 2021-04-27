/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.info;

import com.alhanah.webcalendar.info.FutureEventCalc;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nasiiCalendar.BasicDate;
import nasiiCalendar.DateConfigException;
import nasiiCalendar.PeriodType;

/**
 *
 * @author hmulh
 */
public class NextSpecialDay implements FutureEventCalc {

    List<BasicDate> days;

    public NextSpecialDay(BasicDate ... ds) {
        this.days = Arrays.asList(ds);
    }

    @Override
    public BasicDate getNext(final BasicDate base) {
        int year = 0;
        BasicDate result = null;
        all:
        while (result == null) {
            for (BasicDate sampleFuture : days) {
                PeriodType m= sampleFuture.getCalendar().getMonthName(sampleFuture);
                try {
                    BasicDate f1=sampleFuture.getCalendar().getDate(base.getYear()+year, m, sampleFuture.getDay());
                    if (base.getDate() < f1.getDate()) {
                        result = f1;
                        break all;
                    }
                
                } catch (DateConfigException ex) {
                    Logger.getLogger(NextSpecialDay.class.getName()).log(Level.SEVERE, null, ex);
                }
                

            }
            year++;
            //if(year>10)throw new RuntimeException("what is going on");
           // yt = b.getCalendar().getYearType(b.getYear() + year);

        }
        
        return result;
    }
}
