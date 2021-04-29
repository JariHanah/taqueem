package com.alhanah.webcalendar.views;

import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.info.MyGeoLocation;
import com.alhanah.webcalendar.view.AgeOfMoon;
import com.alhanah.webcalendar.view.CommentBox;
import com.alhanah.webcalendar.view.DateHeader;
import com.alhanah.webcalendar.view.DayDifference;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.alhanah.webcalendar.views.main.MainView;
import com.alhanah.webcalendar.view.MyFooter;
import com.alhanah.webcalendar.info.MyRequestReader;
import com.alhanah.webcalendar.info.NextSpecialDay;
import com.alhanah.webcalendar.info.NextSpecialMonths;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.server.VaadinRequest;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;
import nasiiCalendar.Months;
import nasiiCalendar.locationBasid.City;

@Route(value = "sami", layout = MainView.class)
@PageTitle("التقويم السامي")
@CssImport("./views/about/about-view.css")
public class SamiView extends Div {

    public SamiView() {
        addClassName("sami-view");
        MyRequestReader reader = new MyRequestReader(VaadinRequest.getCurrent().getParameterMap());
        BasicCalendar samiCal = Application.getFactory().getSamiCalendar();
        long time = System.currentTimeMillis();
        BasicDate bd = samiCal.getDate(time);

        //add(new Div(new H3(getT("converts-to"))));
        //add(new Div(new H3(getT("converts-to"))));
        BasicCalendar bcUmm = Application.getFactory().getCalendar(BasicCalendar.UMM_ALQURA_CALENDAR_V1423);
        DateHeader h3 = new DateHeader(bcUmm.getDate(time));
        add(new Div(
                new H1(getT("today-date")),
                new Paragraph(new DateHeader(bd), h3)
        ));

        add(new Div(
                new H2(getT("special-days")),
                new Paragraph(
                        new DayDifference(getT("next-ramadhan"), bd, new NextSpecialMonths(Months.RAMADHAN)),
                        new DayDifference(getT("next-no-hunt"), bd, new NextSpecialMonths(Months.SAFAR, Months.SAFAR2, Months.RABEI1, Months.RABEI2, Months.ALHARAM)),
                        new DayDifference(getT("next-haram"), bd, new NextSpecialMonths(Months.RAJAB_RABEEIAH, Months.RAJAB_RABEEIAH, Months.ALHARAM)),
                        new DayDifference(getT("next-aashora"), bd, new NextSpecialDay(bd.getCalendar().getDate(1400, Months.RAMADHAN, 10))))));
        
        add(new AgeOfMoon(MyGeoLocation.getCity(), samiCal));
        add(new AgeOfMoon(City.MAKKA, samiCal));
        //add(new Div(new Label(dateToString(bd))));

        add(new CommentBox());

        add(new MyFooter());
    }

}
