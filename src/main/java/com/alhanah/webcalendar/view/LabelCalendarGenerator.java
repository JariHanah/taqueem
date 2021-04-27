/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import static com.alhanah.webcalendar.Application.getT;
import com.vaadin.flow.component.ItemLabelGenerator;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.PeriodType;

/**
 *
 * @author hmulh
 */
public class LabelCalendarGenerator implements ItemLabelGenerator<BasicCalendar> {

    @Override
    public String apply(BasicCalendar item) {
        return getT(item.getName());
    }

}
class LabelMonthGenerator implements ItemLabelGenerator<PeriodType> {

    @Override
    public String apply(PeriodType item) {
        return getT(item.getName());
    }

}