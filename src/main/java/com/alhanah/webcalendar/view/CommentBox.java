/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.view;

import com.alhanah.webcalendar.Application;
import static com.alhanah.webcalendar.HanahI18NProvider.AR;
import com.alhanah.webcalendar.comments.CommentHTML;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;

/**
 *
 * @author hmulh
 */
public class CommentBox extends Div {

    public CommentBox() {
        //prepare();
       // showExportedComp();
       addFaceBook();
    }

    public void prepare() {
        add(new Html("<div class=\"powr-comments\" id=\"c5e05303_1617658800\"></div>"));
        add(new Html("<script src=\"https://www.powr.io/powr.js?platform=html\"></script>"));

        add(new H1("TEST"));
        add(new Html("<script type='text/javascript' src='/frontend/bower_components/webcomponentsjs/webcomponents-loader.js'></script>\n"));
        add(new Html("<link rel='import' href='/vaadin/web-component/date-form.html'>"));

        add(new Html("<date-form width=400 height=400>testing</date-form>"));
        add(new Html("<date-form></date-form>"));

        add(new H1("done"));

        add(new Html("<div class=\"iframely-embed\"><div class=\"iframely-responsive\" style=\"height: 140px; padding-bottom: 0;\"><a href=\"https://www.alhanah.com/index.php/2020/10/27/%25d8%25a7%25d9%2584%25d9%2581%25d8%25b1%25d9%2582-%25d8%25a8%25d9%258a%25d9%2586-%25d8%25a7%25d9%2584%25d8%25b1%25d8%25b5%25d8%25af-%25d9%2588%25d8%25a7%25d9%2584%25d8%25aa%25d9%2582%25d9%2588%25d9%258a%25d9%2585/\" data-iframely-url=\"//cdn.iframe.ly/RoyP9LJ\"></a></div></div>"));
        add(new Html("<script async src=\"//cdn.iframe.ly/embed.js\" charset=\"utf-8\"></script>"));
    }
    // Just testing exported code inside the app iteself
    private void showExportedComp() {
        add(new H4("Testing Component")); //testing to add same dateheader but with HTML code.
        add(new DateHeader());//this in app widget
        add(new H4("Testing Exported Component")); //testing to add same dateheader but with HTML code.
        
        Html dev=new Html("<script type='text/javascript' src='/frontend/bower_components/webcomponentsjs/webcomponents-loader.js'></script>");
        Html dev2=new Html("<link rel='import' href='/web-component/date-form.html'>");
        Html h = new Html("<script type='text/javascript' src='/VAADIN/build/webcomponentsjs/webcomponents-loader.js'></script>");
        Html h2 = new Html("<script type='module' src='/web-component/date-form.js'></script>");
        Html h3 = new Html("<date-form></date-form>");
        add(dev);
        add(dev2);
        add(h);
        add(h2);
        add(h3);
        add(new H4("Finished Testing Component"));
    }

    private void addFaceBook() {
        add(CommentHTML.getMeta());
        add(Application.getSelectedLocale().equals(AR)?CommentHTML.getArabicCommentImportHead():CommentHTML.getEnglishCommentImportHead());
        UI.getCurrent().addAfterNavigationListener((ane) -> {
            String page=ane.getLocation().getPath();
            
            add(CommentHTML.getCommmentBox(page));
            //anchor.setHref(ane.getLocation().getPath()+"?" + MyParameters.LANG + "=" + Application.getOtherLocale().getLanguage());
        });
    }

}
