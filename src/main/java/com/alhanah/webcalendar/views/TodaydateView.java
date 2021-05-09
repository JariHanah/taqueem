package com.alhanah.webcalendar.views;

import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.Application.getT;
import static com.alhanah.webcalendar.HanahI18NProvider.AR;
import com.alhanah.webcalendar.comments.CommentHTML;
import com.alhanah.webcalendar.info.MyGeoLocation;
import com.alhanah.webcalendar.view.MyFooter;
import com.alhanah.webcalendar.view.AgeOfMoon;
import com.alhanah.webcalendar.view.CommentBox;
import com.alhanah.webcalendar.view.ConvertSelector;
import com.alhanah.webcalendar.view.DateHeader;
import com.alhanah.webcalendar.info.MyRequestReader;
import com.alhanah.webcalendar.view.MyMemory;
import com.alhanah.webcalendar.view.Util;
import com.alhanah.webcalendar.views.main.MainView;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinRequest;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;
import nasiiCalendar.locationBasid.City;

@Route(value = "today-date", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("تاريخ اليوم")
@CssImport("./views/todaydate/todaydate-view.css")
public class TodaydateView extends VerticalLayout {

    MyRequestReader reader;

    AgeOfMoon localAge;
    HtmlContainer ageCityText;
    public TodaydateView() {
        add(CommentHTML.getMeta());
        add(Application.getSelectedLocale().equals(AR)?CommentHTML.getArabicCommentImportHead():CommentHTML.getEnglishCommentImportHead());
        addClassName("todaydate-view");
        
        MyMemory m=new MyMemory();
        reader = new MyRequestReader(VaadinRequest.getCurrent().getParameterMap());
        if(reader.showMemory())add(m);
        long time = System.currentTimeMillis();
        //BasicDate bd = reader.getCalendar().getDate(System.currentTimeMillis());
        BasicCalendar bcUmm = Application.getFactory().getCalendar(BasicCalendar.UMM_ALQURA_CALENDAR_V1423);
        BasicCalendar samiCal = Application.getFactory().getCalendar(BasicCalendar.SAMI_FIXED_ID);
        
        BasicDate bd = bcUmm.getDate(time);
        //add(new WeekDay(bd));
        
        add(new Paragraph(
                new DateHeader(samiCal.getDate(time)),
                new DateHeader(bd)
        ));
        
        Accordion accrd = new Accordion();
        accrd.add(new AccordionPanel(new H4(getT("show-other-calendars")), new ConvertSelector(bd)));
        AgeOfMoon makkah=new AgeOfMoon(City.MAKKA, samiCal);
        localAge = new AgeOfMoon(Application.getUserCity(), samiCal);
        ageCityText=new H4(getT("city-not-found"));
        AccordionPanel p=new AccordionPanel(ageCityText, localAge);
        accrd.add(p);
        accrd.add(new AccordionPanel(new H4(getT("age-of-moon")+ " "+getT("based-on")+" "+makkah.getCityName()), makkah));
        new MyGeoLocation(this).addValueChangeListener((event) -> {
            ageCityText.setText(getT("age-of-moon")+ " "+getT("based-on")+" "+Util.getCityName(Application.getUserCity()));
            localAge.setCity(Application.getUserCity());
            
        });
        add(accrd);
        add(new CommentBox());
        add(new MyFooter());
        
        //    add(new Span("<iframe src=\"https://taqueem.net\" height=300 width=300> </iframe>"));
        m.append("stop");
                MainView.instance.setPanel(null);

        UI.getCurrent().addAfterNavigationListener((ane) -> {
            String page=ane.getLocation().getPath();
            
            add(CommentHTML.getCommmentBox(page));
            //anchor.setHref(ane.getLocation().getPath()+"?" + MyParameters.LANG + "=" + Application.getOtherLocale().getLanguage());
        });
        
    }

    
}
