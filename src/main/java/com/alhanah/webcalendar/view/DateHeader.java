/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.info.Datable;
import static com.alhanah.webcalendar.Application.getT;
import static com.alhanah.webcalendar.view.Util.dateToString;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;
import nasiiCalendar.CalendarFactory;

/**
 *
 * @author hmulh
 */
public class DateHeader extends Div implements Datable {

    BasicDate date;
    TextField dateLabel = new TextField();
   // Label calendarLabel=new Label();
    public DateHeader(BasicDate date) {
        this.date = date;
    //    add(new H3(calendarLabel));
        add(dateLabel);
        dateLabel.setReadOnly(true);
        setDate(date);
        
    }

    public DateHeader() {
        this(CalendarFactory.getSamiCalendar().getDate(System.currentTimeMillis()));
    }
    

    @Override
    public void setDate(BasicDate bd) {
        date = bd;
        String dateString = dateToString(date);
       // String dateString = getT(date.getCalendar().getMonthName(date).getName()) + " " + date.getDay() + " " + date.getYear() + " " + getT(date.getCalendar().getShortName());
        dateLabel.setValue(dateString);
        dateLabel.setLabel(getT(date.getCalendar().getName()+BasicCalendar.INFO_NAME));
        dateLabel.setHelperText(dateToString(CalendarFactory.getGregoryCalendar().getDate(date.getDate())));
    }

}
