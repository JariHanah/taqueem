/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.Application;
import com.alhanah.webcalendar.info.Datable;
import static com.alhanah.webcalendar.Application.getT;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import nasiiCalendar.BasicDate;

/**
 *
 * @author hmulh
 */
public class WeekDay extends Span implements Datable{
    BasicDate date;
    TextField dayOfWeek = new TextField();
   // Label calendarLabel=new Label();
    public WeekDay(BasicDate date) {
        this.date = date;
    //    add(new H3(calendarLabel));
        add(dayOfWeek);
        dayOfWeek.setReadOnly(true);
        setDate(date);
        
    }

    @Override
    public void setDate(BasicDate bd) {
        date = bd;
        String dateString = Application.getFactory().getWeekDay(date);//getT(date.getCalendar().getMonthName(date).getName()) + " " + date.getDay() + " " + date.getYear() + " " + getT(date.getCalendar().getShortName());
        dayOfWeek.setValue(getT(dateString));
    //    calendarLabel.setText(getT(date.getCalendar().getName()));
        dayOfWeek.setLabel(getT("weekday"));

    }

}
