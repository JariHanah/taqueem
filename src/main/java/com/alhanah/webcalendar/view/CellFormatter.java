/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.Application.getT;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.textfield.TextField;
import java.util.Date;
import nasiiCalendar.BasicCalendar;
import nasiiCalendar.BasicDate;
import nasiiCalendar.JalaliCalendarIR;
import nasiiCalendar.LeapMonth;
import nasiiCalendar.PeriodType;
import nasiiCalendar.Months;
import nasiiCalendar.SamiCalendar;
import nasiiCalendar.SamiFixed;
import nasiiCalendar.SeasonIdentifier;
import nasiiCalendar.Solar128Calendar;

/**
 *
 * @author Hussam
 */
public class CellFormatter {

    public static final String DAY_UIID = "MyCalendarDay";
    public static final String DATE_VIEW_UIID = "MyDateViewCell";
    private static final String HIJRI = getT(BasicCalendar.OMARI_ID_16 + BasicCalendar.INFO_CLASS);
    private static final String SAMI = getT(BasicCalendar.SAMI_FIXED_ID + BasicCalendar.INFO_CLASS);

    public static void prepareSpecialPeriods(BasicDate b, HasStyle l) {
        l.getStyle().set("color", "black");
        //l.getStyle().set("--lumo-secondary-text-color", "black");
        String type = getT(b.getCalendar().getName() + BasicCalendar.INFO_CLASS);
        PeriodType pt = b.getCalendar().getMonthName(b);
        Months p = null;
        if (pt instanceof LeapMonth) {
            p = ((LeapMonth) pt).getMonth();
        } else {
            p = (Months) pt;
        }
        switch (p) {
            case ALHARAM:
            case RAJAB_MODHAR:
            case RAJAB_RABEEIAH:
                l.setClassName("month-haram");
                l.getStyle().set("background", "red");
                l.getStyle().set("color", "white");
                l.getStyle().set("--lumo-secondary-text-color", "white");
                break;
            case RAMADHAN:
                l.setClassName("month-ramadhan");
                l.getStyle().set("background", "pink");
                l.getStyle().set("color", "black");
                l.getStyle().set("--lumo-secondary-text-color", "black");
                break;
            case RABEI1:
            case RABEI2:
            case SAFAR:
            case SAFAR2:
                if (type.equals(SAMI)) {
                    l.addClassName("month-no-hunting");
                    l.getStyle().set("background", "cyan");
                    l.getStyle().set("color", "black");
                    l.getStyle().set("--lumo-secondary-text-color", "black");
                }
                break;
            //l.getElement().getStyle().set("background", "red");break;
            case SHAWWAL:
            case THO_QIDAH:
            case THO_HIJA:
                if (type.equals(HIJRI)) {
                    l.setClassName("month-hajj-season");
                    l.getStyle().set("background", "brown");
                    l.getStyle().set("color", "white");
                    l.getStyle().set("--lumo-secondary-text-color", "white");
                }
                break;
            case RAJAB:
            case MUHARRAM:
                if (type.equals(HIJRI)) {
                    l.setClassName("month-haram");
                    l.getStyle().set("background", "red");
                    l.getStyle().set("color", "white");
                    l.getStyle().set("--lumo-secondary-text-color", "white");
                }
                break;

        }

        if ((p.equals(Months.SHAWWAL) && b.getDay() == 1) || (p.equals(Months.THO_HIJA) && b.getDay() == 10)) {
            l.setClassName("day-eid");
            l.getStyle().set("background", "green");
            l.getStyle().set("color", "black");
            l.getStyle().set("--lumo-secondary-text-color", "black");
        
        }
        if ((p.equals(Months.THO_HIJA) && b.getDay() == 9)) {
            l.setClassName("day-arafah");
            l.getStyle().set("background", "black");
            l.getStyle().set("color", "white");
            l.getStyle().set("--lumo-secondary-text-color", "white");
        
        }
        if ((p.equals(Months.MUHARRAM) || p.equals(Months.TISHREI)) && b.getDay()== 10) {
            l.setClassName("day-aashora");
            l.getStyle().set("background", "black");
            l.getStyle().set("color", "white");
            l.getStyle().set("--lumo-secondary-text-color", "white");
        
        }
        if (type.equals(SAMI)
                && p.equals(Months.RAMADHAN)
                && b.getDate() == 10) {
            System.err.println("\n\n\n\nOK "+b);
            l.setClassName("day-aashora");
            l.getStyle().set("background", "black");
            l.getStyle().set("color", "white");
            l.getStyle().set("--lumo-secondary-text-color", "white");
        
        }
        if (Application.getFactory().dayStart(b.getDate()) == Application.getFactory().dayStart(System.currentTimeMillis())) {
            l.setClassName("day-today");
            l.getStyle().set("background", "yellow");
            l.getStyle().set("color", "black");
            l.getStyle().set("--lumo-secondary-text-color", "black");
        }
    }

    public static String getType(BasicDate b) {
        return b.getCalendar().getName() + BasicCalendar.INFO_CLASS;
    }

    public static void prepareYearType(BasicDate result, HasStyle text) {
        PeriodType y=result.getCalendar().getYearType(result);
        
        if(y.isSmallLeap()){
            text.getStyle().set("background", "yellow");
            text.getStyle().set("color", "black");
            text.getStyle().set("--lumo-secondary-text-color", "black");
        }
        if(y.isBigLeap()){
            text.getStyle().set("background", "red");
            text.getStyle().set("color", "white");
            text.getStyle().set("--lumo-secondary-text-color", "white");
            
        }
        
    }

    /*    public static final Font largeFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_LARGE);
  //  public static final Font mediumFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
    public static final Font smallFont = Font.createSystemFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    public static final Font urlFont = Font.createSystemFont(Font.FACE_MONOSPACE, Font.STYLE_UNDERLINED, Font.SIZE_MEDIUM);
    FontImage fi;
    FontImage show, hide; 
    static String HEADER="Heading";
    private static String LABEL_TRANS="LabelTrans";
//*/
    public CellFormatter() {

    }

    /*
    public static MultiButton getBasicMultiButton() {
        MultiButton buttonRenderer = new MultiButton();
        buttonRenderer.setRTL(getFormRTL());
        buttonRenderer.setUIID(DAY_UIID);
        buttonRenderer.getUnselectedStyle().setBgTransparency(180);
        return buttonRenderer;
    }

    public static Button getBasicButton() {
        Button buttonRenderer = new Button();
        buttonRenderer.setRTL(getFormRTL());
        buttonRenderer.setUIID(DAY_UIID);
        buttonRenderer.getUnselectedStyle().setBgTransparency(220);
        return buttonRenderer;
    }
//*/
 /*
    element.getStyle().set("color", "red");
//camelCase
element.getStyle().set("fontWeight", "bold");
//kebab-case
element.getStyle().set("font-weight", "bold");

//camelCase
element.getStyle().remove("backgroundColor");
//kebab-case
element.getStyle().remove("background-color");

element.getStyle().has("cursor");
Updated 2020-03-18
     */
    protected static void prepareReligious(Component comp, BasicDate bd) {

        PeriodType m = bd.getCalendar().getMonthName(bd);
        if (m.equals(Months.RAMADHAN)) {
            comp.getElement().getStyle().set("color", "pink");

        }
        if (m.equals(Months.NASEI) || m.equals(Months.ADAR2)) {
            comp.getElement().getStyle().set("color", "red");

        }
        if (m.equals(Months.ALHARAM) | m.equals(Months.RAJAB_MODHAR) | m.equals(Months.RAJAB_RABEEIAH)) {
            comp.getElement().getStyle().set("color", "red");

        }

        if (m.equals(Months.RAMADHAN) & bd.getDay() == 10 & bd.getCalendar() instanceof SamiFixed) {
            comp.getElement().getStyle().set("color", "black");

        }
        if ((m.equals(Months.SAFAR) | m.equals(Months.SAFAR2) | m.equals(Months.RABEI1) | m.equals(Months.RABEI2)) & bd.getCalendar() instanceof SamiCalendar) {
            comp.getElement().getStyle().set("color", "blue");

        }
        if ((m.equals(Months.MUHARRAM) || m.equals(Months.TISHREI)) & bd.getDay() == 10) {
            comp.getElement().getStyle().set("color", "black");

        }

    }

    public int getSeasonJalali(BasicDate bd2) {
        JalaliCalendarIR jc = Application.getFactory().getJalaliIR();//getCalendar(BasicCalendar.JALALI_IR_ID);
        BasicDate bd = jc.getDate(bd2.getDate());
        if (bd.equals(bd.getCalendar().getMaximumDate())) {
            return 0;
        }

        BasicDate s1 = jc.getDate(bd.getYear(), 1, 1);
        BasicDate sum1 = jc.getDate(bd.getYear(), 4, 1);
        BasicDate f1 = jc.getDate(bd.getYear(), 7, 1);
        BasicDate w2 = jc.getDate(bd.getYear(), 10, 1);
        // hussam.println(bd+" "+w1+" "+s1+" testing : "+(bd.getDate()>=w1.getDate())+" testing: "+(bd.getDate()<=s1.getDate()));
        if (bd.getDate() >= s1.getDate() && bd.getDate() < sum1.getDate()) {
            return 2;
            //          hussam.println("blue: "+bd);
        }
        if (bd.getDate() >= sum1.getDate() && bd.getDate() < f1.getDate()) {
            //c.setUIID("SpringLabel");
            return 3;
            //           hussam.println("green: "+bd);
        }
        if (bd.getDate() >= f1.getDate() && bd.getDate() < w2.getDate()) {
            return 4;
        }
        if (bd.getDate() >= w2.getDate()) {
            return 1;
        }

        return 0;
    }

    public int getSeasons128(BasicDate bd2) {
        Solar128Calendar sc = Application.getFactory().getSolar128Calendar();//getCalendar(BasicCalendar.SOLAR_128_ID);
        BasicDate bd = sc.getDate(bd2.getDate());
        if (bd.equals(bd.getCalendar().getMaximumDate())) {
            return 0;
        }

        BasicDate w1 = sc.getDate(bd.getYear(), 1, 1);
        BasicDate s1 = sc.getDate(bd.getYear(), 3, 21);
        BasicDate sum1 = sc.getDate(bd.getYear(), 6, 20);
        BasicDate f1 = sc.getDate(bd.getYear(), 9, 22);
        BasicDate w2 = sc.getDate(bd.getYear(), 12, 21);
        BasicDate w3 = sc.getDate(bd.getYear(), 12, 31);
        // hussam.println(bd+" "+w1+" "+s1+" testing : "+(bd.getDate()>=w1.getDate())+" testing: "+(bd.getDate()<=s1.getDate()));
        if (bd.getDate() >= w1.getDate() && bd.getDate() < s1.getDate()) {
            return 1;
            //          hussam.println("blue: "+bd);
        }
        if (bd.getDate() >= s1.getDate() && bd.getDate() < sum1.getDate()) {
            //c.setUIID("SpringLabel");
            return 2;
            //           hussam.println("green: "+bd);
        }
        if (bd.getDate() >= sum1.getDate() && bd.getDate() < f1.getDate()) {
            return 3;
        }
        if (bd.getDate() >= f1.getDate() && bd.getDate() < w2.getDate()) {
            return 4;
        }
        if (bd.getDate() >= w2.getDate() && bd.getDate() < w3.getDate()) {
            return 1;

        }
        return 0;
    }

    public static String prepareSeasons(long time, SeasonIdentifier si) {
        SeasonIdentifier.Season season = si.getSeason(Application.getFactory().getGregoryCalendar().getDate(time));
        if(season==null)System.err.println("why? "+new Date(time));
        return season.getName();
    }

    public static void prepareSeasons(Component comp, BasicDate bd2, SeasonIdentifier si) {

        SeasonIdentifier.Season season = si.getSeason(bd2);
        //    com.getUnselectedStyle().setFgColor(0x000000);
        comp.getElement().getStyle().set("color", "black");

        switch (season) {
            case WINTER:
                //     comp.getUnselectedStyle().setBgColor(0x99ccff);
                comp.getElement().getStyle().set("color", "blue");

                break;
            case SPRING:
                //    comp.getUnselectedStyle().setBgColor(0x99ffcc, true);
                comp.getElement().getStyle().set("color", "green");

                break;
            case SUMMER:
                comp.getElement().getStyle().set("color", "yellow");

                //   comp.getUnselectedStyle().setBgColor(0xff8080);
                break;
            case FALL:
                comp.getElement().getStyle().set("color", "orange");

                //    comp.getUnselectedStyle().setBgColor(0xffccb3);
                break;
        }

    }

    protected static void prepareToday(Component comp, BasicDate cellDate) {

        BasicDate today = cellDate.getCalendar().getDate(new Date().getTime());
        if (today.equals(cellDate)) {
            comp.getElement().getStyle().set("color", "yellow");

            // comp.getUnselectedStyle().setBgColor(0xffff00);
            //comp.getUnselectedStyle().setFgColor(0x000000);
            //    return;
        }

    }

    /*
    protected void strikeThrough(Button labelDate, BasicDate selectedDate) {
        if ((selectedDate.getCalendar() instanceof LunerLocationCalendar)) {
            return;
        }
        if (selectedDate.equals(selectedDate.getCalendar().getMaximumDate())) {
            labelDate.setText("buyCell");
            if (fi == null) {
                fi = FontImage.createMaterial(FontImage.MATERIAL_EXPLORE, labelDate.getAllStyles());
            }

            labelDate.setIcon(fi);
            //   labelDate.getAllStyles().setStrikeThru(true);
            labelDate.getAllStyles().setAlignment(Component.RIGHT);
            //   labelDate.getAllStyles().setBgColor(0x000050);
            return;
        }
    }

    protected Component prepareHeading(Component c) {
        c.setUIID(HEADER);

        c.getAllStyles().setFont(largeFont);
        c.getAllStyles().setFgColor(0xffffff);
        return c;
    }
//*/
    public static void prepareAll(Component labelDate, BasicDate selectedDate) {

        prepareSeasons(labelDate, selectedDate, Application.getFactory().getDefaultSeasonIdentifier());
        prepareReligious(labelDate, selectedDate);
        prepareToday(labelDate, selectedDate);

    }

    void prepareYearType(Component comp, BasicDate cellDate) {

        if (cellDate.getCalendar().isLeapYear(cellDate)) {
            comp.getElement().getStyle().set("color", "red");

        }

    }
    /*
    void setDateInfo(MultiButton buttonRenderer, BasicDate bd, BasicCalendar get) {
        BasicDate result = get.getDate(bd.getDate());
        buttonRenderer.setTextLine1(getT(result.getCalendar().getMonthName(result).getName()));
        buttonRenderer.setTextLine2(result.getDay() + " " + result.getYear());
        buttonRenderer.setTextLine3(getT(get.getName()));

    }

    void setDayDifference(MultiButton buttonRenderer, BasicDate selectedDate) {
        int x = (int) ((selectedDate.getDate() - CalendarFactory.cleanDate(new Date().getTime())) / DAY);
        buttonRenderer.setTextLine1("" + x);
        buttonRenderer.setTextLine2(getT("daydiff"));
    }
    final static String UIID_SEASON = "seasonLabel";

    void setSeason(Component comp, BasicDate selectedDate, SeasonIdentifier si) {
        prepareSeasons(comp, selectedDate, si);
        SeasonIdentifier.Season season=si.getSeason(selectedDate);
        String x = season.getName();
        comp.getElement().getStyle().set("color", "pink");
            
        comp.getAllStyles().setFgColor(0x000000);
        comp.getAllStyles().setFont(largeFont);
        comp.setText(x);
    }

    void setNewMoonCycle(MultiButton buttonRenderer, BasicDate bd, LunerLocationCalendar llc) {
        if (llc == null) {
            buttonRenderer.setTextLine1(getT("missingLocation"));
            buttonRenderer.setTextLine2(getT("missingLocationExtra"));
            return;
        }
        BasicDate result = llc.getDate(bd.getDate());
        buttonRenderer.setTextLine1(result.getDay() + "");

        buttonRenderer.setTextLine2(getT(llc.getName()));// + " " + getT(result.getCalendar().getMonthName(result).getName()));//getT(result.getCalendar().getMonthName(result).getName()) + " " + );
        //    buttonRenderer.setTextLine3(DAY_UIID);
    }

    void setBlackMoonCycle(MultiButton buttonRenderer, BasicDate bd) {
        BasicCalendar cal = getStore().getCalendar(BasicCalendar.BLACK_MOON_ID);
        BasicDate result = cal.getDate(bd.getDate());
        buttonRenderer.setTextLine1(result.getDay() + "");

        buttonRenderer.setTextLine2(getT(Months.BLACK_MOON.getName()));// + getT(result.getCalendar().getMonthName(result).getShortName()));//getT(result.getCalendar().getMonthName(result).getName()) + " " + );
    }

    Font getLargeFont() {
        return largeFont;
    }
    
    Label getBasicLabel(){
        Label l=new Label();
        l.setUIID(LABEL_TRANS);
        return l;
    }
    Label getBigLabel() {
        Label l = getBasicLabel();
        l.getAllStyles().setFont(largeFont);
        return l;
    }

    Label getMediumLabel() {
        Label l = getBasicLabel();
        l.getAllStyles().setFont(mediumFont);
        return l;
    }

    Label getSmallLabel() {
        Label l = getBasicLabel();
        l.getAllStyles().setFont(smallFont);
        return l;
    }

    void prepareSmallLeap(Button buttonRenderer, BasicDate bd) {
        if(bd.getCalendar().getMonthName(bd).isSmallLeap()){
            buttonRenderer.getAllStyles().setBgColor(0xccffff);
        }
    }
    //*/
}
