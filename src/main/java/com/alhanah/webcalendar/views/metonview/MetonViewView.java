package com.alhanah.webcalendar.views.metonview;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.alhanah.webcalendar.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

@Route(value = "meton-view", layout = MainView.class)
@PageTitle("metonView")
@CssImport("./views/metonview/meton-view-view.css")
public class MetonViewView extends PolymerTemplate {

   
    @Id("header")
    Div header;

    @Id("sidebarLeft")
    Div sidebarLeft;

    @Id("sidebarRight")
    Div sidebarRight;

    @Id("main")
    Div main;

    @Id("footer")
    Div footer;

    public MetonViewView() {
        header.add(new H1("Header text"));
        sidebarLeft.add(new VerticalLayout(new Anchor("", "Link 1"),
                new Anchor("", "Link 2"), new Anchor("", "Link 3"),
                new Anchor("", "Link 4")));
        sidebarRight.add(new VerticalLayout(new Anchor("", "Link 5"),
                new Anchor("", "Link 6")));
        main.add(new Paragraph("Main content goes here."));
        footer.add(new Paragraph("Footer text"));
    }

}
