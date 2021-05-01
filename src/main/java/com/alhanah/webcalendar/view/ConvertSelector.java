/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.Application;
import com.alhanah.webcalendar.info.Datable;
import static com.alhanah.webcalendar.Application.getT;
import static com.alhanah.webcalendar.view.Util.dateToString;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;

/**
 *
 * @author hmulh
 */
public class ConvertSelector extends com.vaadin.flow.component.orderedlayout.HorizontalLayout implements Datable{
    Select<BasicCalendar> calTextMap = new Select<BasicCalendar>();
    TextField text=new TextField("");
    List<BasicCalendar>conversions;
    BasicDate date;
    List<CalendarsUpdatedListerner>listeners;
    public ConvertSelector(BasicDate date, List<BasicCalendar>conversions) {
        listeners=new ArrayList<>();
        this.date = date;
        this.conversions=conversions;
        text.setReadOnly(true);
        add(calTextMap);
        add(new Span(getT("converts-to")));
        add(text);
        calTextMap.setItems(conversions);
        calTextMap.setItemLabelGenerator(new LabelCalendarGenerator());
        calTextMap.addValueChangeListener((event) -> {
            BasicCalendar base = calTextMap.getValue();
            updateView();
            notifyCalendarChanged();
        });
        calTextMap.setLabel(getT("select-main-calendar"));
        calTextMap.setValue(conversions.get(0));
        updateView();
    }
    public BasicDate getResult(){
        return calTextMap.getValue().getDate(date.getDate());
    }
    public ConvertSelector(BasicDate b) {
        this(b, Application.getFactory().getCalendars());
    }
    public void addCalendarChangeListener(CalendarsUpdatedListerner l){
        listeners.add(l);
    }
    protected void notifyCalendarChanged(){
        List<BasicCalendar> c=Collections.singletonList(calTextMap.getValue());
        for(CalendarsUpdatedListerner l:listeners){
            l.calendarsUpdated(c);
        }
    }

    @Override
    public void setDate(BasicDate bd) {
        date=bd;
        updateView();
        
    }
    
    void updateView(){
       // System.err.println(text.getTitle());
        text.setTitle(getT("by-calendar") + " " + getT(calTextMap.getValue().getName()));
        text.setLabel(getT("by-calendar") + " " + getT(calTextMap.getValue().getName()));
       // System.err.println(calTextMap.getValue().getName()+"\t"+text.getTitle()+"\t"+text.getLabel()+"\t"+text.getValue());
        
        text.setValue(dateToString(getResult()));
        
    }
    
    
    
}
