/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.beans;

import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.view.CalendarsUpdatedListerner;
import com.alhanah.webcalendar.view.CellFormatter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;
import nasiiCalendar.CalendarFactory;

/**
 *
 * @author hmulh
 */
//@CssImport(value = "/views/comps/textAreaCell.css", themeFor = "vaadin-text-field")
public class ConvertedDayCell extends ComponentRenderer<Component, WeekBean> implements CalendarsUpdatedListerner {

    List<BasicCalendar> cals;
    int weekDay;

    public ConvertedDayCell(int weekDay) {
        this(weekDay, Application.getFactory().getSamiCalendar(), Application.getFactory().getAdCalendar(), Application.getFactory().getCalendar(BasicCalendar.UMM_ALQURA_CALENDAR_V1423));
    }

    public ConvertedDayCell(int weekDay, BasicCalendar... list) {
        this.weekDay = weekDay;
        cals = new ArrayList<BasicCalendar>(Arrays.asList(list));
        
    }

    public int getWeekDay() {
        return weekDay;
    }

    @Override
    public Component createComponent(WeekBean week) {
        // text field for entering a new name for the person

        StringBuilder build = new StringBuilder();
        BasicDate date = cals.get(0).getDate(week.getDay(weekDay));//.getDay();
        String x = getFormated(date);
        TextArea textArea = new TextArea(x);
        String myClass="season"+CellFormatter.prepareSeasons(date.getDate(), Application.getFactory().getDefaultSeasonIdentifier());
        textArea.addClassName(myClass);
        if (weekDay == 1) {
            textArea.setLabel(getFormatedWeek(date));
        }
        long test = date.getDate();
        for (int i = 1; i < cals.size(); i++) {
            if (i != 1) {
                build.append("\n");
            }
            BasicCalendar temp = cals.get(i);
            date = temp.getDate(test);
            if (weekDay == 1) {
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

//@CssImport(value = "/views/comps/textAreaCell.css", themeFor = "vaadin-text-area")
class MyTextArea extends TextArea {

    public MyTextArea(String x) {
        super(x);
    }

}
