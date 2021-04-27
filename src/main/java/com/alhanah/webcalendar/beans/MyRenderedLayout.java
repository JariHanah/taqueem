/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.beans;

import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.view.CalendarsUpdatedListerner;
import com.alhanah.webcalendar.view.CellFormatter;
import com.alhanah.webcalendar.info.Datable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nasiiCalendar.BasicCalendar;
import static nasiiCalendar.BasicCalendar.DAY;
import nasiiCalendar.BasicDate;
import nasiiCalendar.CalendarFactory;

/**
 *
 * @author hmulh
 */
//@CssImport(value = "/views/comps/textarea.css", themeFor = "vaadin-text-area")
public class MyRenderedLayout extends VerticalLayout implements CalendarsUpdatedListerner {
    public static final int NORMAL=10;
    public static final int HEAD_ROW=20;
    
    List<BasicCalendar> cals;
    List<TextArea>areas;
    BasicDate basic;
    
    int type=NORMAL;
    long time;
    String width="50px";
    public MyRenderedLayout(long t) {
        this(t,NORMAL,CalendarFactory.getSamiCalendar(), CalendarFactory.getAdCalendar(), CalendarFactory.getInstance().getCalendar(BasicCalendar.UMM_ALQURA_CALENDAR_V1423));
    }

    public MyRenderedLayout(long t, int type,BasicCalendar... list) {
        cals = new ArrayList<BasicCalendar>(Arrays.asList(list));
        areas=new ArrayList<TextArea>();
        areas.add(getMyComponent());
        time=t;
        this.type=type;
        this.setClassName("gridlabel");
        this.setWidth(width);
        add(areas.get(0));
        setDate(time);
    }

    //@Override
    private void setDate(long time) {
        
        StringBuilder build = new StringBuilder();
        BasicDate date = cals.get(0).getDate(time);//.getDay();
        String label = (type == HEAD_ROW)?getFormatedWeek(date):getFormated(date);
        
        String myClass="season"+CellFormatter.prepareSeasons(date.getDate(), CalendarFactory.getDefaultSeasonIdentifier());
        areas.get(0).addClassName(myClass);
       // areas.get(0).getElement().getStyle().set("background", "red");
        areas.get(0).setLabel(label);
        areas.get(0).setWidth(width);
        long test = date.getDate();
        for (int i = 1; i < cals.size(); i++) {
            if (i != 1) {
                build.append("\n");
            }
            BasicCalendar temp = cals.get(i);
            date = temp.getDate(test);
            if (type == HEAD_ROW) {
                build.append(getFormatedWeek(date));
            } else {
                build.append(getFormated(date));
            }

        }
        areas.get(0).setValue(build.toString());
    }
    
    private TextArea getMyComponent(){
        TextArea textArea=new TextArea();
        textArea.setReadOnly(true);
        //textArea.getElement().getStyle().set(name, value)
        return textArea;
    }
    
    /*
    @Override
    public Component createComponent(WeekBean week) {
        // text field for entering a new name for the person

        StringBuilder build = new StringBuilder();
        BasicDate date = cals.get(0).getDate(week.getDay(time));//.getDay();
        String x = getFormated(date);
        TextArea textArea = new TextArea(x);
        String myClass="season"+CellFormatter.prepareSeasons(date.getDate(), CalendarFactory.getDefaultSeasonIdentifier());
        textArea.addClassName(myClass);
        if (time == 1) {
            textArea.setLabel(getFormatedWeek(date));
        }
        long test = date.getDate();
        for (int i = 1; i < cals.size(); i++) {
            if (i != 1) {
                build.append("\n");
            }
            BasicCalendar temp = cals.get(i);
            date = temp.getDate(test);
            if (time == 1) {
                build.append(getFormatedWeek(date));
            } else {
                build.append(getFormated(date));
            }

        }
        //System.err.println("biulding... "+build);
        textArea.setReadOnly(true);
        textArea.setValue(build.toString());
        return new VerticalLayout(textArea);
    }
//*/
    public String getFormated(BasicDate date) {
        String x = null;
        // System.err.println(dateToString(date));
        switch (date.getDay()) {
            case 1:
                x = getT(date.getCalendar().getMonthName(date).getName() + BasicCalendar.INFO_SHORT);
                if (date.getMonth() == 1) {
                    x = x;//+ " " + date.getYear();
                }   break;
            case 2:
                x = date.getYear() + "";
                break;
            case 3:
                x = getT(date.getCalendar().getName() + BasicCalendar.INFO_REF);
                break;
            default:
                x = date.getDay() + "";
                break;
        }
        return x;
    }

    

    private String getFormatedWeek(BasicDate date) {
        if (date.getDay() < 4) {
            return getFormated(date);
        }
        String x = null;

        x = getT(date.getCalendar().getMonthName(date).getName() + BasicCalendar.INFO_SHORT);
        return x;
    }

    @Override
    public void calendarsUpdated(List<BasicCalendar> list) {
        cals.clear();
        cals.addAll(list);
    }

}
