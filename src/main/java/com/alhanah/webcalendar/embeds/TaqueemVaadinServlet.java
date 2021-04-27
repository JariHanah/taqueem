/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alhanah.webcalendar.embeds;

import com.vaadin.flow.server.VaadinServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hmulh
 */
@WebServlet(urlPatterns = {"/vaadin/*", "/VAADIN/*", "/frontend/*"})
public class TaqueemVaadinServlet extends VaadinServlet {

     @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Origin is needed for all CORS requests
        String origin = request.getHeader("Origin");
         System.err.println("Origin: "+origin);
        if (origin != null && isAllowedRequestOrigin(origin)) {

            // Handle a preflight "option" requests
            if ("options".equalsIgnoreCase(request.getMethod())) {
                response.addHeader("Access-Control-Allow-Origin", origin);
                response.setHeader("Allow", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS");

                // allow the requested method
                String method = request.getHeader("Access-Control-Request-Method");
                response.addHeader("Access-Control-Allow-Methods", method);

                // allow the requested headers
                String headers = request.getHeader("Access-Control-Request-Headers");
                response.addHeader("Access-Control-Allow-Headers", headers);

                response.addHeader("Access-Control-Allow-Credentials", "true");
                response.setContentType("text/plain");
                response.setCharacterEncoding("utf-8");
                response.getWriter().flush();
                return;
            } // Handle UIDL post requests
            else if ("post".equalsIgnoreCase(request.getMethod())) {
                response.addHeader("Access-Control-Allow-Origin", origin);
                response.addHeader("Access-Control-Allow-Credentials", "true");
                super.service(request, response);
                return;
            }
        }

        // All the other requests nothing to do with CORS
        super.service(request, response);

    }
    /**
     * Check that the page Origin header is allowed.
     */
    private boolean isAllowedRequestOrigin(String origin) {
        // TODO: Remember to limit the origins.
        return origin.matches(".*");
    }
}