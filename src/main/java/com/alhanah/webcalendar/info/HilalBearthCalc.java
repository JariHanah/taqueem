/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.info;

import com.alhanah.webcalendar.Application;
import com.vaadin.flow.component.html.Span;
import static nasiiCalendar.BasicCalendar.DAY;
import nasiiCalendar.BasicDate;
import nasiiCalendar.locationBasid.BlackFajrStandard;
import nasiiCalendar.locationBasid.City;
import nasiiCalendar.locationBasid.HilalByMinutesStandard;
import nasiiCalendar.locationBasid.LunerIdentifier;
import nasiiCalendar.locationBasid.UmAlquraStandardV1423;

/**
 *
 * @author hmulh
 */
public class HilalBearthCalc extends Span implements Datable {

    LunerIdentifier calHilal;
    LunerIdentifier calBlack;
    LunerIdentifier calFajr;
    LunerIdentifier calHilal_0;
    
    int margin=5;

    BasicDate base;

    public HilalBearthCalc() {
        this(City.MAKKA);
    }

    public HilalBearthCalc(City city) {
        calHilal_0 = new HilalByMinutesStandard(0, city, Application.getUserZoneId());
        calHilal = new HilalByMinutesStandard(city, Application.getUserZoneId());
        calBlack = new UmAlquraStandardV1423(city, Application.getUserZoneId());
        calFajr = new BlackFajrStandard(city, Application.getUserZoneId());
    }
    public long getNextHilal0(){
        return calHilal_0.getNextMonth(base.getDate()-margin*DAY);
    }
    public long getNextHilal30(){
        return calHilal.getNextMonth(base.getDate()-margin*DAY);
    }
    public long getNextBlack(){
        return calBlack.getNextMonth(base.getDate()-margin*DAY);
    }
    public long getNextBlackFajr(){
        return calFajr.getNextMonth(base.getDate()-margin*DAY);
    }
    @Override
    public void setDate(BasicDate bd2) {
        base = bd2.getCalendar().getDate(bd2.getYear(), bd2.getMonth(), 1);
        
    }

    public BasicDate getDate() {
        return base;
    }
    public void setCity(City c){
        calHilal_0.setCity(c);
        calHilal.setCity(c);
        calBlack.setCity(c);
        calFajr.setCity(c);
    }

    public City getCity() {
        return calHilal_0.getCity();
    }

}
