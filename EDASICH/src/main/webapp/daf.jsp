<%-- 
    Document   : index
    Created on : Oct 8, 2014, 6:30:11 AM
    Author     : Jun
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="at.ac.tuwien.dsg.edasich.utils.EventLog"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.util.logging.Logger"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>EDASICH</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style type="text/css">
            <!--
            @import url("style.css");
            -->
        </style>
        <link rel="stylesheet" href="css/kanso.css?v=2.0.1">

    </head>
    <body>


        <% Logger logger = Logger.getLogger(this.getClass().getName());%>
    <center><h3>EDASICH</h3></center>
        <form action="Uploader" method="post" enctype="multipart/form-data"> <br>
            <table class="smart-green">

                <tr>
                    <td>Data Asset Function</td>
                    <td><input type="file" name="file" /></td> 
                </tr>

                <tr>
                    <td></td>
                    <td><input type="submit" name="submit"/></td> 
                </tr>

            </table>
        </form>


        <br>

        <center><table id="ver-minimalist">
            <tr>
                <th>Data Asset Function</th>
                <th>Action</th> 
                <th>Status</th> 

            </tr>

            <%
                EventLog eLog = new EventLog();
                ResultSet rs = eLog.getDAF();
                try {
                    while (rs.next()) {
                        String d_id = rs.getString("id");
                        String d_name = rs.getString("name");
                        String d_status = rs.getString("status");


            %>



            <tr>
                <td><%= d_name%></td>    
                <td><a href="dafdelete.jsp?dafName=<%= d_name%>">Delete</a></td> 
                <td><%= d_status%></td>
            </tr>

            <%
                    }

                } catch (Exception ex) {

                }
            %>


            </table></center>




    </body>
</html>
