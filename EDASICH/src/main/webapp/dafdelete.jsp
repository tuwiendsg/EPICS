<%-- 
    Document   : dafdelete
    Created on : Oct 10, 2014, 7:19:34 AM
    Author     : Jun
--%>

<%@page import="at.ac.tuwien.dsg.edasich.utils.EventLog"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EDASICH</title>
    </head>
    <body>
        <%
            
            String dafName = request.getParameter("dafName").toString();
            EventLog el = new EventLog();
            el.deleteDAF(dafName);
             response.sendRedirect("daf.jsp");
            
            %>
    </body>
</html>
