
<%@page import="at.ac.tuwien.dsg.edasich.service.core.dafstore.DafStore"%>
<%@page import="java.sql.ResultSet"%>

<style type="text/css">
<!--
@import url("style.css");
-->
</style>

        <%

            String dafName = "daf";
            Cookie[] cookies = null;
            cookies = request.getCookies();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie cookie = cookies[i];
                    if (cookie.getName().equals("dafName")) {
                        dafName = cookie.getValue();
                        break;
                    }
                }
            }

            if (!dafName.equals("daf")) {

        %>

        <table id="hor-minimalist-b" >
      
            <thead>
            <tr>
                <th>ID</th>
                <th>DAF</th>
                <th>DETECTED TIME</th>
                <th>EVENT</th>
                <th>SEVERITY</th>
            </tr>
            </thead>

            <tbody>
            <%                       
                
                DafStore dafStore = new DafStore();
                ResultSet rs = dafStore.getDAF();
                
                try {
                    while (rs.next()) {
                        String id = rs.getString("id");
                        String daf = rs.getString("daf");
                        String detected_time = rs.getString("detected_time");
                        String event_values = rs.getString("event_values");
                        String severity = rs.getString("severity");

            %>

            <tr>
                <td><%= id%></td>
                <td><%= daf%></td>
                <td><%= detected_time%></td>
                <td><%= event_values%></td>
                <td><%= severity%></td>
            </tr>

            <%
                    }

                } catch (Exception ex) {

                }
            %>

            </tbody>

        </table>



        <% }%>
  