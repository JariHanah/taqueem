/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.beans;

import com.vaadin.flow.component.Component;
import nasiiCalendar.BasicDate;

/**
 *
 * @author hmulh
 */
public interface  RenderMyDate {
    public Component renderDate(BasicDate bd);

    public String getWidth();
}
