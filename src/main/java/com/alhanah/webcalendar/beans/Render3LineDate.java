/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.beans;

import com.alhanah.webcalendar.Application;
import com.alhanah.webcalendar.view.CellFormatter;
import com.alhanah.webcalendar.view.Util;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextField;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;

/**
 *
 * @author hmulh
 */
public class Render3LineDate implements RenderMyDate {

    String width ;
    BasicCalendar show;

    public Render3LineDate(BasicCalendar show, String width) {
        this.show = show;
        this.width=width;
    }

    public Render3LineDate() {
        this(Application.getFactory().getCalendar(BasicCalendar.AD_ID), "100px");
    }
    
    
    @Override
    public Component renderDate(BasicDate bd) {
        BasicDate result = bd;
        TextField text = new TextField();
        text.setWidth(width);
        text.setReadOnly(true);

        text.setLabel(Util.dateToYear(result));
        text.setValue(Util.dateToDayMonthOnly(result));
        text.setHelperText(Util.dateToString(show.getDate(result.getDate())));

        CellFormatter.prepareSpecialPeriods(result, text);

        return text;
    }

    @Override
    public String getWidth() {
        return width;
    }
    

}
