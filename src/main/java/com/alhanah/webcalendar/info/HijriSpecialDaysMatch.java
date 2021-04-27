/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.info;

import static com.alhanah.webcalendar.Application.getT;
import static com.alhanah.webcalendar.view.Util.dateToString;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;
import nasiiCalendar.CalendarFactory;

/**
 *
 * @author hmulh
 */
public class HijriSpecialDaysMatch extends Span implements Datable{
    BasicCalendar cal;
    
    TextField rajab4,ramadhan1, eid2;
    TextField moharram1, eid1;
    public HijriSpecialDaysMatch(BasicCalendar c) {
        cal=c;
        H4 title=new H4(getT("special-hijri-days"));
        rajab4=createTextField(getT("match-rajab4"));
        ramadhan1=createTextField(getT("match-ramadhan1"));
        eid2=createTextField(getT("match-eid2"));
        moharram1=createTextField(getT("match-moharram1"));
        eid1=createTextField(getT("match-eid1"));
        
        add(title);
        add(new Paragraph(rajab4, ramadhan1, eid2));
        add(new Paragraph(moharram1, eid1));
        
         
    }
    private TextField createTextField(String text){
        TextField f=new TextField(text);
        f.setReadOnly(true);
        return f;
    }

    @Override
    public void setDate(BasicDate bd) {
        BasicCalendar convert=CalendarFactory.getGregoryCalendar();
        BasicDate temp=cal.getDate(bd.getYear(), 7, 4);
        rajab4.setLabel(getT(CalendarFactory.getWeekDay(temp))+" "+getT("match-rajab4"));
        rajab4.setValue(dateToString(temp));
        rajab4.setHelperText(dateToString(convert.getDate(temp.getDate())));
        temp=cal.getDate(bd.getYear(), 9, 1);
        ramadhan1.setLabel(getT(CalendarFactory.getWeekDay(temp))+" "+getT("match-ramadhan1"));
        ramadhan1.setValue(dateToString(temp));
        ramadhan1.setHelperText(dateToString(convert.getDate(temp.getDate())));
        temp=cal.getDate(bd.getYear(), 12, 10);
        eid2.setLabel(getT(CalendarFactory.getWeekDay(temp))+" "+getT("match-eid2"));
        eid2.setValue(dateToString(temp));
        eid2.setHelperText(dateToString(convert.getDate(temp.getDate())));
        temp=cal.getDate(bd.getYear(), 1, 1);
        moharram1.setLabel(getT(CalendarFactory.getWeekDay(temp))+" "+getT("match-moharram1"));
        moharram1.setValue(dateToString(temp));
        moharram1.setHelperText(dateToString(convert.getDate(temp.getDate())));
        temp=cal.getDate(bd.getYear(), 10, 1);
        eid1.setLabel(getT(CalendarFactory.getWeekDay(temp))+" "+getT("match-eid1"));
        eid1.setValue(dateToString(temp));
        eid1.setHelperText(dateToString(convert.getDate(temp.getDate())));
        
    }
    
    
}
