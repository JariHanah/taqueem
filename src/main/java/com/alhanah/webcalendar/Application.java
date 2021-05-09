package com.alhanah.webcalendar;

//import com.vaadin.flow.component.Direction;
import static com.alhanah.webcalendar.HanahI18NProvider.AR;
import com.alhanah.webcalendar.info.Tester;
import com.vaadin.flow.component.Direction;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinSession;
import static java.lang.System.setProperty;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import static java.util.Locale.ENGLISH;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.CalendarFactory;
import nasiiCalendar.locationBasid.City;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.vaadin.artur.helpers.LaunchUtil;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    // private static Locale selectedLocale = HanahI18NProvider.AR;
    static HanahI18NProvider transInstance = new HanahI18NProvider();
    public static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        prepareLog();
        setProperty("vaadin.i18n.provider", HanahI18NProvider.class.getName());
        Tester.test();

        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(Application.class, args));
       
    }
    public static CalendarFactory getFactory(){
        CalendarFactory fac=(CalendarFactory) VaadinSession.getCurrent().getAttribute("factory");
        if(fac==null){
            fac=new CalendarFactory(getUserZoneId());
            VaadinSession.getCurrent().setAttribute("factory", fac);
        }
        return fac;
    }
    public static void setUserZoneId(ZoneId zone){
        VaadinSession.getCurrent().setAttribute("userZoneId", zone);
        getFactory().setZoneId(zone);
        
    }
    public static ZoneId getUserZoneId(){
        ZoneId zone=(ZoneId) VaadinSession.getCurrent().getAttribute("userZoneId");
        if(zone==null){
            zone=ZoneId.systemDefault();
            VaadinSession.getCurrent().setAttribute("userZoneId", zone);
        }
        return zone;
    }
    public static CityList getCityList() {
        CityList list = (CityList) VaadinServlet.getCurrent().getServletConfig().getServletContext().getAttribute("cities"); // add to application context
        if (list == null) {
            list = new CityList();
            VaadinServlet.getCurrent().getServletConfig().getServletContext().setAttribute("cities", list); // add to application context
        }
        return list;
    }
    static final String USER_CITY="userCity";
    public static City getUserCity(){
        City city=(City) VaadinServlet.getCurrent().getServletConfig().getServletContext().getAttribute(USER_CITY); // add to application context
        if(city==null)return City.MAKKA;
        return city;
    }
    public static void setUserCity(City c){
        VaadinServlet.getCurrent().getServletConfig().getServletContext().setAttribute(USER_CITY, c); // add to application context
    }

    private static void prepareLog() {

        Handler handlerObj = new ConsoleHandler();

        handlerObj.setLevel(Level.ALL);

        LOGGER.addHandler(handlerObj);

        LOGGER.setLevel(Level.ALL);

        LOGGER.setUseParentHandlers(false);
    }

    public static Locale getSelectedLocale() {
        return VaadinSession.getCurrent().getLocale();
    }
    
    public static Locale getOtherLocale() {
        if (getSelectedLocale() == HanahI18NProvider.AR) {
            return ENGLISH;
        }
        return HanahI18NProvider.AR;
    }

    public static void swithLocale() {
        Locale selectedLocale = getSelectedLocale();
        if (selectedLocale == HanahI18NProvider.AR) {
            selectedLocale = ENGLISH;

        } else {
            selectedLocale = AR;
        }
        VaadinSession.getCurrent().setLocale(selectedLocale);
    }

    public static void setLocale(Locale l) {
        VaadinSession.getCurrent().setLocale(l);
        if (l.equals(HanahI18NProvider.AR)) {
            UI.getCurrent().setDirection(Direction.RIGHT_TO_LEFT);

        } else {
            UI.getCurrent().setDirection(Direction.LEFT_TO_RIGHT);

        }
    }

    public static String getT(String key) {
        return transInstance.getTranslation(key, getSelectedLocale());

    }
    static String USER_CALS="userCalcs";
    public static void setSelectedCalendars(List<BasicCalendar> list) {
        VaadinSession.getCurrent().setAttribute(USER_CALS, list);
    }
    public static List<BasicCalendar> getSelectedCalendars(){
        List<BasicCalendar>list= (List<BasicCalendar>) VaadinSession.getCurrent().getAttribute(USER_CALS);
        if(list==null){
            return new ArrayList<>(Arrays.asList(Application.getFactory().getCalendar(BasicCalendar.AD_ID),Application.getFactory().getCalendar(BasicCalendar.OMARI_ID_16), Application.getFactory().getCalendar(BasicCalendar.SAMI_FIXED_ID)));
        }
        return new ArrayList<>(list);
    }
    static String USER_CITIES="userCities";
    
    public static void setSelectedCities(List<City> cities) {
        VaadinSession.getCurrent().setAttribute(USER_CITIES, cities);
    }
    public static List<City> getSelectedCities(){
        List<City>list= (List<City>) VaadinSession.getCurrent().getAttribute(USER_CITIES);
        if(list==null){
            return new ArrayList<>();
        }
        return new ArrayList<City>(list);
    }
    static String USER_SELECTED_TIME="selectedTime";
    public static long getSelectedTime() {
        Long l=(Long) VaadinSession.getCurrent().getAttribute(USER_SELECTED_TIME);
        if(l==null)return System.currentTimeMillis();
        return l;
    }
    public static void setSelectedTime(long t) {
        VaadinSession.getCurrent().setAttribute(USER_SELECTED_TIME, t);
        
    }

}
