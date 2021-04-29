package com.alhanah.webcalendar.views;

import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.view.AgeOfMoon;
import com.alhanah.webcalendar.view.CalendarGrid;
import com.alhanah.webcalendar.view.CommentBox;
import com.alhanah.webcalendar.info.MyRequestReader;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.alhanah.webcalendar.views.main.MainView;
import com.alhanah.webcalendar.view.ControllerBox;
import com.alhanah.webcalendar.info.Datable;
import com.alhanah.webcalendar.view.DateHeader;
import com.alhanah.webcalendar.view.DisplayCalendarControlBox;
import com.alhanah.webcalendar.info.HijriSpecialDaysMatch;
import com.alhanah.webcalendar.view.HilalBearth;
import com.alhanah.webcalendar.view.MyFooter;
import com.alhanah.webcalendar.view.MyMemory;
import com.alhanah.webcalendar.view.SunMoonSetTime;
import com.alhanah.webcalendar.view.Util;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.server.VaadinRequest;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;
import nasiiCalendar.locationBasid.City;

@Route(value = "fatimi-view", layout = MainView.class)
@PageTitle("التقويم الفاطمي")
@CssImport("./views/convertview/convert-view.css")
public class FatimiView extends VerticalLayout {

    ControllerBox box;
    //ConvertSelector selector;
    Select <BasicCalendar>selector;
    MyRequestReader reader;
    ResultSpan answer;

    public FatimiView() {
        MyMemory m=new MyMemory();
        reader = new MyRequestReader(VaadinRequest.getCurrent().getParameterMap());
        if(reader.showMemory())add(m);
        addClassName("fatimi-view");
        BasicDate bd = Application.getFactory().getCalendar(BasicCalendar.FATIMI_ID).getDate(reader.getSelectedTime());
        box = new ControllerBox(bd);
        selector = box.getCalendarToSelect();
        selector.setValue(Application.getFactory().getGregoryCalendar());
        answer = new ResultSpan();
        answer.setDate(bd);
     //   box.add(new H2(getT("select-date-to-convert"))+" "+getT("based-on"));
        box.add(box.getSelectDatePanel());
        box.add(selector);
        
        box.add(new H4(getT("conversion-result")));
        
        box.add(answer);
        box.addDatable(answer);
        selector.addValueChangeListener((list) -> {
            
            answer.update();
        });
        Accordion accord=new Accordion();
        Accordion accordMoon=new Accordion();
        add(new H4(getT(bd.getCalendar().getName()+BasicCalendar.INFO_NAME)));
        add(new H4(getT("today-date")));
        add(new DateHeader(bd));
        add(accord);
        accord.add(new AccordionPanel(new H4(getT("convert-box")+" "+getT("based-on")+" "+getT(bd.getCalendar().getName()+BasicCalendar.INFO_NAME)), box));
        
        SunMoonSetTime smt = new SunMoonSetTime();
        smt.add(box.getDayChangePanel());
        box.addDatable(smt);
        
        smt.setDate(bd);
        accordMoon.add(new AccordionPanel(new H4(getT("moon-info-on-selected-date")), smt));
        
        AgeOfMoon age=new AgeOfMoon(City.MAKKA, bd.getCalendar());
        age.add(box.getDayChangePanel());
        box.addDatable(age);
        accordMoon.add(new AccordionPanel(new H4(getT("moon-age-on-selected-date")), age));
        HilalBearth hil = new HilalBearth();
        hil.add(box.getDayChangePanel());
        hil.setDate(bd);
        box.addDatable(hil);
        accordMoon.add(new AccordionPanel(new H4(getT("moon-bearth-on-selected-date")), hil));
        accord.add(new AccordionPanel(new H4(getT("moon-info")), accordMoon));
        CalendarGrid g=new CalendarGrid(bd, 6, (BasicCalendar[]) Application.getFactory().getCalendars().toArray(new BasicCalendar[Application.getFactory().getCalendars().size()]));
        DisplayCalendarControlBox calendarList=new DisplayCalendarControlBox();
        calendarList.addListener(g);
        accord.add(new AccordionPanel(new H4(getT("selectAndSortCalendars")), calendarList));
        accord.close();
        add(accord);
        box.addDatable(g);
        HijriSpecialDaysMatch specialDays=new HijriSpecialDaysMatch(bd.getCalendar());
        specialDays.setDate(bd);
        add(specialDays);
        box.addDatable(specialDays);
        add(new CommentBox());

        add(new MyFooter());
m.append("stop");
    }
    

    class ResultSpan extends Span implements Datable {
        
        @Override
        public void setDate(BasicDate bd) {
            setText(Util.dateToString(bd) + " " + getT("converts-to") + " " + Util.dateToString(selector.getValue().getDate(bd.getDate())));
        }
        public void update(){
            setDate(box.getDate());
        }
    }

}
