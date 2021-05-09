/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.info.Datable;
import com.alhanah.webcalendar.beans.CalendarBean;
import com.alhanah.webcalendar.beans.Render3LineDate;
import com.alhanah.webcalendar.beans.RenderMyDate;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import java.util.ArrayList;
import java.util.List;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;

/**
 *
 * @author hmulh
 */
@CssImport(value = "/views/comps/calendarGridColumns.css", themeFor = "vaadin-grid")
public class CycleGrid extends Grid<BasicDate> implements Datable, CalendarsUpdatedListerner {

    CalendarBean dragged;
    List<BasicCalendar> cals;
    BasicDate base;
 //   BasicCalendar show;
    TextField filterText;
 //   String width="100px";
    RenderMyDate myRender;
    public enum Cycle{DAY, MONTH, YEAR};
    Cycle cycle;
    public CycleGrid(BasicDate b, String filter, RenderMyDate r, Cycle cycle) {
        super(BasicDate.class, false);
        this.cycle=cycle;
        myRender=r;
    //    show = Application.getFactory().getCalendar(BasicCalendar.GREG_ID);
        cals = new ArrayList<>();
        filterText=new TextField();
        filterText.setValue(filter);
        filterText.setWidth("100px");
        //filterText.setWidthFull();
        filterText.setHelperText(getT(b.getCalendar().getName() + BasicCalendar.INFO_SHORT));
        //t.setLabel(getT(b.getName()+BasicCalendar.INFO_SHORT));
        filterText.setPlaceholder(getT("enter-month-name"));
        filterText.addValueChangeListener((e) -> {
            setDate(base);
        });
        setDate(b);
        setColumnReorderingAllowed(true);
        setRowsDraggable(false);
        setSelectionMode(Grid.SelectionMode.NONE);
        setClassName("longcalendar");
        setWidthFull();
        prepareColumns();

    }

    public CycleGrid(BasicDate b) {
        this(b,"", new Render3LineDate(), Cycle.MONTH);
    }
    
    public void setFilterText(String text){
        filterText.setValue(text);
        setDate(base);
    }
    @Override
    public void setDate(BasicDate b) {
        if(base!=null)
        switch(cycle){
            case MONTH: if(b.getMonth()==base.getMonth()&&b.getYear()==base.getYear())return;
            case YEAR:if(b.getYear()==base.getYear())return;
        }
        base = b.getCalendar().getDate(b.getYear(), b.getMonth(), 1);
        List<BasicDate> gridItems = new ArrayList<>();
        String text = filterText.getValue();
        for (int i = 0; i < 100; i++) {
            switch(cycle){
                case DAY:b = b.getCalendar().getDate(b.getYear(), b.getMonth(), b.getDay()+1);break;
                case MONTH:b = b.getCalendar().getDate(b.getYear(), b.getMonth() + 1, 1);break;
                default:b = b.getCalendar().getDate(b.getYear()+1, b.getMonth(), 1);break;
                
            }
            String name = getT(b.getCalendar().getMonthName(b).getName());
            if(cycle==Cycle.YEAR)name=getT(b.getCalendar().getYearType(b).getName());
            if (name.contains(text)) {
                gridItems.add(b);
            }
        }
        setItems(gridItems);

        prepareColumns();
        getDataProvider().refreshAll();
        //setItems(new ArrayList<CalendarBean>(gridItems));

    }

    private void prepareColumns() {
        this.removeAllColumns();
        for (BasicCalendar b : cals) {
            Grid.Column<BasicDate> selectedColumn = addComponentColumn((source) -> {
                BasicDate item = source;
                BasicDate result = b.getDate(item.getDate());
                return myRender.renderDate(result);
                

            }).setWidth("10px").setFlexGrow(1).setAutoWidth(true).setHeader(getT(b.getName() + BasicCalendar.INFO_SHORT));
        }
        if (getColumns().size() != 0) {
            getColumns().get(0).setFrozen(true).setHeader(filterText);
            
        }
    }

    @Override
    public void calendarsUpdated(List<BasicCalendar> list) {
        cals = new ArrayList<>(list);
        prepareColumns();
    }

}
