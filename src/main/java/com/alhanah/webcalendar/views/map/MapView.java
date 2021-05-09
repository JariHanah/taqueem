package com.alhanah.webcalendar.views.map;

import com.alhanah.webcalendar.Application;
import com.alhanah.webcalendar.info.MyGeoLocation;
import com.alhanah.webcalendar.views.LeafletMap;
import com.alhanah.webcalendar.views.main.MainView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.vaadin.elmot.flow.sensors.GeoLocation;

@Route(value = "map", layout = MainView.class)
@PageTitle("Map")
@CssImport("./views/map/map-view.css")
public class MapView extends VerticalLayout {

    private LeafletMap map = new LeafletMap();

    public MapView() {
        setSizeFull();
        setPadding(false);
        map.setSizeFull();
        map.setView(55.0, 10.0, 4);
        add(map);
        
        map.addAttachListener(new ComponentEventListener<AttachEvent>(){
            @Override
            public void onComponentEvent(AttachEvent t) {
                System.err.println("attached: "+t.getSource());
                
            }
            
        });
        map.addDetachListener(new ComponentEventListener<DetachEvent>(){
            @Override
            public void onComponentEvent(DetachEvent t) {
                System.err.println("detached: "+t.getSource());
                
            }
            
        });
        
    }
}
