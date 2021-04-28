/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.Application.getT;
import static com.alhanah.webcalendar.HanahI18NProvider.AR;
import com.vaadin.flow.component.Component;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import nasiiCalendar.BasicCalendar;
import static nasiiCalendar.BasicCalendar.DAY;
import nasiiCalendar.Months;
import nasiiCalendar.BasicDate;
import nasiiCalendar.locationBasid.City;

/**
 *
 * @author hmulh
 */
public class Util {

    public static BasicDate getNextMonthByName(BasicDate bd, Months... m) {
        List<Months> ms = Arrays.asList(m);
        BasicDate search = bd.getCalendar().getDate(bd.getYear(), bd.getMonth() + 1, 1);
        while (!ms.contains(search.getCalendar().getMonthName(search))) {
            search = bd.getCalendar().getDate(bd.getYear(), bd.getMonth() + 1, 1);
        }
        return search;
    }

    public static int getNextSpecialDay(BasicDate start, BasicDate standard) {
        while (standard.getDate() < start.getDate()) {
            standard = standard.getCalendar().getDate(standard.getYear() + 1, standard.getMonth(), standard.getDay());

        }
        return (int) ((standard.getDate() - start.getDate()) / DAY);
    }

    public static String dateToString(BasicDate bd) {
        return getT(bd.getCalendar().getMonthName(bd).getName()) + " " + bd.getDay() + " " + bd.getYear() + " " + getT(bd.getCalendar().getName()+BasicCalendar.INFO_SHORT);
        
    }

    static void makeLTR(Component com) {
        com.getElement().setAttribute("dir", "ltr");
    }
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public static DateTimeFormatter getTimeFormatter() {
        return formatter;
    }

    public static String getCityName(City city) {
        return Application.getSelectedLocale().equals(AR) ? city.getArabName() : city.getName();//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
