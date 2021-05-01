/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.time.ZoneId;
import java.util.Arrays;
import static java.util.Calendar.YEAR;
import java.util.List;
import static nasiiCalendar.SamiFixed.LONG_CYCLES;

/**
 *
 * @author hmulh
 */
public class HashimiCalendar extends SolarCalendar{
    SamiFixed sami;
    GregoryCalendar greg;
    private static final int BIG_CYCLE = 353 * 382;
    private static final int STARTDATE = -105;
    final BasicYear Y_NORMAL=new BasicYear("LunerYear", false, false, new PeriodType[] {
                Months.RAMADHAN, Months.SHAWWAL, Months.JAMAD1, Months.JAMAD2, Months.RABEI1, Months.RABEI2, 
                Months.RAJAB, Months.THO_QIDAH, Months.THO_HIJA, Months.MUHARRAM, Months.SAFAR, Months.SHAABAN});
   final BasicYear Y5=new BasicYear("haram5", false, true, new PeriodType[] {
                Months.RAMADHAN, Months.SHAWWAL, Months.JAMAD1, Months.JAMAD2, Months.ALHARAM, Months.RABEI1, Months.RABEI2, 
                Months.RAJAB, Months.THO_QIDAH, Months.THO_HIJA, Months.MUHARRAM, Months.SAFAR, Months.SHAABAN});
   final BasicYear Y9=new BasicYear("haram9", false, true, new PeriodType[] {
                Months.RAMADHAN, Months.SHAWWAL, Months.JAMAD1, Months.JAMAD2, Months.RABEI1, Months.RABEI2, 
                Months.RAJAB, Months.THO_QIDAH, Months.ALHARAM, Months.THO_HIJA, Months.MUHARRAM, Months.SAFAR, Months.SHAABAN});
   final BasicYear Y13=new BasicYear("haram13", false, true, new PeriodType[] {
                Months.RAMADHAN, Months.SHAWWAL, Months.JAMAD1, Months.JAMAD2, Months.RABEI1, Months.RABEI2, 
                Months.RAJAB, Months.THO_QIDAH, Months.THO_HIJA, Months.MUHARRAM, Months.SAFAR, Months.ALHARAM, Months.SHAABAN});
   
    public HashimiCalendar(ZoneId zone) {
        super("Hashimi", LONG_CYCLES, BIG_CYCLE, STARTTIME, STARTDATE, zone);
        sami=new SamiFixed(zone);
        greg=new GregoryCalendar(zone);
        
    }
    
    @Override
    protected BasicDate calculateDate(int y, int m, int d) {
        BasicDate base=sami.getDate(y, m, d);
        BasicDate ramadhan=getRamadhanDateForYear(y);
        long dif=base.getDate()-ramadhan.getDate();
        int month=(int) (dif/MONTH)+1;
        return new MyDate(base.getDate(), base.getDay(), month, base.getYear()-1, this);
    }
    @Override
    public int getMonthLength(BasicDate bd) {
        return sami.getMonthLength(bd);
        //return b.getCalendar().getMonthLength(b);
        //  return getYearType(bd.getYear()).getStore().getLengths()[bd.getMonth()];
    }
    @Override
    public boolean isLeapYear(int year) {
        return (getYearType(year)!=Y_NORMAL);
    }

    @Override
    public BasicDate getDate(long time) {
        BasicDate base=sami.getDate(time);
        BasicDate ramadhan=getPreviousRamadhanForSamiDate(base);
        long dif=base.getDate()-ramadhan.getDate();
        if(dif<0){
        //    ramadhan=getPreviousRamadhanForSamiDate(base.getYear()-1);
            dif=base.getDate()-ramadhan.getDate();
        }
        int month=(int) (dif/MONTH)+1;
        return new MyDate(time, base.getDay(), month, base.getYear()-1, this);
    }

    @Override
    public PeriodType getYearType(int year) {
        BasicDate b=sami.getDate(year+1, 12, 15);
        BasicDate b1=getPreviousRamadhanForSamiDate(b);
        BasicDate b2=getPreviousRamadhanForSamiDate(b);
        if(((b2.getDate()-b1.getDate())/DAY)>380){
            return Y13;
        }else{
            return Y_NORMAL;
        }
    }
    public BasicDate getPreviousRamadhanForSamiDate(BasicDate sb){
        BasicDate result=null;
        for(int m=-3*12;m<0;m++){
            BasicDate b=sami.getDate(sb.getDate()+m*MONTH);
            b=sami.getDate(b.getYear(), b.getMonth(), 1);
            BasicDate tempGreg=greg.getDate(b.getDate());
            System.err.println("Testing SB: "+sb+"\n\t"+b);
            if(tempGreg.getDate()>greg.getDate(tempGreg.getYear(), 8, 24).getDate()){
                System.err.println("\t\tYES: "+tempGreg);
                result= b;
            }
        }
        return result;
    }
    
    public BasicDate getRamadhanDateForYear(int year){
        
        for(int i=7;i<11;i++){
            BasicDate temp=sami.getDate(year, i, 1);
            BasicDate tempGreg=greg.getDate(temp.getDate());
            if(tempGreg.getDate()>greg.getDate(tempGreg.getYear(), 8, 24).getDate()){
                //System.err.println("tempGreg: "+tempGreg);
                return temp;
            }
        }
        return null;
    }
    @Override
    public List<PeriodType> getYearTypes() {
        return Arrays.asList(new BasicYear[]{Y13, Y9, Y5, Y_NORMAL});
    }
    
}
