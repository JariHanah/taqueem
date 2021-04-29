/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasiiCalendar.locationBasid;

import java.time.ZoneId;
import java.util.Date;
import static nasiiCalendar.BasicCalendar.DAY;
import static nasiiCalendar.BasicCalendar.HOUR;
import nasiiCalendar.CalendarFactory;
import org.shredzone.commons.suncalc.MoonPhase;
import org.shredzone.commons.suncalc.MoonTimes;

/**
 *استلمت الجمعية الفلكية الأردنية برقية من مجلس الإفتاء الأعلى في المملكة العربية السعودية تبين النظام المعتمد في تقويم أم القرى لتحديد بدايات الأشهر الهجرية في التقويم، والمثال التالي يوضّح النظام : إذا كان يوم 29 من كانون أول/ديسمبر هو مثلا اليوم 29 من شهر شعبان ، والإقتران المركزي يحدث بعد غروب الشمس في مكة المكرمة (و لنقل في الساعة 11 مساء) من يوم 29 كانون الأول/ديسمبر، فإن عمر القمر عند غروب شمس اليوم التالي (30 كانون الأول/ديسمبر ) (ولنقل إنه في الساعة 5 مساء) سيكون 18 ساعة أي أكثر من 12 ساعة ، لذا فإن ذلك اليوم (30 كانون الأول/ديسمبر ) هو اليوم الأول من شهر رمضان، حتى لو لم يكن الهلال الجديد قد وصل إلى مرحلة الإقتران بعد مع غروب الشمس يوم 29 شعبان (29 كانون الأول/ديسمبر ).و بشكل عام فإنه في مثل هذه الحالات سيغرب القمر قبل غروب الشمس .
استلمت الجمعية الفلكية الأردنية برقية من مجلس الإفتاء الأعلى في المملكة العربية السعودية تبين النظام المعتمد في تقويم أم القرى لتحديد بدايات الأشهر الهجرية في التقويم، والمثال التالي يوضّح النظام : إذا كان يوم 29 من كانون أول/ديسمبر هو مثلا اليوم 29 من شهر شعبان ، والإقتران المركزي يحدث بعد غروب الشمس في مكة المكرمة (و لنقل في الساعة 11 مساء) من يوم 29 كانون الأول/ديسمبر، فإن عمر القمر عند غروب شمس اليوم التالي (30 كانون الأول/ديسمبر ) (ولنقل إنه في الساعة 5 مساء) سيكون 18 ساعة أي أكثر من 12 ساعة ، لذا فإن ذلك اليوم (30 كانون الأول/ديسمبر ) هو اليوم الأول من شهر رمضان، حتى لو لم يكن الهلال الجديد قد وصل إلى مرحلة الإقتران بعد مع غروب الشمس يوم 29 شعبان (29 كانون الأول/ديسمبر ).و بشكل عام فإنه في مثل هذه الحالات سيغرب القمر قبل غروب الشمس .

 * @author hmulh
 */
public class UmAlquraStandardV1419 extends AbstractBlackMoonMonth{

    public UmAlquraStandardV1419(ZoneId zone) {
        super(zone);
    }
    
    @Override
    protected long getNextMonth(MoonPhase phase) {
        long blackPhaseTime=getTimeInstant(phase.getTime());
        long blackDay=CalendarFactory.dayStart(blackPhaseTime, getZone());
        MoonTimes moontimes=MoonTimes.compute().at(getCity().getLat(), getCity().getLon()).on(new Date(blackDay)).execute();
        if(moontimes.getSet().compareTo(phase.getTime())<0){
            moontimes = MoonTimes.compute().at(getCity().getLat(), getCity().getLon()).on(new Date(blackDay+DAY)).execute();
            if(getTimeInstant(moontimes.getSet())-blackPhaseTime>12*HOUR)return blackDay+DAY;
            else return blackDay+2*DAY;
        }else return blackDay+DAY;
            
        
    }
    
    
}
