<%-- 
    Document   : eventlog
    Created on : Oct 9, 2014, 4:54:39 AM
    Author     : Jun
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="at.ac.tuwien.dsg.edasich.utils.EventLog"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EDASICH</title>
    </head>
    
   
    <body>
        
        <table border="1">
            <caption>DETECTED EVENT</caption>
            <tr>
                <th>ID</th>
                <th>DAF</th>
                <th>DETECTED TIME</th>
                <th>EVENT</th>
                <th>SEVERITY</th>
            </tr>
            
            <%
            EventLog eLog = new EventLog();
            ResultSet rs = eLog.getEventLog("daf1");
            try {
            while (rs.next()) {
                String id = rs.getString("id");
                String daf = rs.getString("daf");
                String detected_time = rs.getString("detected_time");
                String event_values = rs.getString("event_values");
                String severity = rs.getString("severity");
                
                %>
                
                <tr>
                <td><%= id %></td>
                <td><%= daf %></td>
                <td><%= detected_time %></td>
                <td><%= event_values %></td>
                <td><%= severity %></td>
            </tr>
                
                <%
            }

        } catch (Exception ex) {

        }
            %>
            
            
       
        </table>

        
    </body>
</html>
