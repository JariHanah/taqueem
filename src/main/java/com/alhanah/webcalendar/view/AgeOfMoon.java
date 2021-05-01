/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.info.Datable;
import com.alhanah.webcalendar.info.MyRequestReader;
import com.alhanah.webcalendar.info.ClientTimeZone;
import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.Application.getT;
import static com.alhanah.webcalendar.HanahI18NProvider.AR;
import com.alhanah.webcalendar.info.AgeOfMoonCalc;
import static com.alhanah.webcalendar.view.Util.dateToString;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;
import static nasiiCalendar.locationBasid.AbstractBlackMoonMonth.getTimeInstant;
import nasiiCalendar.locationBasid.City;
import org.shredzone.commons.suncalc.MoonPhase;

public class AgeOfMoon extends HorizontalLayout implements Datable , CalendarsUpdatedListerner{
    AgeOfMoonCalc moonCalc;
    TextField textHilalAge;
    TextField textHilalZeroAge;
    TextField textBlackAge;
    TextField textBlackFajrAge;
 
    TextField nextBlackMoonTime;
    TextField currentDate;
    TextField zone;
    MyRequestReader reader;
    BasicCalendar displayCalendar;
    
    final String AGE_OF_HILAL_0="age-of-hilal_0";
    final String AGE_OF_HILAL="age-of-hilal";
    final String AGE_OF_BLACK="age-of-black";
    final String AGE_OF_FAJR="age-of-fajr";
    H3 h3;
    public AgeOfMoon() {
        this(City.MAKKA, Application.getFactory().getGregoryCalendar());
    }

    public AgeOfMoon(City city, BasicCalendar show) {
        moonCalc=new AgeOfMoonCalc(city, show);
        displayCalendar=show;
        
        nextBlackMoonTime = new TextField(getT("next-black-moon-time"));
        reader = new MyRequestReader(VaadinRequest.getCurrent().getParameterMap());
        textHilalZeroAge = new TextField(getT("age-of-hilal_0"), "", "what");
        textHilalAge = new TextField(getT("age-of-hilal"), "", "what");
        textBlackAge = new TextField(getT("age-of-black"),"", "what");
        textBlackFajrAge = new TextField(getT("age-of-fajr"), "", "what");
        zone = new TextField(getT("timezone"));
        currentDate=new TextField("current-date");
        h3=new H3();
        
        //add(new Div(h3));
        h3.add(new H3(getT("age-of-moon") + " " + getT("based-on") + " " + ((Application.getSelectedLocale().equals(AR)) ? city.getArabName() : city.getName())));
        currentDate.setReadOnly(true);
        textHilalAge.setReadOnly(true);
        textHilalZeroAge.setReadOnly(true);
        textBlackAge.setReadOnly(true);
        textBlackFajrAge.setReadOnly(true);
   
        zone.setReadOnly(true);
        nextBlackMoonTime.setReadOnly(true);
        Util.makeLTR(nextBlackMoonTime);
        add(new Paragraph(textHilalAge,textHilalZeroAge,textBlackAge, textBlackFajrAge));
        
        //add(nextBlackMoonTime);
        //add(currentDate);

        //add(zone);
        UI.getCurrent().getPage().retrieveExtendedClientDetails((extendedClientDetails) -> {
            setDate(reader.getCalendar().getDate(reader.getSelectedTime()));
        });

    }
    private String getCalc(BasicDate h1){
        return h1.getDay() + " "+ getT("from-hilal")+" "+h1.getCalendar().getMonthLength(h1) ;
    }
    @Override
    public void setDate(BasicDate bd) {
        long hilal = moonCalc.getNextHilal30();//calHilal30.getLunerIdentifier().getNextMonth(bd.getDate());
        long black = moonCalc.getNextBlack();//calBlack.getLunerIdentifier().getNextMonth(bd.getDate());
        long fajr = moonCalc.getNextBlackFajr();//calBlackFajr.getLunerIdentifier().getNextMonth(bd.getDate());
        long hilal_0 = moonCalc.getNextHilal0();//calHilal_0.getLunerIdentifier().getNextMonth(bd.getDate());
        BasicCalendar greg=Application.getFactory().getGregoryCalendar();
        
        BasicDate h1=moonCalc.getHilalCycle().getDate(bd.getDate());
        textHilalAge.setLabel(getCalc(h1)+" "+ getT(AGE_OF_HILAL));
        textHilalAge.setValue(dateToString(displayCalendar.getDate(hilal)));
        textHilalAge.setHelperText(dateToString(greg.getDate(hilal)));
        
        h1=moonCalc.getBlackCycle().getDate(bd.getDate());
        textBlackAge.setLabel(getCalc(h1)+ " "+getT(AGE_OF_BLACK));
        textBlackAge.setValue(dateToString(displayCalendar.getDate(black)));
        textBlackAge.setHelperText(dateToString(greg.getDate(black)));
        
        h1=moonCalc.getBlackFajrCycle().getDate(bd.getDate());
        textBlackFajrAge.setLabel(getCalc(h1)+" "+ getT(AGE_OF_FAJR));
        textBlackFajrAge.setValue(dateToString(displayCalendar.getDate(fajr)));
        textBlackFajrAge.setHelperText(dateToString(greg.getDate(fajr)));
        
        h1=moonCalc.getHilal0Cycle().getDate(bd.getDate());
        textHilalZeroAge.setLabel(getCalc(h1) +" "+getT(AGE_OF_HILAL_0));
        textHilalZeroAge.setValue(dateToString(displayCalendar.getDate(hilal_0)));
        textHilalZeroAge.setHelperText(dateToString(greg.getDate(hilal_0)));
  
        long blackTime = getTimeInstant(MoonPhase.compute().on(new Date(Application.getFactory().dayStart(bd.getDate()))).execute().getTime());//.getTime();
        TimeZone client = TimeZone.getTimeZone(ClientTimeZone.getClientZoneId());
        Instant i = Instant.now();
        UI.getCurrent().getPage().retrieveExtendedClientDetails((extendedClientDetails) -> {
            LocalDateTime bt = LocalDateTime.ofInstant(Instant.ofEpochMilli(blackTime), ZoneId.of(ClientTimeZone.getClientZoneId()));

            ZonedDateTime zoned = Instant.ofEpochMilli(blackTime).atZone(ZoneId.of(extendedClientDetails.getTimeZoneId()));
            nextBlackMoonTime.setValue(Util.getTimeFormatter().format(bt));
            zone.setValue(zoned.getZone().toString());

        });
        currentDate.setValue(new Date(bd.getDate()).toString());
    }
    
    @Override
    public void calendarsUpdated(List<BasicCalendar> list) {
        if(list.size()>0)displayCalendar=list.get(0);
    }

   
    public String getCityName(){
       return Application.getSelectedLocale().equals(AR) ? moonCalc.getCity().getArabName() : moonCalc.getCity().getName();
    }

    public void setCity(City city) {
        moonCalc.setCity(city);
        h3.removeAll();
        h3.setText(getT("age-of-moon") + " " + getT("based-on") + " " + getCityName());
        
    }
}
