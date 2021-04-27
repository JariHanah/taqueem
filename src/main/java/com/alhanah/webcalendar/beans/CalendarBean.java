/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.beans;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;
import nasiiCalendar.BasicCalendar;


public class CalendarBean {
    
    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @return the calendar
     */
    public BasicCalendar getCalendar() {
        return calendar;
    }

    /**
     * @param calendar the calendar to set
     */
    public void setCalendar(BasicCalendar calendar) {
        this.calendar = calendar;
    }

    public static List<CalendarBean> getBeans(List<BasicCalendar> list, List<BasicCalendar>show) {
        List<CalendarBean> result = new ArrayList<CalendarBean>();
        for (BasicCalendar b : list) {
            result.add(new CalendarBean(b, show.contains(b)));
            //System.err.println(b+"\t"+show.contains(b));
        }
        return result;
    }
    private boolean selected;
    @Id
    private BasicCalendar calendar;
    
    public CalendarBean(BasicCalendar calendar, boolean show) {
        this.calendar = calendar;
        selected=show;
    }
    public CalendarBean(){
        
    }

    @Override
    public String toString() {
        return getCalendar().getName()+":"+isSelected();
    }

    
    
}