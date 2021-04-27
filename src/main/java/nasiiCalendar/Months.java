/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar;

import java.util.List;

/**
 *
 * @author Hussam
 */
public enum Months implements PeriodType {
    MUHARRAM("muharram",30),
    SAFAR("safar",29),
    RABEI1("rabei1",  30),
    RABEI2("rabei2",29),
    JAMAD1("jamad1",30),
    JAMAD2("jamad2", 29),
    RAJAB("rajab",  30),
    SHAABAN("shaaban", 29),
    RAMADHAN("ramadhan", 30),
    SHAWWAL("shawwal", 29),
    THO_QIDAH("thoqidah",30),
    THO_HIJA("thohija",  29),
    ALHARAM("alharam", 30),
    SAFAR2("safar2", 29),
    RAJAB_MODHAR("rajabmodhar", 30),
    RAJAB_RABEEIAH("rajabrabiah", 30),
    NASEI("nasi", 30),
    JANUARY("january", 31),
    FEBUARY("febuary", 28),
    MARCH("march", 31),
    APRIL("april",  30),
    MAY("may",31),
    JUNE("june",30),
    JULY("july", 31),
    AUGUST("august",  31),
    SEPTEMBER("september", 30),
    OCTOBER("october", 31),
    NOVEMBER("november",30),
    DECEMBER("december",  31),
    
    L01("zabih", 13),
    L02("bolaa",13),
    L03("saud",13),
    L04("akhbiyah",13),
    L05("mokaddam", 13),
    L06("moakhar", 13),
    L07("rasha",13),
    L08("shartain",13),
    L09("butain",13),
    L10("thuraya", 13),
    L11("addobran",13),
    L12("hagaah",13),
    L13("hanaah", 13),
    L14("thiraa", 13),
    L15("nathrah",13),
    L16("taraf", 13),
    L17("jabhah", 14),
    L18("zobrah", 13),
    L19("sarfah", 13),
    L20("alawaa", 13),
    L21("sammak", 13),
    L22("alghfr",13),
    L23("zabana",13),
    L24("ikleel",13),
    L25("alkalb",13),//////14
    L26("sholah",13),
    L27("naaim",13),
    L28("baldah",13),
    
    Z01("z01", 32),
    Z02("z02",28),
    Z03("z03", 24),
    Z04("z04",38),///39
    Z05("z05", 25),
    Z06("z06",37),
    Z07("z07", 31),
    Z08("z08",  20),
    Z09("z09",37),
    Z10("z10", 45),
    Z11("z11", 25),
    Z12("z12", 7),
    Z13("z13",18), 
    
    TISHREI("tishrei", 30), 
    CHESHVAN("cheshvan",  29), ////30
    KISLEV("kislev",  30),/////29 
    TEVET("tevet",29),
    SHEVAT("shevat", 30), 
    ADAR("adar", 29), 
    ADAR2("adar2", 30), 
    NISAN("nisan", 30), 
    IYAR("iyar",29), 
    SIVAN("sivan", 30), 
    TAMMUZ("tammuz", 29), 
    AV("av", 30), 
    ELUH("eluh",  29),
    
    HAMAL("hamal",31),
    SAWR("sawr", 31),
    JAWZA("jawza", 31),
    SARATAN("saratan",31),
    ASAD("asad",31),
    SONBOLA("sonbola",31),
    MIZAN("mizan",30),
    AQRAB("aqrab",30),
    QAWS("qaws",30),
    JADI("jadi", 30),
    DALW("dalw",30),
    HUT("hut",29),/////30
    
    COP_1("cop01",30),
    COP_2("cop02",30),
    COP_3("cop03",30),
    COP_4("cop04",30),
    COP_5("cop05",30),
    COP_6("cop06",30),
    COP_7("cop07",30),
    COP_8("cop08",30),
    COP_9("cop09",30),
    COP_10("cop10",30),
    COP_11("cop11",30),
    COP_12("cop12",30),
    COP_13("cop13",5),
    
    
    
    HILAL_MOON("moonMonth", 29),
    BLACK_MOON("blackMonth", 29);
    String enName;
    int days;
    boolean isSmallLeap=false,isBigLeap=false;
    Months(String en, int d) {
        enName = en;
        
        days=d;
        
    }
    
    public String getName(){
        return enName;
    }
   
    @Override
    public boolean isBigLeap() {
        return false;
    }

    @Override
    public boolean isSmallLeap() {
        return false;
    }

    @Override
    public int getTotalLengthInDays() {
        return days;
    }

    @Override
    public long getDurationInTime() {
        return days*BasicCalendar.DAY;
    }

    @Override
    public int getCountAsPeriods() {
        return 1;
    }

    @Override
    public List<PeriodType> getSubPeriods() {
        return null;
    }
}

