/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.info.Datable;
import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.beans.ConvertedDayCell;
import com.alhanah.webcalendar.beans.MyRenderedCalendarCell;
import static com.alhanah.webcalendar.beans.MyRenderedCalendarCell.NORMAL;
import com.alhanah.webcalendar.beans.MyRenderedLayout;
import com.alhanah.webcalendar.beans.WeekBean;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nasiiCalendar.BasicCalendar;
import static nasiiCalendar.BasicCalendar.WEEK;
import nasiiCalendar.BasicDate;
import nasiiCalendar.CalendarFactory;

/**
 *
 * @author hmulh
 */
//@CssImport("/views/comps/mygrid.css")
@CssImport(value = "/views/comps/calendarGridColumns.css", themeFor = "vaadin-grid")
public class CalendarGrid extends Grid implements Datable, CalendarsUpdatedListerner {

    BasicDate base;
    DataProvider<WeekBean, Void> provider;
    List<BasicCalendar> cals;

    public CalendarGrid() {
        this(CalendarFactory.getSamiCalendar().getDate(System.currentTimeMillis()), 6,
                CalendarFactory.getSamiCalendar(), CalendarFactory.getGregoryCalendar(),
                CalendarFactory.getInstance().getCalendar(BasicCalendar.UMM_ALQURA_CALENDAR_V1423));
    }

    public CalendarGrid(BasicDate d, int pageSize, BasicCalendar... list) {
        super(WeekBean.class, false);
        // setClassName(className);
        //base = d;
        setPageSize(pageSize);
        setClassName("gridcalendar");
        String width = "50px";
        cals = Arrays.asList(list);
        //setItems(WeekBean.getWeeks(System.currentTimeMillis(), 52));
        //    addColumn(new ConvertedDayCell(0, list)).setHeader(getT("calendars")).setWidth("150px").setFrozen(true);
        /*       addColumn(new ConvertedDayCell(1, list)).setHeader(getT("Sunday"+BasicCalendar.INFO_SHORT)).setWidth(width).setFlexGrow(1).setFrozen(true);
        addColumn(new ConvertedDayCell(2, list)).setHeader(getT("Monday"+BasicCalendar.INFO_SHORT)).setWidth(width).setFlexGrow(1);//.setAutoWidth(true);
        addColumn(new ConvertedDayCell(3, list)).setHeader(getT("Tuesday"+BasicCalendar.INFO_SHORT)).setWidth(width).setFlexGrow(1);//.setAutoWidth(true);
        addColumn(new ConvertedDayCell(4, list)).setHeader(getT("Wednesday"+BasicCalendar.INFO_SHORT)).setWidth(width).setFlexGrow(1);//.setAutoWidth(true);
        addColumn(new ConvertedDayCell(5, list)).setHeader(getT("Thursday"+BasicCalendar.INFO_SHORT)).setWidth(width).setFlexGrow(1);//.setAutoWidth(true);
        addColumn(new ConvertedDayCell(6, list)).setHeader(getT("Friday"+BasicCalendar.INFO_SHORT)).setWidth(width).setFlexGrow(1);//.setAutoWidth(true);
        addColumn(new ConvertedDayCell(7, list)).setHeader(getT("Saturday"+BasicCalendar.INFO_SHORT)).setWidth(width).setFlexGrow(1);//.setAutoWidth(true);
         */
        List columns = getColumns();

        for (int i = 0; i < columns.size(); i++) {
            Grid.Column<WeekBean> c = (Grid.Column<WeekBean>) columns.get(i);

            c.setClassNameGenerator((t) -> {

                ConvertedDayCell r = (ConvertedDayCell) c.getRenderer();
                int w = r.getWeekDay();
                long time = t.getDay(w);
                return CellFormatter.prepareSeasons(time, CalendarFactory.getDefaultSeasonIdentifier());
                //return "gridcalendar"; //To change body of generated lambdas, choose Tools | Templates.
            });//*/

        }
        Column x=addComponentColumn((source) -> {
            WeekBean w = (WeekBean) source;
            return getCell(w.getDay(1), MyRenderedLayout.HEAD_ROW,cals.stream().toArray(BasicCalendar[]::new));// (BasicCalendar[]) cals.toArray());
        }).setFlexGrow(1).setHeader(getGridHeader(getT("Sunday" + BasicCalendar.INFO_SHORT))).setWidth(width);
        x.setClassNameGenerator((t) -> {
            return "gridcalendar"; //To change body of generated lambdas, choose Tools | Templates.
        });
        x.setFrozen(true);
        addComponentColumn((source) -> {
            WeekBean w = (WeekBean) source;
            return getCell(w.getDay(2), MyRenderedLayout.NORMAL, cals.stream().toArray(BasicCalendar[]::new));
        }).setFlexGrow(1).setHeader(getGridHeader(getT("Monday" + BasicCalendar.INFO_SHORT))).setClassNameGenerator((t) -> {
            return "gridcalendar"; 
        }).setWidth(width);
        addComponentColumn((source) -> {
            WeekBean w = (WeekBean) source;
            return getCell(w.getDay(3), MyRenderedLayout.NORMAL, cals.stream().toArray(BasicCalendar[]::new));
        }).setFlexGrow(1).setHeader(getGridHeader(getT("Tuesday" + BasicCalendar.INFO_SHORT))).setClassNameGenerator((t) -> {
            return "gridcalendar"; 
        }).setWidth(width);
        addComponentColumn((source) -> {
            WeekBean w = (WeekBean) source;
            return getCell(w.getDay(4), MyRenderedLayout.NORMAL, cals.stream().toArray(BasicCalendar[]::new));
        }).setFlexGrow(1).setHeader(getGridHeader(getT("Wednesday" + BasicCalendar.INFO_SHORT))).setClassNameGenerator((t) -> {
            return "gridcalendar"; 
        }).setWidth(width);
        addComponentColumn((source) -> {
            WeekBean w = (WeekBean) source;
            return getCell(w.getDay(5), MyRenderedLayout.NORMAL, cals.stream().toArray(BasicCalendar[]::new));
        }).setFlexGrow(1).setHeader(getGridHeader(getT("Thursday" + BasicCalendar.INFO_SHORT))).setClassNameGenerator((t) -> {
            return "gridcalendar"; 
        }).setWidth(width);
        addComponentColumn((source) -> {
            WeekBean w = (WeekBean) source;
            return getCell(w.getDay(6), MyRenderedLayout.NORMAL, cals.stream().toArray(BasicCalendar[]::new));
        }).setFlexGrow(1).setHeader(getGridHeader(getT("Friday" + BasicCalendar.INFO_SHORT))).setClassNameGenerator((t) -> {
            return "gridcalendar"; 
        }).setWidth(width);
        addComponentColumn((source) -> {
            WeekBean w = (WeekBean) source;
            return getCell(w.getDay(7), MyRenderedLayout.NORMAL, cals.stream().toArray(BasicCalendar[]::new));
        }).setFlexGrow(1).setHeader(getGridHeader(getT("Saturday" + BasicCalendar.INFO_SHORT))).setClassNameGenerator((t) -> {
            return "gridcalendar"; 
        }).setWidth(width);

        /*     addColumn(new ConvertedDayCell(1, list)).setHeader(getT("Sunday"+BasicCalendar.INFO_SHORT)).setWidth(width).setFlexGrow(1).setFrozen(true);
        addColumn(new ConvertedDayCell(2, list)).setHeader(getT("Monday"+BasicCalendar.INFO_SHORT)).setWidth(width).setFlexGrow(1);//.setAutoWidth(true);
        addColumn(new ConvertedDayCell(3, list)).setHeader(getT("Tuesday"+BasicCalendar.INFO_SHORT)).setWidth(width).setFlexGrow(1);//.setAutoWidth(true);
        addColumn(new ConvertedDayCell(4, list)).setHeader(getT("Wednesday"+BasicCalendar.INFO_SHORT)).setWidth(width).setFlexGrow(1);//.setAutoWidth(true);
        addColumn(new ConvertedDayCell(5, list)).setHeader(getT("Thursday"+BasicCalendar.INFO_SHORT)).setWidth(width).setFlexGrow(1);//.setAutoWidth(true);
        addColumn(new ConvertedDayCell(6, list)).setHeader(getT("Friday"+BasicCalendar.INFO_SHORT)).setWidth(width).setFlexGrow(1);//.setAutoWidth(true);
        addColumn(new ConvertedDayCell(7, list)).setHeader(getT("Saturday"+BasicCalendar.INFO_SHORT)).setWidth(width).setFlexGrow(1);//.setAutoWidth(true);
        //*/
        setDate(d);
    }
    
    public Component getGridHeader(String x){
        TextField l=new TextField();
        l.setReadOnly(true);
        l.setClassName("gridcalendar");
        l.setValue(x);
        l.setWidthFull();
        l.setWidth("100px");
       // l.setMinWidth("0px");
        l.getElement().getStyle().set("padding", "0px");
        
        return l;
    }
    
    @Override
    public void setDate(BasicDate bd) {
        base = bd;
        provider = DataProvider.fromCallbacks(query -> {
            // The index of the first item to load
            int offset = query.getOffset();

            int limit = query.getLimit();

            List<WeekBean> weeks = WeekBean.getWeeks(base.getDate() + WEEK * offset, limit);
            System.err.println("Loading weeks count: " + weeks.size() + " starting: " + offset);

            return weeks.stream();
        },//;
                // Second callback fetches the total number of items currently in the Grid.
                // The grid can then use it to properly adjust the scrollbars.
                query -> 200);
        //    );
        setDataProvider(provider);
    }

    @Override
    public void calendarsUpdated(List<BasicCalendar> list) {
        List<Grid.Column<WeekBean>> c = getColumns();
        cals=new ArrayList<>(list);
        
        //c.forEach(e -> ((MyRenderedCalendarCell) e.getRenderer()).calendarsUpdated(list));
        setDate(base);
    }
    private VerticalLayout getCell(long t, int type, BasicCalendar... list){
        return new MyRenderedCalendarCell(t, type, list);
    }
    private VerticalLayout getCell(long t){
        return getCell(t, NORMAL, CalendarFactory.getSamiCalendar(), CalendarFactory.getAdCalendar(), CalendarFactory.getInstance().getCalendar(BasicCalendar.UMM_ALQURA_CALENDAR_V1423));
    }
    
}
