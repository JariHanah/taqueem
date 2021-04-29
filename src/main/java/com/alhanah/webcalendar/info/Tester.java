/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.info;

import com.alhanah.webcalendar.Application;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import nasiiCalendar.BasicCalendar;
import static nasiiCalendar.BasicCalendar.DAY;
import static nasiiCalendar.BasicCalendar.START_SAMI;
import nasiiCalendar.BasicDate;
import nasiiCalendar.CalendarFactory;
import nasiiCalendar.CalendarTester;
import nasiiCalendar.GregoryCalendar;
import nasiiCalendar.MyBasicCalendar;
import nasiiCalendar.Omari30YearLoop;
import nasiiCalendar.SamiFixed;

/**
 *
 * @author hmulh
 */
public class Tester {

    public static void test2() {
        GregoryCalendar s = Application.getFactory().getGregoryCalendar();
        long time = s.getDate(2021, 1, 1).getDate();
        System.err.println("starting......" + new Date(time) + "\toffset+" + Application.getFactory().getOffSet() + "\t" + new Date(s.getStartTime()) + "\t" + BasicCalendar.START_SAMI + "\t" + new Date(START_SAMI));
        System.err.println("BaseTIme:\t" + GregoryCalendar.BASE_TIME + "\t" + new Date(GregoryCalendar.BASE_TIME));
        System.err.println("cleandate: " + Application.getFactory().cleanDate(GregoryCalendar.BASE_TIME));
        System.err.println("cleandate: " + new Date(Application.getFactory().cleanDate(GregoryCalendar.BASE_TIME)));
        Calendar c = Calendar.getInstance();
        c.set(2003, 9, 26, 3, 0, 0);
        System.err.println("Qazwini Time: " + c.getTime().getTime() + "\t" + c.getTime());
        c.set(2020, 7, 20, 3, 0, 0);
        System.err.println("omari Time: " + c.getTime().getTime() + "\t" + c.getTime());
        c.set(1097, 2, 15, 3, 0, 0);
        System.err.println("jalali Time: " + c.getTime().getTime() + "\t" + c.getTime());
        c.set(2001, 0, 1, 3, 0, 0);
        System.err.println("solar128 Time: " + c.getTime().getTime() + "\t" + c.getTime());
        c.set(2001, 1, 10, 3, 0, 0);
        System.err.println("stations Time: " + c.getTime().getTime() + "\t" + c.getTime());
        c.set(2000, 11, 17, 3, 0, 0);
        System.err.println("zodiac Time: " + c.getTime().getTime() + "\t" + c.getTime());
        c.set(1691, 8, 23, 3, 0, 0);
        System.err.println("fatimi Time: " + c.getTime().getTime() + "\t" + c.getTime());
        c.set(2003, 8, 12, 3, 0, 0);
        System.err.println("Coptic Time: " + c.getTime().getTime() + "\t" + c.getTime());
        c.set(1695, 7, 11, 3, 0, 0);
        System.err.println("Jahhaf Time: " + c.getTime().getTime() + "\t" + c.getTime());
        c.set(1969, 2, 19, 3, 0, 0);
        System.err.println("fatimi Time: " + c.getTime().getTime() + "\t" + c.getTime()); 
        
        for (int i = 0; i < 4; i++) {
            BasicDate bd = s.getDate(time);
            System.err.print(bd.getYear() + "\t" + bd.getMonth() + "\t" + bd.getDay() + "\t" + Application.getFactory().getWeekDay(bd) + "\t" + bd.getDate() + "\t");
            System.err.println("");
            Application.getFactory().test(bd);

            time += DAY;

        }
        System.err.println("finished......");
        
    }

    public static void test3() {
        ZoneId zone=ZoneId.systemDefault();
        SamiFixed s = Application.getFactory().getSamiCalendar();
        Omari30YearLoop o = new Omari30YearLoop(zone);
        BasicCalendar umm = Application.getFactory().getCalendar(BasicCalendar.UMM_ALQURA_CALENDAR_V1423);
        BasicDate bd = s.getDate(0, 9, 1);
        long time = bd.getDate();
        System.err.println("starting......");

        Application.getFactory().test(Application.getFactory(), new CalendarTester() {
            public void test(BasicCalendar c) {
                MyBasicCalendar cal = (MyBasicCalendar) c;
                System.err.println(cal.getMatchTime() + "\t" + new Date(cal.getMatchTime()) + "\t" + cal.getStartTime() + "\t" + new Date(cal.getStartTime()) + "\t" + cal.getName());
            }
        });
        //*/
        //    System.exit(0);
        for (int i = 0; i < 365 * 1410; i++) {
            bd = s.getDate(time);
            Application.getFactory().test(bd);
            System.err.print(bd.getYear() + "\t" + bd.getMonth() + "\t" + bd.getDay() + "\t" + Application.getFactory().getWeekDay(bd) + "\t" + bd.getDate() + "\t");
            bd = o.getDate(time);
            Application.getFactory().test(bd);
            System.err.print(bd.getYear() + "\t" + bd.getMonth() + "\t" + bd.getDay() + "\t" + Application.getFactory().getWeekDay(bd) + "\t" + bd.getDate() + "\t");
            bd = umm.getDate(time);
            Application.getFactory().test(bd);
            System.err.print(bd.getYear() + "\t" + bd.getMonth() + "\t" + bd.getDay() + "\t" + Application.getFactory().getWeekDay(bd) + "\t" + bd.getDate() + "\t");
            bd = Application.getFactory().getGregoryCalendar().getDate(time);
            Application.getFactory().test(bd);
            System.err.print(bd.getYear() + "\t" + bd.getMonth() + "\t" + bd.getDay() + "\t" + Application.getFactory().getWeekDay(bd) + "\t" + bd.getDate() + "\t");

            System.err.println("");
            time += DAY;

        }
    }

    public static void test() {
        
    }
    
    public static void testFatimi(){ 
        int weekday[]=new int[] {6,4,1,5,3,4,5,2,6,4,1,5,3,4,4,2,6,4,1,5,3,4,4,2,6,4,1,5,3,2,4,2,6,3,1,5,3,4,4,2,6,3,1,5,2,4,4,2,6,3,1,5,2,4,4,2,6,3,1,5,2,4,4,1,6,3,1,5,2,4,4,1,6,3,4,5,2,4,4,1,6,3,4,5,2,4,4,1,6,3,4,5,2,6,4,1,6,3,4,5};
        BasicCalendar fat=Application.getFactory().getCalendar(BasicCalendar.FATIMI_ID);
        int yearStart=1101;
        for(int i=0;i<weekday.length;i++){
            BasicDate b=fat.getDate(i+yearStart,1,1);
            int week=Application.getFactory().getWeekDay(b.getDate());
            System.err.print(yearStart+"\t"+week+"\t"+weekday[i]);
            if(week==weekday[i])System.err.println("\tOK");else System.err.println("\tWRONG");
        }
    }

    public static void test4() {
        SamiFixed s = Application.getFactory().getSamiCalendar(); 
        final BasicDate bd = s.getDate(0, 1, 1);
        System.err.println("starting......" + bd.getDate());

        Application.getFactory().test(Application.getFactory(), new CalendarTester() {
            public void test(BasicCalendar c) {
                long time = bd.getDate();
                MyBasicCalendar cal = (MyBasicCalendar) c;

                System.err.println(cal.getMatchTime() + "\t" + new Date(cal.getMatchTime()) + "\t" + cal.getStartTime() + "\t" + new Date(cal.getStartTime()) + "\t" + cal.getName());
                Calendar cals = Calendar.getInstance();
                cals.set(514, 8, 6, 0, 0, 0);
                System.err.println("cal: " + cals.getTime().getTime());
                for (int i = 0; i < 1; i++) {
                    BasicDate bd2 = s.getDate(time);
                    if (bd2.getDate() != time) {
                        //    System.err.println("wow:" + ((time - bd2.getDate())) + "\t" + new Date(time) + "\t" + new Date(bd2.getDate()));
                    }
                    //  Application.getFactory().test(bd2);
                    time += DAY;

                }
            }
        });
        //*/
        //    System.exit(0);
        System.err.println("finished......");

    }
}
