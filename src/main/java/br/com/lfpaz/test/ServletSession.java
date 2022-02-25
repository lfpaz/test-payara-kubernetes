/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lfpaz.test;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lfpaz
 */
@WebServlet(urlPatterns = "/sessiontest")
public class ServletSession extends HttpServlet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String key = req.getParameter("key");
        String value = req.getParameter("value");
        String add = req.getParameter("add");
        String clearSession = req.getParameter("clearSession");

        if (add != null && key != null) {
            req.getSession(true).setAttribute(key, value);
            Logger.getGlobal().log(Level.FINE, "SERVLET Key ->{0} Value -> {1}", new Object[]{key, value});
        }

        if (clearSession != null) {
            Logger.getGlobal().log(Level.FINE, "SERVLET Session Invalidade");
            req.getSession(true).invalidate();
        }

        resp.getWriter().print("<html>");
        resp.getWriter().print("<head><title>Test Kubernetes / Rancher</title></head>");
        resp.getWriter().print("<body>");
        resp.getWriter().print("<h1>Session With Servlet Test - Kubernetes (<a href='" + req.getContextPath() + "/index.xhtml'>CDI/JSF Test</a>)</h1>");
        resp.getWriter().print("<h3>Local: " + req.getScheme() + "://" + req.getLocalName() + "" + req.getLocalPort() + "</h3>");
        resp.getWriter().print("<h3>Remote: " + req.getScheme() + "://" + req.getRemoteHost() + "" + req.getRemoteAddr() + "</h3>");
        resp.getWriter().print("<h3>Request: " + req.getRequestURL().toString() + "</h3>");
        resp.getWriter().print("<h3>Session ID: " + req.getSession(true).getId() + "</h3>");
        resp.getWriter().print("<h3>Add Session key/value</h3>");
        resp.getWriter().print("<form action=\"" + req.getRequestURI() + "\"  method=\"POST\">");
        resp.getWriter().print("<table>");
        resp.getWriter().print("<tr>");
        resp.getWriter().print("<td><label for=\"key\">Key:</label></td>");
        resp.getWriter().print("<td><input type=\"text\" name=\"key\" id=\"key\" /></td>");
        resp.getWriter().print("<td><label for=\"value\">Value:</label></td>");
        resp.getWriter().print("<td><input type=\"text\" name=\"value\" id=\"value\" /></td>");
        resp.getWriter().print("<td>");
        resp.getWriter().print("<input type=\"submit\" name=\"add\" value=\"Add\"></input>");
        resp.getWriter().print("</td>");
        resp.getWriter().print("</tr>");
        resp.getWriter().print("</table>");

        resp.getWriter().print("<hr/>");
        resp.getWriter().print("<h3> Values added in session </h3>");

        resp.getWriter().print("<table border='1'>");
        resp.getWriter().print("<th>Key</th><th>Value</th>");
        Enumeration keys = req.getSession().getAttributeNames();
        while (keys.hasMoreElements()) {
            String attribute = (String) keys.nextElement();
            resp.getWriter().print("<tr>");
            resp.getWriter().print("<td>" + attribute + "</td>");
            resp.getWriter().print("<td>" + req.getSession(true).getAttribute(attribute) + "</td>");
            resp.getWriter().print("</tr>");
        }

        resp.getWriter().print("<table/>");
        resp.getWriter().print("<br/>");
        resp.getWriter().print("<hr/>");
        resp.getWriter().print("<input type=\"submit\" name=\"clearSession\" value=\"Clear Session\"></input>");
        resp.getWriter().print("<hr/>");
        resp.getWriter().print("</body>");

        ResourceBundle rb = ResourceBundle.getBundle("git");
        try {
            String branch = rb.getString("git.branch");
            String id = rb.getString("git.commit.id.abbrev");
            String server = InetAddress.getLocalHost().getHostName();
            resp.getWriter().print("Server: " + server + " - Branch: " + branch + " Commit: " + id);
        } catch (UnknownHostException ex) {
            resp.getWriter().print("No version found");
        }
        resp.getWriter().print("</form>");
        resp.getWriter().print("<br/>");
        resp.getWriter().print("</html>");
    }

}
