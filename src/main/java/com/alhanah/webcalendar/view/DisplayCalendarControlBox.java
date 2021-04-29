/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.Application;
import com.alhanah.webcalendar.info.Datable;
import static com.alhanah.webcalendar.Application.getT;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;

/**
 *
 * @author hmulh
 */
public class DisplayCalendarControlBox extends Div implements Datable{
    CalendarManualSort sorted;
    Button confirm;
    List<CalendarsUpdatedListerner>listeners;
    public DisplayCalendarControlBox(List<BasicCalendar>calendars) {
        sorted=new CalendarManualSort(calendars, Application.getFactory().getCurrentDate());
        confirm=new Button(getT("confirm"), (event) -> {
            Notification.show(getT("loading"));
            notifyMyListeners();
            Notification.show(getT("finishedLoading"));
        });
        listeners=new ArrayList<>();
        add(sorted);
        add(confirm);
        setWidthFull();
    }
    public DisplayCalendarControlBox(){
        this(Application.getFactory().getCalendars());
        //this(Arrays.asList(Application.getFactory().getSamiCalendar(),Application.getFactory().getGregoryCalendar(), Application.getFactory().getCalendar(BasicCalendar.OMARI_ID_16)));
    }
    public CalendarGrid getGrid(BasicDate base){
        return new CalendarGrid(base, 52, sorted.getMySortedList().stream().toArray(BasicCalendar[]::new));
        
    }
    public void addListener(CalendarsUpdatedListerner l){
        listeners.add(l);
    }
    public void notifyMyListeners(){
        listeners.forEach(e->e.calendarsUpdated(sorted.getMySortedList()));
        System.err.println("Sorted List: "+sorted.getMySortedList());
    }

    @Override
    public void setDate(BasicDate bd) {
        sorted.setDate(bd);
        
    }
    
}
