<%-- 
    Document   : EDO
    Created on : Aug 18, 2014, 11:07:49 AM
    Author     : Jun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Define EDO</title>
    </head>
    <body>
        
        <form action="main.jsp" method="GET">
        
        <h3>Data Items</h3>
        <h5>Type of database: 
        <select>
            <option value="Relational Database">Relational Database</option>
            <option value="Key-valued Database">Key-valued Database</option>
            <option value="Document-based Database">Document-based Database</option>
        </select> </h5>

        <h5>Structure of data: <input type="file" name="file"  </h5>

        <h5>Number of data items: <input type="text" name="noOfDataItems"> </h5>
        
        <br>
        
        <br>

        <h3>Elastic States</h3>
        <h5>Data Elasticity Metric</h5>
        <h5>Name <input type="text" name="name"></h5>
        <h5>ID <input type="text" name="ID"></h5>
        <input type="submit" name="submit" value="Submit">

        
        </form>
    </body>
</html>
