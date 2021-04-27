package com.alhanah.webcalendar.views.yearview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.alhanah.webcalendar.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "year-view", layout = MainView.class)
@PageTitle("yearView")
@CssImport("./views/yearview/year-view-view.css")
public class YearViewView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public YearViewView() {
        addClassName("year-view-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }

}
