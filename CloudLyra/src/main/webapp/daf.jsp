<%-- 
    Document   : index
    Created on : Oct 8, 2014, 6:30:11 AM
    Author     : Jun
--%>

<%@page import="at.ac.tuwien.dsg.edasich.service.core.dafstore.DafStore"%>
<%@page import="java.sql.ResultSet"%>
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
        <link rel="stylesheet" href="css/dropdown.css">
        <link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>
        <link href="http://fonts.googleapis.com/css?family=Allerta" rel="stylesheet" type="text/css">
    </head>
    <body>
        <h3>EDASICH</h3>
        <header>

            <div class="nav">
                <ul>
                    <li class="analytic"><a  href="index.jsp">Analytics</a></li>
                    <li class="daf"><a class="active" href="daf.jsp">DAF</a></li>
                </ul>
            </div>

        </header>
        <br> 
        <% Logger logger = Logger.getLogger(this.getClass().getName());%>





        <form action="Uploader" method="post" enctype="multipart/form-data"> <br>
            <table class="smart-green">

                <tr>
                    <td>Data Analytics Function</td>
                    <td><input type="file" name="file" /></td> 
                </tr>

                <tr>
                    <td>Type</td>
                    <td><select name="type">
                            <option value="cep" selected>Complex Event Processing</option>
                            <option value="wf">Data Analytics Workflow</option>
                        </select></td> 
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
                <th>Data Analytics Function</th>
                <th>Type</th>
                <th>Action</th> 
                <th>Status</th> 

            </tr>

            <%
                DafStore dafStore = new DafStore();
                ResultSet rs = dafStore.getDAF();
                try {
                    while (rs.next()) {
                        String d_id = rs.getString("id");
                        String d_name = rs.getString("name");
                        String d_type = rs.getString("type");
                        String d_status = rs.getString("status");


            %>



            <tr>
                <td><%= d_name%></td>    
                <td><%= d_type%></td> 
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
