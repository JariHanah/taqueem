package com.alhanah.webcalendar.components.leafletmap;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;

@CssImport("leaflet/dist/leaflet.css")
@JsModule("./components/leafletmap/leaflet-map.js")
@Tag("leaflet-map")
public class LeafletMap extends Component implements HasSize {

    public void setView(double latitude, double longitude, int zoomLevel) {
        getElement().callJsFunction("setView", latitude, longitude, zoomLevel);
    }

}
