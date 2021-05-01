package com.alhanah.webcalendar.views;

import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.view.CommentBox;
import com.alhanah.webcalendar.info.MyRequestReader;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.alhanah.webcalendar.views.main.MainView;
import com.alhanah.webcalendar.view.ControllerBox;
import com.alhanah.webcalendar.info.Datable;
import com.alhanah.webcalendar.info.MyGeoLocation;
import com.alhanah.webcalendar.info.SMTData;
import com.alhanah.webcalendar.view.DisplayCalendarControlBox;
import com.alhanah.webcalendar.view.MyFooter;
import com.alhanah.webcalendar.view.MyMemory;
import com.alhanah.webcalendar.view.SMTGrid;
import com.alhanah.webcalendar.view.SunMoonSetTime;
import com.alhanah.webcalendar.view.Util;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.server.VaadinRequest;
import java.util.ArrayList;
import java.util.Arrays;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;

@Route(value = "convert-date-view", layout = MainView.class)
@PageTitle("تحويل التاريخ")
@CssImport("./views/convertview/convert-view.css")
public class ConvertDateView extends VerticalLayout {

    ControllerBox box;
 //   ConvertSelector selector;
    Select<BasicCalendar>selector;
    MyRequestReader reader;
    ResultSpan answer;
    //SunMoonSetTime smtLocal;
    //HtmlContainer infoLocal;
    public ConvertDateView() {
        MyMemory m=new MyMemory();
        reader = new MyRequestReader(VaadinRequest.getCurrent().getParameterMap());
        if(reader.showMemory())add(m);
        addClassName("convert-date-view");
        BasicDate bd = reader.getCalendar().getDate(reader.getSelectedTime());
        box = new ControllerBox(bd);
        HtmlContainer smtText=new H4();
        smtText.setText(getT("moon-set-local")+" "+Util.dateToString(box.getDate()));
        selector=box.getCalendarToSelect();
        answer=new ResultSpan();
        answer.setDate(bd);
        add(new H2(getT("use-convert-box")));
        
        selector.setLabel(getT("choose-calendar-to-convert-to"));
        add(new Paragraph(box.getCalendarFromPanel(), selector));
        add(new Paragraph(box.getSelectDatePanel()));
        //add(box.getDayChangePanel());
        selector.addValueChangeListener((event) -> {
            answer.setDate(box.getDate());
        });
        SMTData local=new SMTData();
        SMTData makkah=new SMTData(); 
        SMTGrid smtGrid=new SMTGrid(Arrays.asList(local, makkah), box.getDate());
        smtGrid.setDate(box.getDate());
        box.addDatable(smtGrid);
        //SunMoonSetTime smtMakkah=new SunMoonSetTime();
        //smtLocal=new SunMoonSetTime();
        
        
        //smtMakkah.setDate(bd);
        add(new H2(getT("conversion-result")));
        addMyDiv(answer);
        add(box.getDayChangePanel());
        DisplayCalendarControlBox disp=new DisplayCalendarControlBox(new ArrayList<BasicCalendar>());
        box.addDatable(disp);
        box.addDatable((cc) -> {
            smtText.setText(getT("moon-set-local")+" "+Util.dateToString(cc));
           
        });
        Accordion accord=new Accordion();
        accord.add(new AccordionPanel(new H4(getT("show-result-multi-cals")), disp));
        
        accord.add(new AccordionPanel(smtText,smtGrid));
        accord.setSizeFull();
        add(accord);
        
        new MyGeoLocation(answer).addValueChangeListener((event) -> {
            //smtLocal.setCity(MyGeoLocation.getCity());
            local.setCity(MyGeoLocation.getCity());
            //smtLocal.setDate(box.getDate());
            smtGrid.setDate(box.getDate());
            //infoLocal.setText(getT("moon-set-local")+" "+Util.getCityName(smtLocal.getCity()));
           // System.err.println("convertGeo Updated");
        });;
   //     add(new H2(getT("moon-info-on-selected-date")));
     //   addMyDiv(smt);
     //   addMyDiv(new AgeOfMoon());
     //   addMyDiv(new HilalBearth());
        add(new CommentBox());
        
        add(new MyFooter());
        m.append("stop");
    }

    class ResultSpan extends Span implements Datable{

        @Override
        public void setDate(BasicDate bd) {
            setText(Util.dateToString(bd)+" "+getT("converts-to")+" "+Util.dateToString(selector.getValue().getDate(bd.getDate())));
        }
        
    }
    private void addMyDiv(Datable d){
        add((Component)d);
        box.addDatable(d);
    }
}
