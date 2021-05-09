package com.alhanah.webcalendar.views;

import com.alhanah.webcalendar.Application;
import com.alhanah.webcalendar.view.CommentBox;
import com.alhanah.webcalendar.info.MyRequestReader;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.alhanah.webcalendar.views.main.MainView;
import com.alhanah.webcalendar.view.ControllerBox;
import com.alhanah.webcalendar.info.HijriSpecialDaysMatch;
import com.alhanah.webcalendar.view.BasicCalendarInfo;
import com.alhanah.webcalendar.view.HasBox;
import com.alhanah.webcalendar.view.MyFooter;
import com.alhanah.webcalendar.view.MyMemory;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.server.VaadinRequest;
import nasiiCalendar.BasicCalendar;

@Route(value = "fatimi-view", layout = MainView.class)
@PageTitle("التقويم الفاطمي")
@CssImport("./views/convertview/convert-view.css")
public class FatimiView extends Div implements HasBox {

    Select<BasicCalendar> selector;
    MyRequestReader reader;

    BasicCalendarInfo info;
    public FatimiView() {
        info = new BasicCalendarInfo(Application.getFactory().getCalendar(BasicCalendar.FATIMI_ID), "fatimi-view");
        add(info);
        MyMemory m = new MyMemory();
        reader = new MyRequestReader(VaadinRequest.getCurrent().getParameterMap());
        if (reader.showMemory()) {
            add(m);
        }
        addClassName("fatimi-view");

        HijriSpecialDaysMatch specialDays = new HijriSpecialDaysMatch(info.getBox().getDate().getCalendar());
        specialDays.setDate(info.getBox().getDate());
        add(specialDays);
        info.getBox().addDatable(specialDays);
        add(new CommentBox());
        MainView.instance.setPanel(info.getBox().getQuickDateChangePanel());

        add(new MyFooter());
        m.append("stop");

    }
    @Override
    public ControllerBox getBox() {
        return info.getBox();
    }
}
