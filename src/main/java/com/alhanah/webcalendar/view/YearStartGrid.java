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
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.list;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;

/**
 *
 * @author hmulh
 */
@Deprecated
@CssImport(value = "/views/comps/calendarGridColumns.css", themeFor = "vaadin-grid")
public class YearStartGrid extends Grid<BasicDate> implements Datable, CalendarsUpdatedListerner {

    CalendarBean dragged;
    //List<BasicDate> gridItems;
    List<BasicCalendar> cals;
    BasicDate base;
    BasicCalendar show;

    public YearStartGrid(BasicDate b) {
        super(BasicDate.class, false);
        show = Application.getFactory().getCalendar(BasicCalendar.GREG_ID);

        cals = new ArrayList<>();
        setDate(b);
        setColumnReorderingAllowed(true);
        setRowsDraggable(false);
        setSelectionMode(Grid.SelectionMode.NONE);
        setClassName("longcalendar");

        //prepareColumns();
    }

    @Override
    public void setDate(BasicDate b) {

        base = b.getCalendar().getDate(b.getYear(), 1, 1);
        List<BasicDate> gridItems = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            b = b.getCalendar().getDate(b.getYear() + 1, 1, 1);
            gridItems.add(b);
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
                TextField text = new TextField();
                text.setReadOnly(true);
                text.setWidthFull();
                if (cals.get(0) == b) {
                    text.setLabel(Util.dateToYear(result));
                    text.setValue(Util.dateToDayMonthOnly(result));
                    text.setHelperText(Util.dateToString(show.getDate(result.getDate())));
                } else {
                    text.setLabel(Util.dateToYear(result));
                    text.setValue(Util.dateToDayMonthOnly(result));
                    text.setHelperText(Util.dateToString(show.getDate(result.getDate())));
                }
                CellFormatter.prepareSpecialPeriods(result, text);
                return text;

            }).setHeader(getT(b.getName() + BasicCalendar.INFO_SHORT)).setFlexGrow(1).setAutoWidth(true);
            
        }
        if(getColumns().size()!=0)getColumns().get(0).setFrozen(true);
    }

    @Override
    public void calendarsUpdated(List<BasicCalendar> list) {
        cals = new ArrayList<>(list);
        prepareColumns();
    }

}
