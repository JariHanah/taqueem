/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.info.Datable;
import com.alhanah.webcalendar.info.FutureEventCalc;
import static com.alhanah.webcalendar.Application.getT;
import static com.alhanah.webcalendar.view.Util.dateToString;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import static nasiiCalendar.BasicCalendar.DAY;
import nasiiCalendar.BasicDate;
import nasiiCalendar.CalendarFactory;

/**
 *
 * @author hmulh
 */
public class DayDifference extends Span implements Datable{
    BasicDate base;
    FutureEventCalc future;
    //TextField daysCount;
    TextField date;
    String label;
    public DayDifference(String label,BasicDate bd1, FutureEventCalc bd2) {
        this.label=label;
        base=bd1;
        future=bd2;
        //daysCount=new TextField(getT("difference"));
        date=new TextField(label);
        //daysCount.setReadOnly(true);
        date.setReadOnly(true);
        add(date);
        //add(daysCount);
        updateView();
    }
    
    @Override
    public void setDate(BasicDate bd) {
        base=bd;
        updateView();
    }
    public void updateView(){
        BasicDate ad=future.getNext(base);
        long timeDiff=ad.getDate()-base.getDate();
        int days=(int) (timeDiff/DAY);
        date.setLabel(label+" "+getT("after")+" "+days+" "+getT("days"));
        date.setValue(dateToString(ad));
        String text=dateToString(CalendarFactory.getGregoryCalendar().getDate(ad.getDate()));
        date.setHelperText(text);
        //daysCount.setValue(days+" "+getT("days"));
        
    }
    
    
    
}
