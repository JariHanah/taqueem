package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.beans.RenderDateAsYearType;
import com.alhanah.webcalendar.info.MyRequestReader;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.alhanah.webcalendar.info.Datable;
import com.alhanah.webcalendar.info.MyGeoLocation;
import com.alhanah.webcalendar.info.SMTData;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.server.VaadinRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;
import nasiiCalendar.locationBasid.City;

public class BasicCalendarInfo extends Composite<Div> {

    ControllerBox box;
    //ConvertSelector selector;
    Select<BasicCalendar> selector;
    MyRequestReader reader;
    ResultSpan answer;
    BasicCalendar cal;

    CycleGrid mGrid;
    CycleGrid yGrid;
    CalendarGrid calendarGrid;
    CalendarManualSort manSort;

    String location;
    Anchor link;
    public ControllerBox getBox() {
        return box;
    }

    public BasicCalendarInfo(BasicCalendar cal, String location) {
        this.location=location+"?";
        this.cal = cal;
        link=new Anchor();
        reader = new MyRequestReader(VaadinRequest.getCurrent().getParameterMap());
        List<BasicCalendar>list=Application.getSelectedCalendars();
        BasicDate today=cal.getDate(System.currentTimeMillis());
        BasicDate bd = cal.getDate(reader.getSelectedTime());
        box = new ControllerBox(bd, list);
        selector = box.getCalendarToSelect();
        selector.setValue(Application.getFactory().getGregoryCalendar());
        if(list.size()>1)selector.setValue(list.get(1));
        answer = new ResultSpan();
        answer.setDate(bd);
        //   box.add(new H2(getT("select-date-to-convert"))+" "+getT("based-on"));
        box.add(box.getSelectDatePanel());
        box.add(selector);

        box.add(new H4(getT("conversion-result")));

        box.add(answer);
        box.addDatable(answer);
        selector.addValueChangeListener((list2) -> {

            answer.update();
            updateSelection();
        });
        Accordion accord = new Accordion();
        Accordion accordMoon = new Accordion();
        //add(new H4(getT(bd.getCalendar().getName()+BasicCalendar.INFO_NAME)));
        //add(new H4(getT("today-date")));
        getContent().add(new DateHeader(today));
        getContent().add(link);
        getContent().add(accord);
        SMTData local = new SMTData();
        SMTData makkah = new SMTData();
        SMTGrid smtGrid = new SMTGrid(Arrays.asList(local, makkah), box.getDate());

        SunMoonSetTime smt = new SunMoonSetTime();
        //smt.add(box.getDayChangePanel());
        box.addDatable(smt);

        smt.setDate(bd);
        accordMoon.add(new AccordionPanel(new H4(getT("moon-info-on-selected-date")), smt));

        AgeOfMoon age = new AgeOfMoon(City.MAKKA, bd.getCalendar());
        //age.add(box.getDayChangePanel());
        box.addDatable(age);
        accordMoon.add(new AccordionPanel(new H4(getT("moon-age-on-selected-date")), age));
        HilalBearth hil = new HilalBearth();
        //hil.add(box.getDayChangePanel());
        hil.setDate(bd);
        box.addDatable(hil);
        accordMoon.add(new AccordionPanel(new H4(getT("moon-bearth-on-selected-date")), hil));

        VerticalLayout vCalSort = new VerticalLayout();
        List<BasicCalendar>list3=new ArrayList<>(list);
        list3.remove(cal);
        list3.add(0, cal);
        manSort = new CalendarManualSort(list3, bd);

        calendarGrid = new CalendarGrid(bd, 6, (BasicCalendar[]) manSort.getMySortedList().toArray(new BasicCalendar[manSort.getMySortedList().size()]));
        mGrid = new CycleGrid(bd);
        yGrid = new CycleGrid(bd, "", new RenderDateAsYearType(), CycleGrid.Cycle.YEAR);

        Button confirm = new Button(getT("confirm"), (event) -> {
            Notification.show(getT("loading"));
            updateSelectedCalendars();
            Notification.show(getT("finishedLoading"));
        });
        vCalSort.add(confirm, manSort);

        accord.add(new AccordionPanel(new H4(getT("convert-box") + " " + getT("based-on") + " " + getT(bd.getCalendar().getName() + BasicCalendar.INFO_NAME)), box));
        //accord.add(new AccordionPanel(new H4(getT("moon-info")), accordMoon));
        accord.add(new AccordionPanel(new H4(getT("moon-info")), smtGrid));
        accord.add(new AccordionPanel(new H4(getT("selectAndSortCalendars")), vCalSort));
        accord.add(new AccordionPanel(new H4(getT("year-calendar-view")), calendarGrid));
        accord.add(new AccordionPanel(new H4(getT("calendar-view-month-only")),  mGrid));
        accord.add(new AccordionPanel(new H4(getT("calendar-view-year-only")), yGrid));
        accord.setWidthFull();
        accord.close();
        
        getContent().add(accord);
        box.addDatable(calendarGrid);
        box.addDatable(mGrid);

        box.addDatable(yGrid);
        box.addDatable((bd3) -> {
            
            updateSelection();
        });
        new MyGeoLocation(this.getContent()).addValueChangeListener((event) -> {
            if(local.getCity().equals(Application.getUserCity()))return;
            local.setCity(Application.getUserCity());
            smtGrid.setDate(box.getDate());

        });
        updateSelectedCalendars();
        updateSelection();
    }

    public void updateSelectedCalendars() {
        List<BasicCalendar> list = manSort.getMySortedList();
        Application.setSelectedCalendars(list);
        calendarGrid.calendarsUpdated(list);
        mGrid.calendarsUpdated(list);
        yGrid.calendarsUpdated(list);
    }

    private void updateSelection() {
         Application.setSelectedTime(box.getDate().getDate());
        List<BasicCalendar> list = Application.getSelectedCalendars();
        list.remove(box.getDate().getCalendar());
        list.remove(selector.getValue());
        list.add(0, box.getDate().getCalendar());
        list.add(1, selector.getValue());
        //System.err.println("list after: "+list);
        Application.setSelectedCalendars(list);
        link.setHref(location + reader.getLink());
        link.setText(location + reader.getLink());
        UI.getCurrent().getPage().getHistory().pushState(null, link.getHref());

    }

    class ResultSpan extends Span implements Datable {

        @Override
        public void setDate(BasicDate bd) {
            setText(Util.dateToString(bd) + " " + getT("converts-to") + " " + Util.dateToString(selector.getValue().getDate(bd.getDate())));
        }

        public void update() {
            setDate(box.getDate());
        }
    }

}
