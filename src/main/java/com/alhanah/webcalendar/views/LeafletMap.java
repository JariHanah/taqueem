package com.alhanah.webcalendar.views;

import com.alhanah.webcalendar.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.Route;
import java.util.Iterator;

@Route(value = "map-view", layout = MainView.class)
//@JsModule("./components/leafletmap/leaflet-map.ts")
@Tag("leaflet-map")
@CssImport("leaflet/dist/leaflet.css")
public class LeafletMap extends Component implements HasSize {

    public void setView(double latitude, double longitude, int zoomLevel) {
        getElement().callJsFunction("setView", latitude, longitude, zoomLevel);
        print(this, 0);
    }
    public static void print(Component comp, int tab){
        StringBuilder s=new StringBuilder();
        for(int i=0;i<tab;i++)s.append("\t");
        Iterator<Component> c=comp.getChildren().iterator();
        while(c.hasNext()){
                Component c1=c.next();
            System.err.println(s.toString()+c1);
            print(c1, tab+1); 
        }
    }

}
