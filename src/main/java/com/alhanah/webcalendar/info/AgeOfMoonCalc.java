/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.info;

import com.alhanah.webcalendar.CityList;
import com.alhanah.webcalendar.view.CalendarsUpdatedListerner;
import com.alhanah.webcalendar.views.main.MainView;
import com.vaadin.flow.component.UI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;
import nasiiCalendar.CalendarFactory;
import nasiiCalendar.MyBasicCalendar;
import nasiiCalendar.locationBasid.AbstractBlackMoonMonth;
import nasiiCalendar.locationBasid.BlackFajrStandard;
import nasiiCalendar.locationBasid.City;
import nasiiCalendar.locationBasid.GenericLunerCalendar;
import nasiiCalendar.locationBasid.HilalByMinutesStandard;
import nasiiCalendar.locationBasid.UmAlquraStandardV1423;
import org.shredzone.commons.suncalc.MoonPhase;

public class AgeOfMoonCalc implements Datable , CalendarsUpdatedListerner{

    GenericLunerCalendar calHilal30;
    GenericLunerCalendar calHilal_0;
    GenericLunerCalendar calBlack;
    GenericLunerCalendar calBlackFajr;
    
    BasicCalendar displayCalendar;
    BasicDate base;
    City city;
    
    public AgeOfMoonCalc() {
        this(City.MAKKA, CalendarFactory.getGregoryCalendar());
    }

    public AgeOfMoonCalc(City city, BasicCalendar show) {
        this.city=city;
        base=show.getDate(System.currentTimeMillis());
        displayCalendar=show;
        calHilal30 = new GenericLunerCalendar(
                (MyBasicCalendar) CalendarFactory.getInstance().getCalendar(BasicCalendar.OMARI_ID_16),
                new HilalByMinutesStandard(city));
        calHilal_0 = new GenericLunerCalendar(
                (MyBasicCalendar) CalendarFactory.getInstance().getCalendar(BasicCalendar.OMARI_ID_16),
                new HilalByMinutesStandard(0,city));
        calBlack = new GenericLunerCalendar(
                (MyBasicCalendar) CalendarFactory.getInstance().getCalendar(BasicCalendar.OMARI_ID_16),
                new UmAlquraStandardV1423(city));
        calBlackFajr = new GenericLunerCalendar(
                (MyBasicCalendar) CalendarFactory.getInstance().getCalendar(BasicCalendar.OMARI_ID_16),
                new BlackFajrStandard(city));
  
        
    }
    public long getNextHilal0(){
        return calHilal_0.getLunerIdentifier().getNextMonth(base.getDate());
    }
    public long getNextHilal30(){
        return calHilal30.getLunerIdentifier().getNextMonth(base.getDate());
    }
    public long getNextBlack(){
        return calBlack.getLunerIdentifier().getNextMonth(base.getDate());
    }
    public long getNextBlackFajr(){
        return calBlackFajr.getLunerIdentifier().getNextMonth(base.getDate());
    }
    
    @Override
    public void setDate(BasicDate bd) {
        base=bd;
        long hilal = calHilal30.getLunerIdentifier().getNextMonth(bd.getDate());
        long black = calBlack.getLunerIdentifier().getNextMonth(bd.getDate());
        long fajr = calBlackFajr.getLunerIdentifier().getNextMonth(bd.getDate());
        long hilal_0 = calHilal_0.getLunerIdentifier().getNextMonth(bd.getDate());
        BasicCalendar greg=CalendarFactory.getGregoryCalendar();
        
        BasicDate h1=calHilal30.getDate(bd.getDate());
        
        h1=calBlack.getDate(bd.getDate());
        
        h1=calBlackFajr.getDate(bd.getDate());
        
        h1=calHilal_0.getDate(bd.getDate());
          
       h1=calBlackFajr.getDate(bd.getDate());
        h1=calHilal_0.getDate(bd.getDate());
        
        long blackTime = AbstractBlackMoonMonth.getTimeInstant(MoonPhase.compute().on(new Date(CalendarFactory.dayStart(bd.getDate()))).execute().getTime());
        TimeZone client = TimeZone.getTimeZone(ClientTimeZone.getClientZoneId());
        Instant i = Instant.now();
        UI.getCurrent().getPage().retrieveExtendedClientDetails((extendedClientDetails) -> {
            LocalDateTime bt = LocalDateTime.ofInstant(Instant.ofEpochMilli(blackTime), ZoneId.of(ClientTimeZone.getClientZoneId()));

            ZonedDateTime zoned = Instant.ofEpochMilli(blackTime).atZone(ZoneId.of(extendedClientDetails.getTimeZoneId()));
            //nextBlackMoonTime.setValue(Util.getFormet().format(bt));
            //zone.setValue(zoned.getZone().toString());

        });
        //currentDate.setValue(new Date(bd.getDate()).toString());
    }
    
    @Override
    public void calendarsUpdated(List<BasicCalendar> list) {
        if(list.size()>0)displayCalendar=list.get(0);
    }


    public BasicCalendar getHilalCycle() {
        return calHilal30;
    }

    public BasicCalendar getBlackCycle() {
        return calBlack;
    }

    public BasicCalendar getBlackFajrCycle() {
        return calBlackFajr;
    }

    public BasicCalendar getHilal0Cycle() {
        return calHilal_0;
    }

    public City getCity(){
        return city;
    }

    public void setCity(City city) {
        this.city=city;
    }

    

}
