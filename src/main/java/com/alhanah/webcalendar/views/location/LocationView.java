package com.alhanah.webcalendar.views.location;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.alhanah.webcalendar.components.leafletmap.LeafletMap;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.alhanah.webcalendar.views.main.MainView;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "set-location", layout = MainView.class)
@PageTitle("location")
@CssImport("./views/location/location-view.css")
public class LocationView extends VerticalLayout {

    private LeafletMap map = new LeafletMap();

    public LocationView() {
        setSizeFull();
        setPadding(false);
        map.setSizeFull();
        map.setView(55.0, 10.0, 4);
        add(map);
        
    }
}
