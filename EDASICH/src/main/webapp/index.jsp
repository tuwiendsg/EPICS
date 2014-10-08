<%-- 
    Document   : index
    Created on : Oct 8, 2014, 6:30:11 AM
    Author     : Jun
--%>

<%@page import="java.util.logging.Level"%>
<%@page import="java.util.logging.Logger"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>EDASICH</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        
        <% Logger logger=Logger.getLogger(this.getClass().getName());%>
        <h3>EDASICH</h3>
        <form action="Uploader" method="post" enctype="multipart/form-data"> <br>
            <table border="0">
                <tr>
                    <td>Type</td>
                    <td><select name="dafType">
                            <option value="stream" selected>Streaming Data</option>
                            <option value="batch">Batch Data</option>
                        </select></td> 
                </tr>
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
        
     
      
    </body>
</html>
