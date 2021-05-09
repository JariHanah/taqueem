package com.alhanah.webcalendar.views;

import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.info.MyRequestReader;
import com.alhanah.webcalendar.view.CommentBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.alhanah.webcalendar.views.main.MainView;
import com.alhanah.webcalendar.view.MyMemory;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.server.VaadinRequest;

@Route(value = "terms-of-service", layout = MainView.class)
@PageTitle("الخصوصية")
@CssImport("./views/about/about-view.css")
public class ToS extends Div {

    public ToS() {
        MyMemory m=new MyMemory();
        MyRequestReader reader = new MyRequestReader(VaadinRequest.getCurrent().getParameterMap());
        if(reader.showMemory())add(m);
        addClassName("about-view");
        add(new Html(getT("terms-of-service")));
        add(new H6("test FB Comments"));
        add(new CommentBox());
        add(new H6("End  FB Comments"));
        MainView.instance.setPanel(null);
    }
}
