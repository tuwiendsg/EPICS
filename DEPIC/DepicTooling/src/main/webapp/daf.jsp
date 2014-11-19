<%-- 
    Document   : daf
    Created on : Nov 18, 2014, 7:17:36 PM
    Author     : Jun
--%>


<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="at.ac.tuwien.dsg.depictool.elstore.ElasticityProcessStore"%>
<style type="text/css">
<!--
@import url("style.css");
-->
</style>

        <%
            
            String edaasName = "";
            
            if (request.getParameter("edaasName")!=null){
                edaasName = request.getParameter("edaasName");
            }
            
            ElasticityProcessStore elasticityProcessStore = new ElasticityProcessStore();
            
            if (!edaasName.equals("")) {

        %>

        <table id="hor-minimalist-b" >
      
            <thead>
            <tr>
                <th>eDaaS</th>
                <th>Data Asset ID</th>
            </tr>
            </thead>

            <tbody>
            <%                       
                
              
               ResultSet rs = elasticityProcessStore.getDataAssetFunction(edaasName);
                
                try {
                    while (rs.next()) {
                        String edaas = rs.getString("edaas");
                        String dataAssetID = rs.getString("dataAssetID");

            %>

            <tr>
                <td><%= edaas%></td>
                <td><%= dataAssetID%></td>
            </tr>

            <%
                    }

                } catch (Exception ex) {

                }
            %>

            </tbody>

        </table>

<% } %>

  