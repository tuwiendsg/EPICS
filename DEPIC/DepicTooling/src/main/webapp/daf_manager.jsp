<%@page import="java.util.List"%>
<%@page import="at.ac.tuwien.dsg.depictool.elstore.ElasticityProcessStore"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Depic Tooling</title>
        
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js"></script>
  
    <link rel="stylesheet" href="css/kanso.css?v=2.0.1">
    <link rel="stylesheet" href="css/dropdown.css">
    <link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>
    <link href="http://fonts.googleapis.com/css?family=Allerta" rel="stylesheet" type="text/css">
    </head>
    <body>
        
        <h3>DEPIC</h3>
        <header>

            <div class="nav">
                <ul>
                    <li class="tool"><a  href="index.jsp">Depic Tooling</a></li>
                    <li class="edaas"><a class="active" href="daf_manager.jsp">eDaaS Manager</a></li>
                </ul>
            </div>

        </header>
    
    
    <%
        
        ElasticityProcessStore elStore = new ElasticityProcessStore();
        List<String> edaasList = elStore.getElasticDaasNames();
        
        
        %>
    
    
        <form action="DataAssetFunctionUploader" method="post" enctype="multipart/form-data"> <br>
            <table class="smart-green">

                <tr>
                    <td>eDaaS</td>
                    <td><select name="edaas">
                            
                            <%
                            for (String edaasName : edaasList) {
                            %>
                            
                            <option value="<%= edaasName %>" selected  ><%= edaasName %></option>
                            
                            <%
                            }    
                            %>
              
                        </select></td> 
                </tr>
                 <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td> 
                </tr>
                
                <tr>
                    <td>Data Asset Function</td>
                    <td><input type="file" name="daf" /></td> 
                </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td> 
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" name="submit" value="Add DAF"/></td> 
                </tr>

            </table>
        </form>
        
    </body>
</html>
