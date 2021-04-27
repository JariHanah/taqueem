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
public interface LunerIdentifier {
    public long getNextMonth(long time);
    public long getPreviousMonth(long time);
    public City getCity();
    public boolean isLocationBased();
 //   public String getName();
   // public String getShortName();

    public void setCity(City c);
}

