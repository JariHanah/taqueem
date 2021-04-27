/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.info;

import com.alhanah.webcalendar.Application;
import com.alhanah.webcalendar.view.MyParameters;
import static com.alhanah.webcalendar.HanahI18NProvider.AR;
import com.vaadin.flow.component.UI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.CalendarFactory;

/**
 *
 * @author hmulh
 */
public class MyRequestReader {

    /**
     * @return the clientZoneId
     */
    public String getClientZoneId() {
        return clientZoneId;
    }

    /**
     * @return the clientDate
     */
    public Date getClientDate() {
        return clientDate;
    }

    Map<String, String[]> params;

    BasicCalendar base = null;
    List<BasicCalendar> conversions = new ArrayList<BasicCalendar>();
    long selectedTime = 0;
    Locale locale;

    int raw = 0;
    int offset = 0;
    private Date clientDate;
    private String clientZoneId;
    public MyRequestReader(Map<String, String[]> params) {
        UI.getCurrent().getPage().retrieveExtendedClientDetails(extendedClientDetails -> {
            raw = extendedClientDetails.getRawTimezoneOffset();
            offset = extendedClientDetails.getTimezoneOffset();
            clientDate=extendedClientDetails.getCurrentDate();
            clientZoneId= extendedClientDetails.getTimeZoneId();
            
            
        });
        
        this.params = params;
        readRequest();
    }

    public BasicCalendar getCalendar() {
        return base;
    }
    public int getOffSet(){
        return offset;
    }
    public int getRaw(){
        return raw;
    }
    public List<BasicCalendar> getCalendars() {
        return conversions;
    }

    public long getSelectedTime() {
        return selectedTime;
    }

    void readRequest() {
        String[] cals = params.get(MyParameters.CONVERTS);
    //    System.err.println("params converts: " + cals);
        if (cals != null) {
            for (String x : cals) {
            //    System.err.println("\t" + x);
                BasicCalendar bc = Application.getFactory().getCalendar(x);
           //     System.err.println("bc: " + bc);
                if (bc != null) {
                    conversions.add(bc);
                }
            }
        } else {
            conversions.addAll(Application.getFactory().getCalendars());
        }
        String[] myTime = params.get(MyParameters.MILI_TIME);
        selectedTime = System.currentTimeMillis();
        if (myTime != null) {
            selectedTime = Long.parseLong(myTime[0]);
        }
        String[] type = params.get(MyParameters.CAL_TYPE);
        if (type != null) {
            base = Application.getFactory().getCalendar(type[0]);//getAdCalendar();
        }
        if (base == null) {
            base = CalendarFactory.getAdCalendar();
        }
        String[] loc = params.get(MyParameters.LANG);
        if (loc != null) {
            if (loc[0].equals("ar")) {
                locale = AR;
            } else {
                locale = Locale.ENGLISH;
            }
            //Application.setLocale(locale);
            // System.err.println("selected: "+loc);
        } else {
            locale = AR;
        }

    }

    public Locale getLocale() {
        return locale;
    }
}
