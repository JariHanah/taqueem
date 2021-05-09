package com.alhanah.webcalendar.views;

import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.view.AgeOfMoon;
import com.alhanah.webcalendar.view.CommentBox;
import com.alhanah.webcalendar.info.MyRequestReader;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.alhanah.webcalendar.views.main.MainView;
import com.alhanah.webcalendar.view.ControllerBox;
import com.alhanah.webcalendar.view.ConvertSelector;
import com.alhanah.webcalendar.info.Datable;
import com.alhanah.webcalendar.view.CalendarGrid;
import com.alhanah.webcalendar.view.DisplayCalendarControlBox;
import com.alhanah.webcalendar.view.HilalBearth;
import com.alhanah.webcalendar.view.MyFooter;
import com.alhanah.webcalendar.view.MyMemory;
import com.alhanah.webcalendar.view.SunMoonSetTime;
import com.alhanah.webcalendar.view.Util;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinRequest;

import nasiiCalendar.BasicDate;

@Route(value = "info-view", layout = MainView.class)
@PageTitle("بيانات")
//@CssImport(value = "/views/comps/textarea.css", themeFor = "vaadin-text-area")
@CssImport("./views/convertview/convert-view.css")

public class InfoView extends VerticalLayout {

    ControllerBox box;
    ConvertSelector selector;
    MyRequestReader reader;
    ResultSpan answer;

    public InfoView() {
        
        setSizeFull();
        // Wrapper to use up extra space
        FlexLayout footerWrapper = new FlexLayout();
        // Actual footer content
        
        
        MyMemory m = new MyMemory();
        reader = new MyRequestReader(VaadinRequest.getCurrent().getParameterMap());
        if (reader.showMemory()) {
            add(m);
        }

        addClassName("info-view");
        BasicDate bd = reader.getCalendar().getDate(reader.getSelectedTime());

        box = new ControllerBox(bd);
        selector = new ConvertSelector(bd);
        SunMoonSetTime smt = new SunMoonSetTime();
        answer = new ResultSpan();
        answer.setDate(bd);
        if (reader.showMemory()) {
            DisplayCalendarControlBox conBox = new DisplayCalendarControlBox();
            box.addDatable(conBox);
            CalendarGrid g = conBox.getGrid(bd);
            conBox.addListener(g);

            addMyDiv(g);

            Accordion a = new Accordion();
            a.add(getT("selectAndSortCalendars"), conBox);
            a.close();
            add(a);
        }
        add(new H2(getT("special-page-for-developer")));

        add(box.getDayChangePanel());
        add(box.getSelectDatePanel());
        add(new H2(getT("choose-calendar-to-convert-to")));
        addMyDiv(selector);
        smt.setDate(bd);
        add(new H2(getT("conversion-result")));
        addMyDiv(answer);
        add(new H2(getT("moon-info-on-selected-date")));
        addMyDiv(smt);
        addMyDiv(new AgeOfMoon());
        HilalBearth hil = new HilalBearth();
        hil.setDate(bd);
        addMyDiv(hil);

        add(new CommentBox());

      //  add(new MyFooter());
        
        Component footer = box.getDayChangePanel();
        //footer.setText("Footer");
        // Align the footer to the end of the wrapper
        footerWrapper.setAlignItems(Alignment.END);
        // Make the footer always last in the parent using FlexBox order
        footerWrapper.getElement().getStyle().set("order", "999");
        footerWrapper.add(footer);
        add(footerWrapper);
        // expand the wrapper to take all remaining unused space
        expand(footerWrapper);
        
                MainView.instance.setPanel(box.getQuickDateChangePanel());

        m.append("done");

    }

    class ResultSpan extends Span implements Datable {

        @Override
        public void setDate(BasicDate bd) {
            setText(Util.dateToString(bd) + " " + getT("converts-to") + " " + Util.dateToString(selector.getResult()));
        }

    }

    private void addMyDiv(Datable d) {
        add((Component) d);
        box.addDatable(d);
    }
}
//@CssImport(value = "./views/comps/textAreaCell.css", themeFor = "vaadin-text-field")

class MyTextField extends TextField {

    public MyTextField(String x) {
        super(x);
    }

}
