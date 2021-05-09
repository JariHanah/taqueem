package com.alhanah.webcalendar.views;

import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.view.CommentBox;
import com.alhanah.webcalendar.view.DayDifference;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.alhanah.webcalendar.views.main.MainView;
import com.alhanah.webcalendar.view.MyFooter;
import com.alhanah.webcalendar.info.MyRequestReader;
import com.alhanah.webcalendar.info.NextSpecialDay;
import com.alhanah.webcalendar.info.NextSpecialMonths;
import com.alhanah.webcalendar.view.BasicCalendarInfo;
import com.alhanah.webcalendar.view.ControllerBox;
import com.alhanah.webcalendar.view.HasBox;
import com.alhanah.webcalendar.view.MyMemory;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.server.VaadinRequest;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;
import nasiiCalendar.Months;

@Route(value = "sami", layout = MainView.class)
@PageTitle("التقويم السامي")
@CssImport("./views/about/about-view.css")
public class SamiView extends Div implements HasBox {

    BasicCalendarInfo info;

    public SamiView() {

        MyMemory m = new MyMemory();
        MyRequestReader reader = new MyRequestReader(VaadinRequest.getCurrent().getParameterMap());
        if (reader.showMemory()) {
            add(m);
        }
        //addClassName("sami-view");
        BasicCalendar samiCal = Application.getFactory().getSamiCalendar();
        info = new BasicCalendarInfo(samiCal, "sami");
        long time = System.currentTimeMillis();
        BasicDate bd = samiCal.getDate(time);

        info.getContent().add(new Div(
                new H2(getT("special-days")),
                new Paragraph(
                        new DayDifference(getT("next-ramadhan"), bd, new NextSpecialMonths(Months.RAMADHAN)),
                        new DayDifference(getT("next-no-hunt"), bd, new NextSpecialMonths(Months.SAFAR, Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.ALHARAM)),
                        new DayDifference(getT("next-haram"), bd, new NextSpecialMonths(Months.RAJAB_RABEEIAH, Months.RAJAB_RABEEIAH, Months.ALHARAM)),
                        new DayDifference(getT("next-aashora"), bd, new NextSpecialDay(bd.getCalendar().getDate(1400, Months.RAMADHAN, 10))))));

        info.getContent().add(new CommentBox());

        info.getContent().add(new MyFooter());
        MainView.instance.setPanel(info.getBox().getQuickDateChangePanel());

        add(info);
        m.append("stop");
    }

    @Override
    public ControllerBox getBox() {
        return info.getBox();
    }

}
