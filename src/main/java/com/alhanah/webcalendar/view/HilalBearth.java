/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.info.Datable;
import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.info.HilalBearthCalc;
import static com.alhanah.webcalendar.view.Util.dateToString;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import nasiiCalendar.BasicDate;
import nasiiCalendar.CalendarFactory;
import nasiiCalendar.locationBasid.City;

/**
 *
 * @author hmulh
 */
public class HilalBearth extends Span implements Datable {
    HilalBearthCalc calc;
    TextField hilalAgeText;
    TextField textHilalAge_0;
    TextField blackAgeText;
    TextField fajrAgeText;

    

    
    public HilalBearth() {
        this(City.MAKKA);
    }

    public HilalBearth(City city) {
        calc=new HilalBearthCalc(city);
        
        textHilalAge_0 = new TextField(getT("age-of-hilal_0"), "", "what");
        hilalAgeText = new TextField(getT("age-of-hilal"), "", "what");
        blackAgeText = new TextField(getT("age-of-black"), "", "what");
        fajrAgeText = new TextField(getT("age-of-fajr"), "", "what");

        textHilalAge_0.setReadOnly(true);
        hilalAgeText.setReadOnly(true);
        blackAgeText.setReadOnly(true);
        fajrAgeText.setReadOnly(true);
        
        add(new Paragraph(hilalAgeText, textHilalAge_0, blackAgeText, fajrAgeText));

    }

    @Override
    public void setDate(BasicDate bd2) {
        calc.setDate(bd2);
        final BasicDate bd = calc.getDate();
        long hilalStart = calc.getNextHilal30();//hilalCal.getNextMonth(bd.getDate()-5*DAY);
        hilalAgeText.setValue(dateToString(bd.getCalendar().getDate(hilalStart)));
        hilalAgeText.setHelperText(dateToString(CalendarFactory.getGregoryCalendar().getDate(hilalStart)));
        long hilal0Start = calc.getNextHilal0();
        textHilalAge_0.setValue(dateToString(bd.getCalendar().getDate(hilal0Start)));
        textHilalAge_0.setHelperText(dateToString(CalendarFactory.getGregoryCalendar().getDate(hilal0Start)));
        long blackStart = calc.getNextBlack();
        blackAgeText.setValue(dateToString(bd.getCalendar().getDate(blackStart)));
        blackAgeText.setHelperText(dateToString(CalendarFactory.getGregoryCalendar().getDate(blackStart)));
        long fajrStart = calc.getNextBlackFajr();
        fajrAgeText.setValue(dateToString(bd.getCalendar().getDate(fajrStart)));
        fajrAgeText.setHelperText(dateToString(CalendarFactory.getGregoryCalendar().getDate(fajrStart)));
       

    }

    public void setCity(City city) {
        calc.setCity(city);
        setDate(calc.getDate());
    }

    public HilalBearthCalc getCalc() {
        return calc;
    }

}
