/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.comments;

import com.vaadin.flow.component.Html;

/**
 *
 * @author hmulh
 */
public class CommentHTML {
    public static final String FACEBOOK_APP_ID="850269315834724";
    public static Html getMeta(){
        return new Html("<meta property=\"fb:app_id\" content=\""+FACEBOOK_APP_ID+"\" />");
    }
    public static Html getEnglishCommentImportHead(){
        return new Html("<div>"
                + "<div id=\"fb-root\"></div>\n" +
"<script async defer crossorigin=\"anonymous\" src=\"https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v10.0&appId="+FACEBOOK_APP_ID+"&autoLogAppEvents=1\" nonce=\"VFLKSEod\"></script>"+
                "</div>");
    }
    public static Html getArabicCommentImportHead(){
        return new Html("<div>"
                + "<div id=\"fb-root\"></div>\n" +
"<script async defer crossorigin=\"anonymous\" src=\"https://connect.facebook.net/ar_AR/sdk.js#xfbml=1&version=v10.0&appId="+FACEBOOK_APP_ID+"&autoLogAppEvents=1\" nonce=\"VFLKSEod\"></script>"+
                "</div>");
    }
    
    public static Html getCommmentBox(String page){
        return new Html("<div class=\"fb-comments\" data-href=\"https://taqueem.net/"+page+"\" data-width=\"400\" data-numposts=\"10\"></div>");
    }
}
