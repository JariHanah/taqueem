package com.alhanah.webcalendar.views.main;

import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.Application.getT;
import com.alhanah.webcalendar.info.MyRequestReader;
import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.router.PageTitle;
import com.alhanah.webcalendar.views.ConvertDateView;
import com.alhanah.webcalendar.views.AboutView;
import com.alhanah.webcalendar.view.MyParameters;
import com.alhanah.webcalendar.views.FatimiView;
import com.alhanah.webcalendar.views.InfoView;
import com.alhanah.webcalendar.views.RamadhanView;
import com.alhanah.webcalendar.views.SamiView;
import com.alhanah.webcalendar.views.TodaydateView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouteData;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.VaadinRequest;
import static com.vaadin.flow.server.VaadinRequest.getCurrent;
import java.util.List;

/**
 * The main view is a top-level placeholder for other views.
 */
@PWA(name = "Taqueem.net", shortName = "Taqueem", enableInstallPrompt = true)
@JsModule("./styles/shared-styles.js")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./views/main/main-view.css")
public class MainView extends AppLayout implements PageConfigurator {

    private final Tabs menu;
    private H1 viewTitle;

    public MainView() {
        MyRequestReader reader = new MyRequestReader(VaadinRequest.getCurrent().getParameterMap());
        Application.setLocale(reader.getLocale());
        setPrimarySection(AppLayout.Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        menu = createMenu();
        addToDrawer(createDrawerContent(menu));
        this.setDrawerOpened(true);
        
    }
    
    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        layout.add(viewTitle);
        layout.add(new Avatar());
        return layout;
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.add(new Image("images/logo.png", getT("all-logo-text")));
        logoLayout.add(new H1(getT("taqueem-app-name")));
        layout.add(logoLayout, menu);
        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        final Anchor anchor =new Anchor("/?" + MyParameters.LANG + "=" + Application.getOtherLocale().getLanguage(), getT("change-lang"));
        tabs.add(new Span(anchor));
        UI.getCurrent().addAfterNavigationListener((ane) -> {
            
            anchor.setHref(ane.getLocation().getPath()+"?" + MyParameters.LANG + "=" + Application.getOtherLocale().getLanguage());
        });
        List<RouteData> routes = RouteConfiguration.forSessionScope().getAvailableRoutes();
        
        return tabs;
    }

    private Component[] createMenuItems() {
        return new Tab[]{
            createTab(getT("today-date"), TodaydateView.class),
            createTab(getT("convertDateView"), ConvertDateView.class),
            createTab(getT("ramadhanView"), RamadhanView.class),
            createTab(getT("samiView"), SamiView.class),
            createTab(getT("fatimiView"), FatimiView.class),
            createTab(getT("infoView"), InfoView.class),
          //  createTab(getT("map-view"), MapView.class),
            //    createTab(getT("calendarList"), CalendarListView.class),
            //   createTab(getT("Master-Detail"), MasterDetailView.class), 
            createTab(getT("About_page"), AboutView.class)};
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        try{
            getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        viewTitle.setText(getCurrentPageTitle());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        //System.err.println("Printing Comp: " + component);
        //print(component, 1);
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class)
                .equals(
                        component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addFavIcon("icon", "icons/icon.png", "16x16");
    }
}
