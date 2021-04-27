/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.info.MyRequestReader;
import com.alhanah.webcalendar.views.AboutView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author hmulh
 */
public class MyFooter extends Div {

    MyRequestReader reader;
    ZoneId zone;
    TextField localTime;

    public MyFooter() {
        VaadinRequest vaadinRequest = VaadinService.getCurrentRequest();
        reader = new MyRequestReader(vaadinRequest.getParameterMap());
        localTime = new TextField(getT("local-time"));
        localTime.setReadOnly(true);
        localTime.setMaxLength(40);
        add(localTime);
        showTime();
        add(new Div(new Anchor("https://alhanah.com", getT("alhanah-web"))));
        add(new Div(new Text(getT("version") + " " + getT("VersionN"))));
        add(AboutView.getIntroHeader());
        add(new Paragraph(getT("under-construction")+" "+getT("version")+" "+getT("VersionN")+" "+getT("beta-test")+" "+getT("last-version-update")));
        
        Image image=new Image("images/logo.png", getT("app-logo-text"));
        image.setWidth("250px");
        add(image);
        // extra();
    }

    void extra() {
        MyFooter f = this;
        VaadinRequest vaadinRequest = VaadinService.getCurrentRequest();

        f.add(new Div(new Label(vaadinRequest.getCharacterEncoding() + "")));
        f.add(new Div(new Label(vaadinRequest.getAuthType() + " AUTH_TYPE")));
        f.add(new Div(new Label(vaadinRequest.getContentType() + " CONTENT_TYPE")));
        f.add(new Div(new Label(vaadinRequest.getContextPath() + " CONTEXT_TYPE")));
        f.add(new Div(new Label(vaadinRequest.getMethod() + " METHOD")));
        f.add(new Div(new Label(vaadinRequest.getPathInfo() + " PATH_INFO")));
        f.add(new Div(new Label(vaadinRequest.getRemoteAddr() + " REMOTE_ADDR")));
        f.add(new Div(new Label(vaadinRequest.getRemoteHost() + " REMOTE_USER")));
        f.add(new Div(new Label(vaadinRequest.getRemoteUser() + " REMOTE_USER")));
        f.add(new Div(new Label(vaadinRequest.getContentLength() + " CONTENT LENGTH")));
        f.add(new Div(new Label(vaadinRequest.getAttributeNames().toString() + " ATTRIBUTE_NAMES")));
        f.add(new Div(new Label(vaadinRequest.getLocale() + " LOCALE")));
        f.add(new Div(new Label(vaadinRequest.getParameterMap().entrySet() + " PARAMETER_MAP")));
        f.add(new Div(new Label(vaadinRequest.getParameterMap().keySet() + " MAP")));
        f.add(new Div(new Label(vaadinRequest.getParameterMap().values() + " MAP")));
        f.add(new Div(new Label(vaadinRequest.getParameter("wow") + "")));
        f.add(new Div(new Label(vaadinRequest.getParameterMap().get("wow") + "")));

    }

    private void showTime() {
        UI.getCurrent().getPage().retrieveExtendedClientDetails((extendedClientDetails) -> {
            zone=ZoneId.of(extendedClientDetails.getTimeZoneId());
            String value=Util.getTimeFormatter().format(LocalDateTime.now(zone)).toString();
            localTime.setValue(value);
            Util.makeLTR(localTime);
        });
    }
}
