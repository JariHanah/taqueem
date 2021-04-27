/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.Application;
import com.alhanah.webcalendar.info.Datable;
import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.beans.CalendarBean;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.grid.dnd.GridDragEndEvent;
import com.vaadin.flow.component.grid.dnd.GridDragStartEvent;
import com.vaadin.flow.component.grid.dnd.GridDropEvent;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.grid.editor.Editor;
import java.util.ArrayList;
import java.util.List;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;

/**
 *
 * @author hmulh
 */
@CssImport(value = "/views/comps/calendarGridColumns.css", themeFor = "vaadin-grid")
public class CalendarManualSort extends Grid<CalendarBean> implements Datable {

    CalendarBean dragged;
    List<CalendarBean> gridItems;
    BasicDate base;

    public CalendarManualSort(List<BasicCalendar> list, BasicDate b) {
        super(CalendarBean.class, false);
        base = b;
        gridItems = CalendarBean.getBeans(Application.getFactory().getCalendars(), list);
        setItems(gridItems);
        setRowsDraggable(true);
        setSelectionMode(SelectionMode.NONE);
        setClassName("longcalendar");
        Grid.Column<CalendarBean> sort = addComponentColumn((source) -> {
            Icon edit = new Icon(VaadinIcon.SORT);
            return edit;

        }).setHeader(getT("sort-rows")).setWidth("10px").setFrozen(true).setFlexGrow(0).setAutoWidth(true);

        Grid.Column<CalendarBean> selectedColumn = addComponentColumn((source) -> {
            CalendarBean item = source;
            Checkbox c = new Checkbox(item.isSelected()); //To change body of generated methods, choose Tools | Templates.

            c.addValueChangeListener((event) -> {
                item.setSelected(event.getValue());
                String message = event.getValue() ? getT("willBeShown") : getT("notShown");
            //    Notification.show(getT(item.getCalendar().getName()) + " " + message);
            });
            return new VerticalLayout(c);

        }).setHeader(getT("visible")).setWidth("10px").setFrozen(true).setFlexGrow(0).setAutoWidth(true);

        Grid.Column<CalendarBean> convertedResult = addComponentColumn((source) -> {
            CalendarBean item = source;
            Label l = new Label();
            l.setWidth("300px");
            l.setText(Util.dateToString(item.getCalendar().getDate(base.getDate())));
            return l;

        }).setHeader(getT("date")).setFlexGrow(1).setAutoWidth(true);
        Grid.Column<CalendarBean> calendarNameColumn = addComponentColumn((source) -> {
            CalendarBean item = source;
            Label l = new Label();
            l.setWidth("300px");
            l.setText(getT(item.getCalendar().getName() + BasicCalendar.INFO_NAME));
            return l;

        }).setHeader(getT("calendar")).setFlexGrow(1).setAutoWidth(true);
        /*Grid.Column<CalendarBean> calendarNameColumn = addColumn(new ComponentRenderer<Label, CalendarBean>() {
            @Override
            public Label createComponent(CalendarBean item) {
                System.err.println("trying to create label: " + item);
                Label l = new Label();
                
                l.setText(getT(item.getCalendar().getName() + BasicCalendar.INFO_NAME));
                return l;
            }

        }).setHeader(getT("calendar")).setFlexGrow(1);//*/

        Binder<CalendarBean> binder = new Binder<>(CalendarBean.class);
        Editor<CalendarBean> editor = getEditor();
        editor.setBinder(binder);

        Checkbox selectCheckbox = new Checkbox();
//Select<String> countrySelect = new Select<>();
//countrySelect.setItems(countryList);
        binder.bind(selectCheckbox, "selected");
//        selectedColumn.setEditorComponent(selectCheckbox);
// Close the editor in case of forward navigation between components
        selectCheckbox.getElement().addEventListener("keydown", event -> {
            //if (UNITED_STATES.equals(countrySelect.getValue())) {
            getEditor().cancel();
            //}
        }).setFilter("event.key === 'Tab' && !event.shiftKey");

//TextField stateTextField = new TextField();
//Select<String> stateSelect = new Select<>();
//stateSelect.setItems(stateList);
//binder.bind(stateSelect, "state");
        addItemDoubleClickListener(e -> {
            ItemDoubleClickEvent<CalendarBean> event = (ItemDoubleClickEvent<CalendarBean>) e;
            getEditor().editItem(event.getItem());
            selectCheckbox.focus();
        });

// Re-validates the editors every time something changes on the Binder.
// This is needed for the email column to turn into nothing when the
// checkbox is deselected, for example.
        binder.addValueChangeListener(event -> {
            getEditor().refresh();
        });

        getEditor().addCloseListener(event -> {
            if (binder.getBean() != null) {
                Notification.show(binder.getBean().getCalendar().getName() + ", "
                        + binder.getBean().isSelected());
            }
        });

        ///////////////////////
        // setSelectionMode(SelectionMode.SINGLE);
        setRowsDraggable(true);
        addDragStartListener(e -> {
            GridDragStartEvent<CalendarBean> event = (GridDragStartEvent<CalendarBean>) e;

            dragged = event.getDraggedItems().get(0);
            //   System.err.println("dragged: " + event.getDraggedItems());
            setDropMode(GridDropMode.BETWEEN);
        });

        addDragEndListener(new ComponentEventListener<GridDragEndEvent<CalendarBean>>() {
            public void onComponentEvent(GridDragEndEvent<CalendarBean> event) {
                dragged = null;
                setDropMode(null);
            }

        });

        addDropListener(e -> {
            GridDropEvent<CalendarBean> event = (GridDropEvent<CalendarBean>) e;
            CalendarBean dropOverItem = event.getDropTargetItem().get();
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

    public List<BasicCalendar> getMySortedList() {
        List<BasicCalendar> result = new ArrayList<>();
        gridItems.forEach(e -> {
            if (e.isSelected()) {
                result.add(e.getCalendar());
            }
        });
        System.err.println("result: "+result);
        return result;
    }

    @Override
    public void setDate(BasicDate bd) {
        base=bd;
        getDataProvider().refreshAll();
        //setItems(new ArrayList<CalendarBean>(gridItems));

    }

}
