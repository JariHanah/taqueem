/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.Application;
import com.alhanah.webcalendar.info.Datable;
import static com.alhanah.webcalendar.view.Util.dateToString;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;

/**
 *
 * @author hmulh
 */
public class ConversionsList extends Span implements Datable{
    
    BasicDate date;
    List<BasicCalendar>cals;
    Map<BasicCalendar, ConvertSelector> map = new TreeMap<>();
    Label head = new Label();
    
    public ConversionsList(BasicDate date, List<BasicCalendar> conversions) {
        this.date = date;
        this.cals = conversions;
        for (BasicCalendar bctemp : conversions) {
            ConvertSelector selector=new ConvertSelector(date, Application.getFactory().getCalendars());
            map.put(bctemp, selector);
            add(selector);
            
        }
        updateView();
    }

    @Override
    public void setDate(BasicDate bd) {
        date=bd;
        updateView();
    }
    public void updateView() {
        head.setText(dateToString(date));
        for (BasicCalendar bctemp : cals) {
            //System.err.println("searching: "+bctemp.getName());
            ConvertSelector t1 = map.get(bctemp);
            if(t1==null){
                System.err.println("Found Cal Empty no Text: "+bctemp);
                continue;
            }
            t1.setDate(date);
            

        }
    }
        
    
    
}
