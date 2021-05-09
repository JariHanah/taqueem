package com.alhanah.webcalendar.views;

import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.beans.Render1LineDate;
import com.alhanah.webcalendar.info.MyGeoLocation;
import com.alhanah.webcalendar.view.CommentBox;
import com.alhanah.webcalendar.view.DayDifference;
import com.alhanah.webcalendar.view.HilalBearth;
import com.alhanah.webcalendar.view.LabelCalendarGenerator;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.alhanah.webcalendar.views.main.MainView;
import com.alhanah.webcalendar.view.MyFooter;
import com.alhanah.webcalendar.info.MyRequestReader;
import com.alhanah.webcalendar.info.NextSpecialMonths;
import com.alhanah.webcalendar.view.CycleGrid;
import com.alhanah.webcalendar.view.MonthStartGrid;
import com.alhanah.webcalendar.view.MyMemory;
import com.alhanah.webcalendar.view.Util;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.server.VaadinRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;
import nasiiCalendar.Months;
import nasiiCalendar.locationBasid.City;

@Route(value = "ramadhan", layout = MainView.class)
@PageTitle("موعد رمضان")
@CssImport("./views/about/about-view.css")
public class RamadhanView extends Div {

    Select<BasicCalendar> calsSelect;
    DayDifference difDay;
    HilalBearth bearth, bearthLocal;
    MyRequestReader reader;
    NextSpecialMonths next;
    MonthStartGrid monthStartGrid;
    CycleGrid monthGrid;
    public RamadhanView() {
        MyMemory m=new MyMemory();
        reader = new MyRequestReader(VaadinRequest.getCurrent().getParameterMap());
        if(reader.showMemory())add(m);
        addClassName("ramadhan-view");
        List<BasicCalendar> calsList = new ArrayList<BasicCalendar>();
        for (BasicCalendar btemp : Application.getFactory().getCalendars()) {
            if (btemp.getYearType(1400).getSubPeriods().contains(Months.RAMADHAN)) {
                calsList.add(btemp);

            }
        }
        bearth = new HilalBearth();

        bearthLocal = new HilalBearth(Application.getUserCity());
        BasicCalendar[] cals = new BasicCalendar[calsList.size()];
        calsSelect = new Select<BasicCalendar>(calsList.toArray(cals));
        calsSelect.setValue(Application.getFactory().getSamiCalendar());
        calsSelect.addValueChangeListener((event) -> {
            if (event.isFromClient()) {
                updateView();
            }
        });
        calsSelect.setItemLabelGenerator(new LabelCalendarGenerator());
        long time = System.currentTimeMillis();
        BasicCalendar bc = Application.getFactory().getSamiCalendar();
        BasicDate bd = bc.getDate(time);
        next = new NextSpecialMonths(Months.RAMADHAN);
        add(new H1(getT("ramadhan-starts")));
        add(new H2(getT("select-calendar-to-show-ramadhan-date")));
        add(calsSelect);
        difDay = new DayDifference(getT("difference"), bd, next);
        add(difDay);
        HtmlContainer h = new H3(getT("start-of-fasting") + " " + getT("based-on") + " " + Util.getCityName(Application.getUserCity()));
        add(h);
        add(bearthLocal);
        add(new H3(getT("start-of-fasting") + " " + getT("based-on") + " " + Util.getCityName(bearth.getCalc().getCity())));
        add(bearth);
        
        List<BasicCalendar>list=calsList.subList(0, 1);
        list.add(Application.getFactory().getAD());
        monthGrid=new CycleGrid(bd, getT(Months.RAMADHAN.getName()), new Render1LineDate(), CycleGrid.Cycle.MONTH);
        monthGrid.calendarsUpdated(list);
        add(monthGrid);
        
        
        bearth.setDate(next.getNext(bd));
        
        bearthLocal.setDate(next.getNext(bd));
        Button test = new Button("Hi");
        
        new MyGeoLocation(bearthLocal).addValueChangeListener((event) -> {
            City c=Application.getUserCity();
            if(!c.equals(bearthLocal.getCity())){
                bearthLocal.setCity(c);
                h.setText(getT("start-of-fasting") + " " + getT("based-on") + " " + Util.getCityName(c));
            }

        });
        
        MainView.instance.setPanel(null);
        add(new CommentBox());
        add(new MyFooter());
        m.append("stop");
    }

    public void updateView() {
        BasicDate bd = calsSelect.getValue().getDate(reader.getSelectedTime());
        difDay.setDate(bd);
        monthStartGrid.calendarsUpdated(Arrays.asList(calsSelect.getValue()));
        monthStartGrid.setDate(bd);
        bearth.setDate(next.getNext(bd));
        bearthLocal.setCity(Application.getUserCity());
        bearthLocal.setDate(next.getNext(bd));
    }

}
