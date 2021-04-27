/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.info.Datable;
import static com.alhanah.webcalendar.Application.getT;
import static com.alhanah.webcalendar.view.Util.dateToString;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import java.util.ArrayList;
import java.util.List;
import nasiiCalendar.BasicCalendar;
import static nasiiCalendar.BasicCalendar.DAY;
import nasiiCalendar.BasicDate;
import nasiiCalendar.CalendarFactory;
import nasiiCalendar.PeriodType;

/**
 *
 * @author hmulh
 */
public class ControllerBox extends Span implements Datable {

    BasicDate selectedDate;
    List<Datable> datableList;

    public ControllerBox(BasicDate bd) {

        this.selectedDate = bd;
        datableList = new ArrayList<>();
        //add(getSelectDatePanel(changeSource));
        //    updateView();
        //   notifyListeners();
    }

    private Select<BasicCalendar> getCalendarSelect() {

        Select<BasicCalendar> calSelect = new Select<BasicCalendar>();

        calSelect.setItems(CalendarFactory.getInstance().getCalendars());
        calSelect.setValue(selectedDate.getCalendar());
        calSelect.setLabel(getT("cal-type"));
        calSelect.setItemLabelGenerator(
                new LabelCalendarGenerator());

        return calSelect;

    }

    public Select<BasicCalendar> getCalendarFromPanel() {
        final Select<BasicCalendar> calSelectFrom = getCalendarSelect();
        calSelectFrom.addValueChangeListener(
                (event) -> {
                    if (!event.isFromClient()) {
                        return;
                    }
                    BasicCalendar b = calSelectFrom.getValue();
                    selectedDate = b.getDate(selectedDate.getDate());
                    notifyListeners();
                }
        );
        calSelectFrom.setHelperText(getT("set-calendar-select-from"));
        MySpan s = new MySpan() {
            public void setDate(BasicDate b) {
                calSelectFrom.setValue(b.getCalendar());
            }
        };

        addDatable(s);
        return calSelectFrom;
    }

    public Select<BasicCalendar> getCalendarToSelect() {
        final Select<BasicCalendar> calSelectTo = getCalendarSelect();
        calSelectTo.addValueChangeListener(
                (event) -> {
                    //if(!event.isFromClient())return;
                    BasicCalendar b = calSelectTo.getValue();
                    calSelectTo.setHelperText(dateToString(b.getDate(selectedDate.getDate())));
                    //notifyListeners();
                }
        );
        calSelectTo.setHelperText(dateToString(calSelectTo.getValue().getDate(selectedDate.getDate())));
        calSelectTo.setLabel(getT("choose-calendar-to-convert-to"));
        datableList.add(new Datable() {
            @Override
            public void setDate(BasicDate bd) {
                calSelectTo.setHelperText(dateToString(calSelectTo.getValue().getDate(selectedDate.getDate())));
            }
        });
        return calSelectTo;
    }
    String width = "150px";

    public Component getSelectDatePanel() {
        final Select<Integer> daysSelect;
        Select<PeriodType> monthsSelect;
        NumberField yearsSelect;
        daysSelect = new Select<Integer>();
        daysSelect.setLabel(getT("day"));
        Integer[] i = getInts(selectedDate.getCalendar().getMonthLength(selectedDate));
        //System.err.println("i: "+i.length +i[15]);
        daysSelect.setItems(i);
        daysSelect.setValue(selectedDate.getDay());
        daysSelect.setWidth(width);
        daysSelect.setItemLabelGenerator(new ItemLabelGenerator<Integer>() {

            @Override
            public String apply(Integer item) {
                return item + " " + getT(CalendarFactory.getWeekDay(selectedDate.getCalendar().getDate(selectedDate.getYear(), selectedDate.getMonth(), item)));
            }
        });

        monthsSelect = new Select<PeriodType>();
        monthsSelect.setWidth(width);
        monthsSelect.setLabel(getT("month"));
        monthsSelect.setItemLabelGenerator(
                new LabelMonthGenerator());
        monthsSelect.setItems(selectedDate.getCalendar().getYearType(selectedDate).getSubPeriods());
        monthsSelect.setValue(selectedDate.getCalendar().getMonthName(selectedDate));

        yearsSelect = new NumberField(getT("year"), selectedDate.getYear() + "");
        yearsSelect.setWidth("80px");
        yearsSelect.setValue((double) selectedDate.getYear());
        yearsSelect.setHelperText(getT(selectedDate.getCalendar().getName() + BasicCalendar.INFO_REF));

        yearsSelect.addValueChangeListener(
                (event) -> {
                    if (event.isFromClient()) {
                        int x = (int) yearsSelect.getValue().doubleValue();
                        selectedDate = selectedDate.getCalendar().getDate(x, selectedDate.getMonth(), selectedDate.getDay());
                        notifyListeners();
                    }

                }
        );
        daysSelect.addValueChangeListener(
                (event) -> {
                    if (event.isFromClient()) {
                        selectedDate = selectedDate.getCalendar().getDate(selectedDate.getYear(), selectedDate.getMonth(), daysSelect.getValue());

                        notifyListeners();
                    }
                }
        );

        monthsSelect.addValueChangeListener(
                (event) -> {
                    if (event.isFromClient()) {
                        selectedDate = selectedDate.getCalendar().getDate(selectedDate.getYear(), selectedDate.getCalendar().getYearType(selectedDate).getSubPeriods().indexOf(monthsSelect.getValue()) + 1, selectedDate.getDay());
                        notifyListeners();
                    }
                }
        );//*/

        yearsSelect.setValue(
                (double) selectedDate.getYear()
        );
        /*Button submit = new Button(getT("confirm-date"), (event) -> {
            selectedDate = selectedDate.getCalendar().getDate(yearsSelect.getValue().intValue(), selectedDate.getCalendar().getYearType(selectedDate).getSubPeriods().indexOf(monthsSelect.getValue()) + 1, (int) daysSelect.getValue());
            notifyListeners();
        });//*/
        MySpan s = new MySpan() {
            public void setDate(BasicDate b) {
                yearsSelect.setValue((double) selectedDate.getYear());
                List<PeriodType> months = selectedDate.getCalendar().getYearType(selectedDate).getSubPeriods();
                monthsSelect.setItems(months);
                monthsSelect.setValue(selectedDate.getCalendar().getMonthName(selectedDate));

                Integer[] x = getInts(selectedDate.getCalendar().getMonthLength(selectedDate));
                daysSelect.setItems(x);
                daysSelect.setValue(selectedDate.getDay());

                PeriodType pt = selectedDate.getCalendar().getYearType(selectedDate);
                yearsSelect.setHelperText(getT(pt.getName()));
                monthsSelect.setHelperText(pt.getSubPeriods().size() + " " + getT("months"));
                daysSelect.setHelperText(selectedDate.getCalendar().getMonthLength(selectedDate) + " " + getT("days"));
            }
        };
        
        s.add(
                new H4(getT("select-day-month-year")));
        s.add(
                new Div(yearsSelect, monthsSelect, daysSelect));//, submit));

        addDatable(s);
        s.setDate(selectedDate);
        return s;
    }

    public Component getDayChangePanel() {
        WeekDay weekDayTfext = new WeekDay(selectedDate);
        final TextField weekDayText=new TextField(getT("selected-date"));
        weekDayText.setReadOnly(true);
        MySpan c = new MySpan() {
            public void setDate(BasicDate d) {
                String dateString = CalendarFactory.getWeekDay(d);
                weekDayText.setLabel(getT("selected-date")+" "+getT(dateString));
                weekDayText.setValue(dateToString(d));
                weekDayText.setHelperText(dateToString(CalendarFactory.getGregoryCalendar().getDate(d.getDate())));
            }
        };
        Button nextDay = new Button(getT("next-day"), (event) -> {
            selectedDate = selectedDate.getCalendar().nextDay(selectedDate);

            notifyListeners();
        });
        Button currentDay = new Button(getT("current-day"), (event) -> {
            selectedDate = selectedDate.getCalendar().getDate(System.currentTimeMillis());

            notifyListeners();
        });
        Button previousDay = new Button(getT("previous-day"), (event) -> {
            selectedDate = selectedDate.getCalendar().getDate(selectedDate.getDate() - DAY);
            notifyListeners();
        });
        c.add(previousDay, currentDay, nextDay, weekDayText);
        addDatable(c);
        c.setDate(selectedDate);
        return c;

    }

    class MySpan extends Span implements Datable {

        @Override
        public void setDate(BasicDate bd) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    private void notifyListeners() {

        for (Datable d : datableList) {
            d.setDate(selectedDate);
        }
    }

    public void addDatable(Datable d) {
        datableList.add(d);
    }

    @Override
    public void setDate(BasicDate bd) {
        selectedDate = bd;
        notifyListeners();
    }

    Integer[] getInts(int end) {
        Integer[] x = new Integer[end];

        for (int i = 0; i < x.length; i++) {
            x[i] = i + 1;
        }

        return x;
    }

    public BasicDate getDate() {
        return selectedDate;
    }

}
