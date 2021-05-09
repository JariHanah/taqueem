package com.alhanah.webcalendar.views;

import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.info.MyRequestReader;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.alhanah.webcalendar.views.main.MainView;
import com.alhanah.webcalendar.view.MyMemory;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.server.VaadinRequest;

@Route(value = "privacy", layout = MainView.class)
@PageTitle("الخصوصية")
@CssImport("./views/about/about-view.css")
public class PrivacyPolicy extends Div {

    public PrivacyPolicy() {
        MyMemory m=new MyMemory();
        MyRequestReader reader = new MyRequestReader(VaadinRequest.getCurrent().getParameterMap());
        if(reader.showMemory())add(m);
        addClassName("about-view");
        add(new Html(getT("privacy-policy")));
                MainView.instance.setPanel(null);

    }
}
