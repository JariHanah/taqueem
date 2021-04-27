/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar;

import com.vaadin.flow.i18n.I18NProvider;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import java.util.Locale;
import static java.util.Locale.ENGLISH;
import java.util.ResourceBundle;

/**
 *
 * @author hmulh
 */
public class HanahI18NProvider  implements I18NProvider {
    
    public static final String RESOURCE_BUNDLE_NAME = "hanah";
    public static final Locale AR = new Locale("ar");
    
    private static final ResourceBundle RESOURCE_BUNDLE_EN = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, ENGLISH);
    private static final ResourceBundle RESOURCE_BUNDLE_AR = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, AR);
    private static final List providedLocales = unmodifiableList(asList(ENGLISH, AR));

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        ResourceBundle resourceBundle = RESOURCE_BUNDLE_EN;
        if (AR.equals(locale)) {
            resourceBundle = RESOURCE_BUNDLE_AR;
        }

        if (!resourceBundle.containsKey(key)) {
            return key + " - " + locale;
        } else {
            return (resourceBundle.containsKey(key)) ? resourceBundle.getString(key) : key;
        }
    }

    @Override
    public List<Locale> getProvidedLocales() {
        return providedLocales;
    }
}
