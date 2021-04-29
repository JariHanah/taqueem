/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.embeds;

import com.alhanah.webcalendar.view.DateHeader;
import com.vaadin.flow.component.WebComponentExporter;
import com.vaadin.flow.component.webcomponent.WebComponent;

/**
 *
 * @author hmulh
 */
public class DateExporter  extends WebComponentExporter<DateHeader> {
    public DateExporter() {
        super("date-form");
    }

    @Override
    protected void configureInstance(WebComponent<DateHeader> webComponent,DateHeader form) {
        
    }
}
