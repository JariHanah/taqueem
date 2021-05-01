/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hmulh
 */
public class MyMemory extends Div{
    List<MemoryRecord>records=new ArrayList<>();
    TextArea area=new TextArea("Memory");

    public MyMemory() {
        add(area);
        area.setWidthFull();
        this.setWidthFull();
        Util.makeLTR(area);
        append("strt");
    }
    
    public void append(String message){
        MemoryRecord m=new MemoryRecord(message);
        records.add(m);
        StringBuilder sb=new StringBuilder();
        for(MemoryRecord mr:records){
            sb.append(mr.message+"\tU: "+mr.used+"MB\tF: "+mr.free+"MB\tT:"+mr.total+"MB\tM: "+mr.max+"MB\n");
        }
        area.setValue(sb.toString());
    }
}
class MemoryRecord {
    int used;
    int free;
    int max;
    int total;
    String message;
private static final long MEGABYTE = 1024L * 1024L;

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }
    public MemoryRecord(String message) {
        this.message=message;
        total=(int) bytesToMegabytes(Runtime.getRuntime().totalMemory());
        max=(int) bytesToMegabytes(Runtime.getRuntime().maxMemory());
        free=(int) bytesToMegabytes(Runtime.getRuntime().freeMemory());
        used=total-free;
    }
    
}
