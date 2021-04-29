/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.info;

import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.view.Util;
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
public class SMTData implements Datable {

    /**
     * @return the sunText
     */
    public long getSunText() {
        return sunText;
    }

    /**
     * @param sunText the sunText to set
     */
    public void setSunText(long sunText) {
        this.sunText = sunText;
    }

    /**
     * @return the moonText
     */
    public Long getMoonText() {
        return moonText;
    }

    /**
     * @param moonText the moonText to set
     */
    public void setMoonText(Long moonText) {
        this.moonText = moonText;
    }

    /**
     * @return the sunRiseText
     */
    public long getSunRiseText() {
        return sunRiseText;
    }

    /**
     * @param sunRiseText the sunRiseText to set
     */
    public void setSunRiseText(long sunRiseText) {
        this.sunRiseText = sunRiseText;
    }

    /**
     * @return the moonRiseText
     */
    public Long getMoonRiseText() {
        return moonRiseText;
    }

    /**
     * @param moonRiseText the moonRiseText to set
     */
    public void setMoonRiseText(Long moonRiseText) {
        this.moonRiseText = moonRiseText;
    }

    /**
     * @return the fajrRiseText
     */
    public long getFajrRiseText() {
        return fajrRiseText;
    }

    /**
     * @param fajrRiseText the fajrRiseText to set
     */
    public void setFajrRiseText(long fajrRiseText) {
        this.fajrRiseText = fajrRiseText;
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

    private long sunText;
    private Long moonText;
    private long sunRiseText;
    private Long moonRiseText;
    private long fajrRiseText;
    private long minDiff;
    private String zoneId;
    BlackFajrStandard fajrStandard;
    private BasicDate date;
    private City city;

    public SMTData() {
        this(City.MAKKA);
    }

    public SMTData(City city) {
        this.city = city;
        fajrStandard = new BlackFajrStandard(Application.getUserZoneId());
        date = Application.getFactory().getCurrentDate();
        zoneId=ClientTimeZone.getClientZoneId();
    }

    @Override
    public void setDate(BasicDate bd) {
        MoonTimes moon = MoonTimes.compute().on(new Date(Application.getFactory().dayStart(bd.getDate()))).at(getCity().getLat(), getCity().getLon()).timezone(zoneId).execute();
        SunTimes sun = SunTimes.compute().on(new Date(Application.getFactory().dayStart(bd.getDate()))).at(getCity().getLat(), getCity().getLon()).timezone(zoneId).execute();
        try {
            setMoonText((Long) getTimeInstant(moon.getSet()));
        } catch (NullPointerException e) {
            setMoonText(null);
        }
        setSunText(getTimeInstant(sun.getSet()));
        try {
            setMoonRiseText((Long) getTimeInstant(moon.getRise()));
        } catch (NullPointerException e) {
            setMoonRiseText(null);
        }
        setSunRiseText(getTimeInstant(sun.getRise()));

        Instant fajrInstant = Instant.ofEpochMilli(fajrStandard.getFajrDate(Application.getFactory().dayStart(bd.getDate())).getTime());
        setFajrRiseText(fajrInstant.toEpochMilli());

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
