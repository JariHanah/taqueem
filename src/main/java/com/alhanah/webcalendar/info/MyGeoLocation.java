/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.info;

import com.alhanah.webcalendar.Application;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.server.VaadinSession;
import nasiiCalendar.locationBasid.City;
import org.vaadin.elmot.flow.sensors.GeoLocation;

/**
 *
 * @author hmulh
 */
public class MyGeoLocation extends GeoLocation{
    public MyGeoLocation(HtmlContainer c){
        if(c!=null)c.add(this);
        GeoLocation geoLocation=this;
        geoLocation.setWatch(true);
        geoLocation.setHighAccuracy(true);
        geoLocation.setTimeout(100000);
        geoLocation.setMaxAge(200000);
        System.err.println("I think geo is called");
        geoLocation.addValueChangeListener(e -> {
            City city = Application.getCityList().getClosestCity(e.getValue().getLatitude(), e.getValue().getLongitude());
            VaadinSession.getCurrent().setAttribute("city", city);
            System.err.println("MainGeo Updated!..");
        });//*/
        geoLocation.addErrorListener(e -> {
            System.err.println("GeoLocation wnet wrong......." + e.getMessage() + "\n" + e.getCode() + "\n" + e.toString());
        });//*/
    }
    
    

    public static City getCity() {
        City city = (City) VaadinSession.getCurrent().getAttribute("city");
        if (city == null) {
            return City.MAKKA;
        }
        return city;
    }
}
