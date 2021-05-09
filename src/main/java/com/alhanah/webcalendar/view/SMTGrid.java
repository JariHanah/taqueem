/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.info.AgeOfMoonCalc;
import com.alhanah.webcalendar.info.Datable;
import com.alhanah.webcalendar.info.SMTData;
import static com.alhanah.webcalendar.view.AgeOfMoon.AGE_OF_HILAL;
import static com.alhanah.webcalendar.view.AgeOfMoon.AGE_OF_HILAL_0;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dnd.GridDragEndEvent;
import com.vaadin.flow.component.grid.dnd.GridDragStartEvent;
import com.vaadin.flow.component.grid.dnd.GridDropEvent;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nasiiCalendar.BasicCalendar;
import static nasiiCalendar.BasicCalendar.MINUTE;
import nasiiCalendar.BasicDate;

/**
 *
 * @author hmulh
 */
public class SMTGrid extends Grid<SMTData> implements Datable, CalendarsUpdatedListerner {

    SMTData dragged;
    List<SMTData> gridItems;
    BasicDate base;
    //String width = "100px";

    public SMTGrid(List<SMTData> list, BasicDate b) {
        super(SMTData.class, false);
        base = b;
        gridItems = new ArrayList<>(list);
        setItems(gridItems);
        setRowsDraggable(true);
        setSelectionMode(Grid.SelectionMode.NONE);
        setClassName("longcalendar");
        /*       Grid.Column<SMTData> sort = addComponentColumn((source) -> {
            Icon edit = new Icon(VaadinIcon.SORT);
            return edit;

        }).setHeader(getT("sort-rows")).setWidth("10px").setFrozen(true).setFlexGrow(0).setAutoWidth(true);
//*/
        Grid.Column<SMTData> selectedColumn = addComponentColumn((source) -> {
            SMTData item = source;
            //System.err.println("item: "+item.getCity()+" "+item.getFajrRise());
            VerticalLayout v = new VerticalLayout();
            v.add(new Icon(VaadinIcon.SORT));
            v.add(new Label(Util.getCityName(item.getCity())));
            Label l = new Label();
            //    l.setWidth(width);
            l.setText(new Date(item.getFajrRise()).toString());
            l.setText(getT("fajrrise-time") + " " + Util.getTimeFormatter().format(LocalDateTime.ofInstant(Instant.ofEpochMilli(item.getFajrRise()), Application.getUserZoneId())));

            Util.makeLTR(l);
            v.add(l);
            return v;
            //return new VerticalLayout(c);

        }).setHeader(getT("city")).setWidth("10px").setFrozen(true).setFlexGrow(0).setAutoWidth(true);

        Grid.Column<SMTData> set = addComponentColumn((source) -> {
            SMTData item = source;
            VerticalLayout v = new VerticalLayout();
            Label l1 = new Label();
            Label l2 = new Label();
            Label l3 = new Label();
            //    l1.setWidth(width);
            //  l2.setWidth(width);
            //l3.setWidth(width);

            try {
                long info = item.getSunText();
                l1.setText(Util.getTimeFormatter().format(LocalDateTime.ofInstant(Instant.ofEpochMilli(info), Application.getUserZoneId())) + " " + getT("sun"));
            } catch (RuntimeException e) {
                l1.setText(getT("sun-not-set"));
            }
            try {
                long info = item.getMoonSet();
                l2.setText(Util.getTimeFormatter().format(LocalDateTime.ofInstant(Instant.ofEpochMilli(info), Application.getUserZoneId())) + " " + getT("moon"));
                int day = (int) ((item.getMoonSet() - item.getSunText()) / MINUTE);
                l3.setText(day + " " + getT("minutes"));
            } catch (RuntimeException e) {
                l2.setText(getT("moon-not-set"));
                l3.setText(getT("moon-not-set"));

            }
            v.add(l1);
            v.add(l2);
            v.add(l3);

            return v;

        }).setHeader(getT("time-moon-sun-set")).setFlexGrow(1).setAutoWidth(true);

        Grid.Column<SMTData> rise = addComponentColumn((source) -> {
            SMTData item = source;
            VerticalLayout v = new VerticalLayout();
            Label l1 = new Label();
            Label l2 = new Label();
            Label l3 = new Label();
            //    l1.setWidth(width);
            //  l2.setWidth(width);
            //l3.setWidth(width);

            try {
                long info = item.getSunRise();
                l1.setText(Util.getTimeFormatter().format(LocalDateTime.ofInstant(Instant.ofEpochMilli(info), Application.getUserZoneId())) + " " + getT("sun"));
            } catch (RuntimeException e) {
                l1.setText(getT("sun-not-rise"));
            }
            try {
                long info = item.getMoonRise();
                l2.setText(Util.getTimeFormatter().format(LocalDateTime.ofInstant(Instant.ofEpochMilli(info), Application.getUserZoneId())) + " " + getT("moon"));
                int day = (int) ((item.getMoonRise() - item.getSunRise()) / MINUTE);
                l3.setText(day + " " + getT("minutes"));
            } catch (RuntimeException e) {
                l2.setText(getT("moon-not-rise"));
                l3.setText(getT("moon-not-rise"));

            }
            v.add(l1);
            v.add(l2);
            v.add(l3);

            return v;

        }).setHeader(getT("time-moon-sun-rise")).setFlexGrow(1).setAutoWidth(true);
        Grid.Column<SMTData> hilalBeath = addComponentColumn((source) -> {
            SMTData item2 = source;
            AgeOfMoonCalc moonCalc = item2.getAgeOfMoonCalc();
            VerticalLayout v = new VerticalLayout();
            Label l1 = new Label();
            Label l2 = new Label();
            Label l3 = new Label();
            Label l4 = new Label();
            BasicDate h1 = null;
            //System.err.println(item2.getDate()+"\n\t"+item2.getAgeMoonCalc().getDate());
            h1 = moonCalc.getHilalCycle().getDate(item2.getDate().getDate());
            l1.setText(getCalc(h1) + " " + getT(AGE_OF_HILAL));

            h1 = moonCalc.getHilal0Cycle().getDate(item2.getDate().getDate());
            l2.setText(getCalc(h1) + " " + getT(AGE_OF_HILAL_0));

            h1 = moonCalc.getBlackCycle().getDate(item2.getDate().getDate());
            l3.setText(getCalc(h1) + " " + getT(AgeOfMoon.AGE_OF_BLACK));

            h1 = moonCalc.getBlackFajrCycle().getDate(item2.getDate().getDate());
            l4.setText(getCalc(h1) + " " + getT(AgeOfMoon.AGE_OF_FAJR));

            v.add(l1);
            v.add(l2);
            v.add(l3);
            v.add(l4);

            return v;

        }).setHeader(getT("moon-bearth")).setFlexGrow(1).setAutoWidth(true);
        this.setColumnReorderingAllowed(true);
        /*  Grid.Column<SMTData> calendarNameColumn = addComponentColumn((source) -> {
            SMTData item = source;
            Label l = new Label();
          //  l.setWidth(width);
            try {
                //l.setText(new Date(item.getMoonSet()).toString());
                l.setText(Util.getTimeFormatter().format(LocalDateTime.ofInstant(Instant.ofEpochMilli(item.getMoonSet()), ZoneId.of(ClientTimeZone.getClientZoneId()))));

            } catch (RuntimeException e) {
                l.setText(getT("moon-not-set"));
            }
            Util.makeLTR(l);
            return l;

        }).setHeader(getT("moonset-time")).setFlexGrow(1).setAutoWidth(true);
        Grid.Column<SMTData> diff = addComponentColumn((source) -> {
            SMTData item = source;
            Label l = new Label();
        //    l.setWidth(width);
            try {
                int day = (int) ((item.getMoonSet() - item.getSunText()) / MINUTE);

                l.setText(day + "");
            } catch (RuntimeException e) {
                l.setText("moon-not-set");
            }
            Util.makeLTR(l);
            return l;

        }).setHeader(getT("minute-difference")).setFlexGrow(1).setAutoWidth(true);

        Grid.Column<SMTData> sunrise = addComponentColumn((source) -> {
            SMTData item = source;
            Label l = new Label();
        //    l.setWidth(width);
            l.setText(Util.getTimeFormatter().format(LocalDateTime.ofInstant(Instant.ofEpochMilli(item.getSunRise()), ZoneId.of(ClientTimeZone.getClientZoneId()))));

            Util.makeLTR(l);
            return l;

        }).setHeader(getT("sunrise-time")).setFlexGrow(1).setAutoWidth(true);
        Grid.Column<SMTData> moonrise = addComponentColumn((source) -> {
            SMTData item = source;
            Label l = new Label();
       //     l.setWidth(width);
            try {
                l.setText(Util.getTimeFormatter().format(LocalDateTime.ofInstant(Instant.ofEpochMilli(item.getMoonRise()), ZoneId.of(ClientTimeZone.getClientZoneId()))));

            } catch (RuntimeException e) {
                l.setText(getT("moon-not-set"));
            }
            Util.makeLTR(l);
            return l;

        }).setHeader(getT("moonrise-time")).setFlexGrow(1).setAutoWidth(true);
        
        Grid.Column<SMTData> fajr = addComponentColumn((source) -> {
            SMTData item = source;
            Label l = new Label();
        //    l.setWidth(width);
            l.setText(new Date(item.getFajrRise()).toString());
            l.setText(Util.getTimeFormatter().format(LocalDateTime.ofInstant(Instant.ofEpochMilli(item.getFajrRise()), Application.getUserZoneId())));

            Util.makeLTR(l);
            return l;

        }).setHeader(getT("fajrrise-time")).setFlexGrow(1).setAutoWidth(true);
//*/
 /*Grid.Column<CalendarBean> calendarNameColumn = addColumn(new ComponentRenderer<Label, CalendarBean>() {
            @Override
            public Label createComponent(CalendarBean item) {
                System.err.println("trying to create label: " + item);
                Label l = new Label();
                
                l.setText(getT(item.getCalendar().getName() + BasicCalendar.INFO_NAME));
                return l;
            }

        }).setHeader(getT("calendar")).setFlexGrow(1);//*/
        //  Binder<CalendarBean> binder = new Binder<>(CalendarBean.class);
        // Editor<CalendarBean> editor = getEditor();
        //  editor.setBinder(binder);
//TextField stateTextField = new TextField();
//Select<String> stateSelect = new Select<>();
//stateSelect.setItems(stateList);
//binder.bind(stateSelect, "state");
// Re-validates the editors every time something changes on the Binder.
// This is needed for the email column to turn into nothing when the
// checkbox is deselected, for example.
        ////////
        // setSelectionMode(SelectionMode.SINGLE);
        setRowsDraggable(true);
        addDragStartListener(e -> {
            GridDragStartEvent<SMTData> event = (GridDragStartEvent<SMTData>) e;

            dragged = event.getDraggedItems().get(0);
            //   System.err.println("dragged: " + event.getDraggedItems());
            setDropMode(GridDropMode.BETWEEN);
        });

        addDragEndListener(new ComponentEventListener<GridDragEndEvent<SMTData>>() {
            public void onComponentEvent(GridDragEndEvent<SMTData> event) {
                dragged = null;
                setDropMode(null);
            }

        });

        addDropListener(e -> {
            GridDropEvent<SMTData> event = (GridDropEvent<SMTData>) e;
            SMTData dropOverItem = event.getDropTargetItem().get();
            if (!dropOverItem.equals(dragged)) {
                gridItems.remove(dragged);
                int dropIndex = gridItems.indexOf(dropOverItem)
                        + (event.getDropLocation() == GridDropLocation.BELOW ? 1
                        : 0);
                gridItems.add(dropIndex, dragged);
                getDataProvider().refreshAll();

            }
        });
        //*/
    }

    public List<SMTData> getMySortedCities() {
        List<SMTData> result = new ArrayList<>();
        gridItems.forEach(e -> {

            result.add(e);

        });
        return result;
    }

    @Override
    public void setDate(BasicDate bd) {
        base = bd;
        gridItems.forEach((t) -> {
            t.setDate(base);
        });
        getDataProvider().refreshAll();
        //setItems(new ArrayList<CalendarBean>(gridItems));

    }

    @Override
    public void calendarsUpdated(List<BasicCalendar> list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String getCalc(BasicDate h1) {
        return h1.getDay() + " " + getT("from-hilal") + " " + h1.getCalendar().getMonthLength(h1);
    }
}
