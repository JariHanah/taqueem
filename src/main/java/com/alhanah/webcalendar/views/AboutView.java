package com.alhanah.webcalendar.views;

import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.info.MyRequestReader;
import com.alhanah.webcalendar.view.CommentBox;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.alhanah.webcalendar.views.main.MainView;
import com.alhanah.webcalendar.view.MyFooter;
import com.alhanah.webcalendar.view.MyMemory;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinRequest;

@Route(value = "about", layout = MainView.class)
@PageTitle("حول الآلة")
@CssImport("./views/about/about-view.css")
public class AboutView extends Div {

    public AboutView() {
        MyMemory m=new MyMemory();
        MyRequestReader reader = new MyRequestReader(VaadinRequest.getCurrent().getParameterMap());
        if(reader.showMemory())add(m);
        addClassName("about-view");
        add(new Text(getT("version") + " " + getT("VersionN")));
        
        add(new Text(getT("calendars-developed-by")));

        add(new H3(getT("tech-used")));
        add(new H3(new Anchor("https://github.com/batoulapps/adhan-java", getT("used-tech-fajr"))));
        add(new H3(new Anchor("https://github.com/shred/commons-suncalc", getT("used-tech-sunmoontimes"))));
        add(new H3(getT("thankyou")));
        thankyou();
        add(new CommentBox());

        add(new MyFooter());
        m.append("stop");
    }

    private void thankyou() {

        Anchor a = new Anchor("https://www.linkedin.com/in/ameralghamdi", getT("AmerGH"));
        add(new H3(a));
    }
    public static VerticalLayout getIntroHeader(){
        VerticalLayout c=new VerticalLayout();
        c.add(new H5(getT("taqueem-head")));
        c.add(new Paragraph(getT("taqueem-intro")));
        c.add(new Paragraph(getT("version-last-update")));
        
        return c;
        
    }
}
