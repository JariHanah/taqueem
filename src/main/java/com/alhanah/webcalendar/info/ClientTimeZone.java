/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.info;

import com.vaadin.flow.component.UI;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;
import nasiiCalendar.CalendarFactory;

/**
 *
 * @author hmulh
 */
public class ClientTimeZone {

   
    private static int raw;
    private static int offset;
    private static Date clientDate;
    private static String clientZoneId=ZoneId.systemDefault().getId();
    private static boolean flag=false;
    private TimeZone zone=TimeZone.getDefault();
    
    static {
        updateTimeZone();
    }
    public static void updateTimeZone() {
        UI.getCurrent().getPage().retrieveExtendedClientDetails(extendedClientDetails -> {
            raw = extendedClientDetails.getRawTimezoneOffset();
            offset = extendedClientDetails.getTimezoneOffset();
            clientDate=extendedClientDetails.getCurrentDate();
            clientZoneId= extendedClientDetails.getTimeZoneId();
            TimeZone zone=TimeZone.getTimeZone(clientZoneId);
            CalendarFactory.setTimeZone(zone);
            flag=true;
            
        });
    }
    
     /**
     * @return the raw
     */
    public static int getRaw() {
        return raw;
    }

    /**
     * @return the offset
     */
    public static int getOffset() {
        return offset;
    }

    /**
     * @return the clientDate
     */
    public static Date getClientDate() {
        return clientDate;
    }

    /**
     * @return the clientZoneId
     */
    public static String getClientZoneId() {
        return clientZoneId;
    }
    
    public static boolean isUpdated(){
        return flag;
    }
}
