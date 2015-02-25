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


        <script>
        
        function callAjax() {
                    var x = document.getElementById("edaasName").value;

                    url = "daf.jsp?edaasName=" + x;
   
                    $.ajaxSetup({cache: false});
                    $('#eventDiv').load(url);
        
        }
        </script>

        <link rel="stylesheet" href="css/kanso.css?v=2.0.1">
        <link rel="stylesheet" href="css/dropdown.css">
        <link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>
        <link href="http://fonts.googleapis.com/css?family=Allerta" rel="stylesheet" type="text/css">
    </head>
    <body onload="callAjax()">

        
        
        <h3>DEPIC</h3>
        <header>

            <div class="nav">
                <ul>
                    <li class="tool"><a  href="index.jsp">Depic Tooling</a></li>
                    <li class="daf"><a class="active" href="daf_manager.jsp">Data Asset</a></li>
                </ul>
            </div>

        </header>
    
    
    <%
        String currentSelectedEDaas ="";
        
        if (request.getParameter("edaasname")!=null){
            currentSelectedEDaas = request.getParameter("edaasname");
        }
        
        ElasticityProcessStore elStore = new ElasticityProcessStore();
        List<String> edaasList = elStore.getElasticDaasNames();
        
        
        %>
    
    
        <form action="DataAssetFunctionUploader" method="post" enctype="multipart/form-data" > <br>
            <table class="smart-green">

                <tr>
                    <td>eDaaS</td>
                    <td><select name="edaasName" id="edaasName" onchange="callAjax()">
                            
                            <%
                            for (String edaasName : edaasList) {
                                
                                if (edaasName.equals(currentSelectedEDaas)) {
                                
                            %>
                            
                            <option value="<%= edaasName %>" selected  ><%= edaasName %></option>
                            
                            <%
                            }    else {
                                    
                                    %>
                                    <option value="<%= edaasName %>"  ><%= edaasName %></option>
                                    <%
                                    
                                }
                            }
                            %>
              
                        </select></td> 
                </tr>
                 <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td> 
                </tr>
                <tr>
                    <td>Data Asset ID</td>
                    <td><input type="text" name="dataAssetID"></td> 
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
                    <td></td>
                    <td><input type="submit" name="submit" value="Add DAF"/></td> 
                </tr>

            </table>
        </form>


        <br>
        <br>
        <center><div id="eventDiv">

            </div></center>
    </body>
</html>
