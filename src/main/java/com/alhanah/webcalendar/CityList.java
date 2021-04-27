/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar;

import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.csv.CSVIterator;
import com.helger.commons.csv.CSVParser;
import com.helger.commons.csv.CSVReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import nasiiCalendar.locationBasid.City;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hmulh
 */
public class CityList {
    static int count=0;
    List<City> list = new ArrayList<>();
    //City lastCity=null;
    
    public CityList() {
        System.err.println("reading citis.........................: "+count++);
        readCities();
        
    }
    
    public static Comparator<City> getDistanceComparator(double lat, double lon) {
        Comparator<City> c = new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                double c1 = Math.pow(o1.getLat() - lat, 2) + Math.pow(o1.getLon() - lon, 2);
                double c2 = Math.pow(o2.getLat() - lat, 2) + Math.pow(o2.getLon() - lon, 2);
                if (c2 == c1) {
                    return 0; 
                }
                if (c2 < c1) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
        return c;
    }
    
    public static Comparator<City> getAlphabetComparator() {
        Comparator<City> c = new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                return (o1.getName().compareTo(o2.getName()));

            }
        };
        return c;
    }

    public synchronized City getClosestCity(double lat, double lon) {
        if (list.size() == 0) {
            readCities();
            if(list.size()==0){
                System.err.println("something is wrong1!!");
                return City.MAKKA;
            }
        }
        List<City> l1 = new ArrayList<>(list);
        Collections.sort(l1, getDistanceComparator(lat, lon));
        //lastCity=l1.get(0);
        return l1.get(0);
    }

    private synchronized void readCities() {
        
        try {
            InputStream in = this.getClass().getClassLoader()
                                .getResourceAsStream("META-INF/resources/worldcities.csv");
            CSVReader read=new CSVReader(new InputStreamReader(in, "utf8"));
            CSVIterator it=new CSVIterator(read); 
            it.next();
            while(it.hasNext()){ 
                ICommonsList<String> line=it.next();
                City city = new City(line.get(0), line.get(1), Double.parseDouble(line.get(2)), Double.parseDouble(line.get(3)));
                list.add(city);
                
            }
            
            Collections.sort(list, getAlphabetComparator());
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(CityList.class.getName()).log(Level.SEVERE, null, ex); 
            System.err.println("BIG ERROR!!!!!!");
        }
        
    }

    public synchronized  List<City> getCities() {
        if (list.size() == 0) {
            readCities();
        }
        return list;
    }
/*
    public City getLastClosestCity() {
        if (lastCity==null)
            return City.MAKKA;
        return lastCity;
    }
    public boolean isSearched(){
        return lastCity!=null;
    }//*/
}
