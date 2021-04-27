/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

/**
 *
 * @author hmulh
 */
public interface SeasonIdentifier {
    
    public enum Season{
        
        SPRING("spring"),
        SUMMER("summer"),
        FALL("autumn"),
        WINTER("winter");
        
        String name;
        
        private Season(String name) {
            
            this.name=name;
        
        }
        
        public String getName(){
        
            return name;
        
        }
    }
    
    public Season getSeason(BasicDate b);
    public String getName();
}
