/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.info;

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

    LunerIdentifier hilalCal;
    LunerIdentifier blackCal;
    LunerIdentifier fajrCal;
    LunerIdentifier calHilal_0;
    
    int margin=5;

    BasicDate base;

    public HilalBearthCalc() {
        this(City.MAKKA);
    }

    public HilalBearthCalc(City city) {
        calHilal_0 = new HilalByMinutesStandard(0, city);
        hilalCal = new HilalByMinutesStandard(city);
        blackCal = new UmAlquraStandardV1423(city);
        fajrCal = new BlackFajrStandard(city);
    }
    public long getNextHilal0(){
        return calHilal_0.getNextMonth(base.getDate()-margin*DAY);
    }
    public long getNextHilal30(){
        return hilalCal.getNextMonth(base.getDate()-margin*DAY);
    }
    public long getNextBlack(){
        return blackCal.getNextMonth(base.getDate()-margin*DAY);
    }
    public long getNextBlackFajr(){
        return fajrCal.getNextMonth(base.getDate()-margin*DAY);
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
        hilalCal.setCity(c);
        blackCal.setCity(c);
        fajrCal.setCity(c);
    }

    public City getCity() {
        return calHilal_0.getCity();
    }

}
