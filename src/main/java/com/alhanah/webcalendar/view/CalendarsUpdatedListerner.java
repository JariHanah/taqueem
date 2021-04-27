/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import java.util.List;
import nasiiCalendar.BasicCalendar;

/**
 *
 * @author hmulh
 */
public interface CalendarsUpdatedListerner {
    void calendarsUpdated(List<BasicCalendar>list);
}
