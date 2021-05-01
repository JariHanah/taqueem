/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.Application;
import com.alhanah.webcalendar.info.Datable;
import com.alhanah.webcalendar.info.ClientTimeZone;
import static com.alhanah.webcalendar.Application.getT;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import static nasiiCalendar.BasicCalendar.MINUTE;
import nasiiCalendar.BasicDate;
import static nasiiCalendar.locationBasid.AbstractBlackMoonMonth.getTimeInstant;
import nasiiCalendar.locationBasid.BlackFajrStandard;
import nasiiCalendar.locationBasid.City;
import org.shredzone.commons.suncalc.MoonTimes;
import org.shredzone.commons.suncalc.SunTimes;

/**
 *
 * @author hmulh
 */
public class SunMoonSetTime extends com.vaadin.flow.component.orderedlayout.HorizontalLayout implements Datable{

    
    TextField sunText;
    TextField moonText;
    TextField sunRiseText;
    TextField moonRiseText;
    TextField fajrRiseText;
    TextField minDiff;
    BlackFajrStandard fajrStandard;
    BasicDate date;
    private City city;
    public SunMoonSetTime(){
        this(City.MAKKA);
    }
    public SunMoonSetTime(City city) {
        this.city=city;
        fajrStandard=new BlackFajrStandard( Application.getUserZoneId());
        sunText=new TextField(getT("sunset-time"));
        moonText=new TextField(getT("moonset-time"));
        sunRiseText=new TextField(getT("sunrise-time"));
        moonRiseText=new TextField(getT("moonrise-time"));
        fajrRiseText=new TextField(getT("fajrrise-time"));
        minDiff=new TextField(getT("minute-difference"));
        sunText.setReadOnly(true);
        moonText.setReadOnly(true);
        minDiff.setReadOnly(true);
        sunRiseText.setReadOnly(true);
        moonRiseText.setReadOnly(true);
        fajrRiseText.setReadOnly(true);
        add(sunText);
        add(moonText);
        add(minDiff);
        add(sunRiseText);
        add(moonRiseText);
        add(fajrRiseText);
        date=Application.getFactory().getCurrentDate();
        
    }
    
    @Override
    public void setDate(BasicDate bd) {
        UI.getCurrent().getPage().retrieveExtendedClientDetails((extendedClientDetails) -> {
            MoonTimes moon=MoonTimes.compute().on(new Date(Application.getFactory().dayStart(bd.getDate()))).at(city.getLat(), city.getLon()).timezone(extendedClientDetails.getTimeZoneId()).execute();
            SunTimes sun=SunTimes.compute().on(new Date(Application.getFactory().dayStart(bd.getDate()))).at(city.getLat(), city.getLon()).timezone(extendedClientDetails.getTimeZoneId()).execute();
            try{
                moonText.setValue(Util.getTimeFormatter().format(LocalDateTime.ofInstant(Instant.ofEpochMilli(getTimeInstant(moon.getSet())), ZoneId.of(ClientTimeZone.getClientZoneId()))));
            }catch(NullPointerException e){
             //   e.printStackTrace();
                moonText.setValue(getT("moon-not-set"));
            }
            sunText.setValue(Util.getTimeFormatter().format(LocalDateTime.ofInstant(Instant.ofEpochMilli(getTimeInstant(sun.getSet())), ZoneId.of(ClientTimeZone.getClientZoneId()))));
            try{
                moonRiseText.setValue(Util.getTimeFormatter().format(LocalDateTime.ofInstant(Instant.ofEpochMilli(getTimeInstant(moon.getRise())), ZoneId.of(ClientTimeZone.getClientZoneId()))));
            }catch(NullPointerException e){
                moonRiseText.setValue(getT("moon-not-rise"));
            }
            sunRiseText.setValue(Util.getTimeFormatter().format(LocalDateTime.ofInstant(Instant.ofEpochMilli(getTimeInstant(sun.getRise())), ZoneId.of(ClientTimeZone.getClientZoneId()))));
            
            Instant fajrInstant=Instant.ofEpochMilli(fajrStandard.getFajrDate(Application.getFactory().dayStart(bd.getDate())).getTime());
            LocalDateTime fajrLocal=LocalDateTime.ofInstant(fajrInstant, ZoneId.of(ClientTimeZone.getClientZoneId()));
            fajrRiseText.setValue(Util.getTimeFormatter().format(fajrLocal));
            Util.makeLTR(moonText);
            Util.makeLTR(sunText);
            
            try{
                minDiff.setValue(""+((getTimeInstant(moon.getSet())-getTimeInstant(sun.getSet()))/MINUTE) +" "+getT("minutes"));
            }catch(NullPointerException e){
              //  e.printStackTrace();
                minDiff.setValue(getT("moon-not-set"));
            }
        });
        
    }
    /**
     * @return the city
     */
    public City getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(City city) {
        this.city = city;
        
    }
    
}
