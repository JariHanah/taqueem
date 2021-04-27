/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar.locationBasid;

/**
 *
 * @author hmulh
 */
public class City{
    public static final City MAKKA=new City("Mecca" ,"مكة المكرمة" , 21.3891, 39.8579);
    String name, arabName;
    double lat, lon;

    public City(String name, String arabName, double lat, double lon) {
        this.name = name;
        this.arabName=arabName;
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    public String getArabName() {
        return arabName;
    }

    
    @Override
    public String toString() {
        return getName()+" "+getArabName()+" "+getLat()+" "+getLon();
    }
    
    
}
