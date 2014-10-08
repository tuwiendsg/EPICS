<%-- 
    Document   : analytic
    Created on : Oct 8, 2014, 6:57:02 AM
    Author     : Jun
--%>

<%@page import="java.util.logging.Level"%>
<%@page import="java.util.logging.Logger"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EDASICH</title>
    </head>
    <% Logger logger=Logger.getLogger(this.getClass().getName());%>
    <body>
        <h3>File Uploaded!</h3>
        
        
        
        <form action="analytic.jsp"> <br>
            <input type="submit" name="start" value ="Start"/>
        </form>
        
        
        <%
            if (request.getParameter("start")!=null) {
                logger.log(Level.INFO, "Start button pressed");
            }
            
            
            
            
            %>
        
        
    </body>
</html>
