<%-- 
    Document   : generator
    Created on : Apr 12, 2015, 8:45:30 PM
    Author     : Jun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Depic Tooling</title>
    </head>
    <body>
   
        
         <form action="InputSpecificationUploader" method="post" enctype="multipart/form-data"> <br>
            <table class="smart-green">

                <tr>
                    <td>eDaaS Name</td>
                    <td><input type="text" name="edaas"></td> 
                </tr>
                 <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td> 
                </tr>
                
                <tr>
                    <td>Data Asset Function</td>
                    <td><input type="file" name="dataAssetFunction" /></td> 
                </tr>
               
               
                  <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td> 
                </tr>
                <tr>
                    <td>Type</td>
                    <td><select name="type" id="type">
                            <option value="MYSQL" selected>MySql</option>
                            <option value="CASSANDRA" selected>Cassandra</option>                   
                        </select></td> 
                </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td> 
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" name="submit" value="Next"/></td> 
                </tr>

            </table>
        </form>
        
    </body>
</html>
