import { PolymerElement } from '@polymer/polymer/polymer-element';
import { html } from '@polymer/polymer/lib/utils/html-tag';
import * as L from 'leaflet';

const openStreetMapLayer = 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png';
const openStreetMapAttribution = `&copy; <a href='https://www.openstreetmap.org/copyright'>OpenStreetMap</a> contributors`;

export class LeafletMap extends PolymerElement {
  _attachDom(dom) {
    // Do not use a shadow root
    this.appendChild(dom);
  }

  render() {
    return html``;
  }

  ready() {
    super.ready();

    this.map = L.map(this);
    let tileLayer = L.tileLayer(openStreetMapLayer, {
      attribution: openStreetMapAttribution,
      maxZoom: 13,
    });
    tileLayer.addTo(this.map);
  }

  async setView(latitude, longitude, zoomLevel) {
    this.map.setView([latitude, longitude], zoomLevel);
  }
  static get is() {
    return 'leaflet-map';
  }
}
customElements.define(LeafletMap.is, LeafletMap);
