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
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;

/**
 *
 * @author hmulh
 */
public class Render1LineDate implements RenderMyDate {

    String width ;
    BasicCalendar show;

    public Render1LineDate(BasicCalendar show, String width) {
        this.show = show;
        this.width=width;
    }

    public Render1LineDate() {
        this(Application.getFactory().getCalendar(BasicCalendar.AD_ID), "300px");
    }
    
    
    @Override
    public Component renderDate(BasicDate bd) {
        VerticalLayout v=new VerticalLayout();
        BasicDate result = bd;
        TextField text = new TextField();
        
        text.setValue(Util.dateToString(bd));
        //text.setHelperText(Util.dateToString(show.getDate(bd.getDate())));
        text.setReadOnly(true);
        v.add(text);
        CellFormatter.prepareSpecialPeriods(result, text);
        CellFormatter.prepareSeasons(v, bd, Application.getFactory().getSeasonIdentifier());
        return v;
    }

    @Override
    public String getWidth() {
        return width;
    }
    

}
