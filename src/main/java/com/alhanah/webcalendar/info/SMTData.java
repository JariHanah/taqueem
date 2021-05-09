/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.info;

import com.alhanah.webcalendar.Application;
import com.alhanah.webcalendar.view.HilalBearth;
import java.time.Instant;
import java.util.Date;
import nasiiCalendar.BasicCalendar;
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
public class SMTData implements Datable {

    /**
     * @return the sunSet
     */
    public long getSunText() {
        return sunSet;
    }

    /**
     * @param sunText the sunSet to set
     */
    public void setSunSet(long sunText) {
        this.sunSet = sunText;
    }

    /**
     * @return the moonSet
     */
    public Long getMoonSet() {
        return moonSet;
    }

    /**
     * @param moonText the moonSet to set
     */
    public void setMoonSet(Long moonText) {
        this.moonSet = moonText;
    }

    /**
     * @return the sunRise
     */
    public long getSunRise() {
        return sunRise;
    }

    /**
     * @param sunRiseText the sunRise to set
     */
    public void setSunRise(long sunRiseText) {
        this.sunRise = sunRiseText;
    }

    /**
     * @return the moonRise
     */
    public Long getMoonRise() {
        return moonRise;
    }

    /**
     * @param moonRiseText the moonRise to set
     */
    public void setMoonRise(Long moonRiseText) {
        this.moonRise = moonRiseText;
    }

    /**
     * @return the fajrRise
     */
    public long getFajrRise() {
        return fajrRise;
    }

    /**
     * @param fajrRiseText the fajrRise to set
     */
    public void setFajrRise(long fajrRiseText) {
        this.fajrRise = fajrRiseText;
    }

    /**
     * @return the minDiff
     */
    public long getMinDiff() {
        return minDiff;
    }

    /**
     * @param minDiff the minDiff to set
     */
    public void setMinDiff(long minDiff) {
        this.minDiff = minDiff;
    }

    /**
     * @return the zoneId
     */
    public String getZoneId() {
        return zoneId;
    }

    /**
     * @param zoneId the zoneId to set
     */
    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    /**
     * @return the date
     */
    public BasicDate getDate() {
        return date;
    }
    BasicCalendar showCalc;

    public BasicCalendar getShowCalc() {
        return showCalc;
    }

    public void setShowCalc(BasicCalendar showCalc) {
        this.showCalc = showCalc;
        ageCalc.setDisplayCalendar(showCalc);
    }
    AgeOfMoonCalc ageCalc;
    HilalBearth bearthCalc;
    private long sunSet;
    private Long moonSet;
    private long sunRise;
    private Long moonRise;
    private long fajrRise;
    private long minDiff;
    private String zoneId;
    BlackFajrStandard fajrStandard;
    private BasicDate date;
    private City city;

    public SMTData() {
        this(City.MAKKA, Application.getFactory().getCalendar(BasicCalendar.GREG_ID));
    }

    public SMTData(City city, BasicCalendar show) {
        this.city = city;
        this.showCalc=show;
        fajrStandard = new BlackFajrStandard(city,Application.getUserZoneId());
        date = Application.getFactory().getCurrentDate();
        zoneId=ClientTimeZone.getClientZoneId();
        bearthCalc=new HilalBearth(city);
        ageCalc=new AgeOfMoonCalc(city, show);
        
    }
    public AgeOfMoonCalc getAgeMoonCalc(){
        return ageCalc;
    }
    
    public HilalBearth getBearth(){
        return bearthCalc;
    }
    @Override
    public void setDate(BasicDate bd) {
        date=bd;
        ageCalc.setDate(bd);
        bearthCalc.setDate(bd);
        MoonTimes moon = MoonTimes.compute().on(new Date(Application.getFactory().dayStart(bd.getDate()))).at(getCity().getLat(), getCity().getLon()).timezone(zoneId).execute();
        SunTimes sun = SunTimes.compute().on(new Date(Application.getFactory().dayStart(bd.getDate()))).at(getCity().getLat(), getCity().getLon()).timezone(zoneId).execute();
        try {
            setMoonSet((Long) getTimeInstant(moon.getSet()));
        } catch (NullPointerException e) {
            setMoonSet(null);
        }
        setSunSet(getTimeInstant(sun.getSet()));
        try {
            setMoonRise((Long) getTimeInstant(moon.getRise()));
        } catch (NullPointerException e) {
            setMoonRise(null);
        }
        setSunRise(getTimeInstant(sun.getRise()));

        Instant fajrInstant = Instant.ofEpochMilli(fajrStandard.getFajrDate(Application.getFactory().dayStart(bd.getDate())).getTime());
        setFajrRise(fajrInstant.toEpochMilli());
       
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
        bearthCalc.setCity(city);
        fajrStandard.setCity(city);
    }

    public AgeOfMoonCalc getAgeOfMoonCalc() {
        return ageCalc;
    }

}
