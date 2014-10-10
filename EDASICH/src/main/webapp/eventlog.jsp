
<%@page import="java.sql.ResultSet"%>
<%@page import="at.ac.tuwien.dsg.edasich.utils.EventLog"%>


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

        <table border="1">
      
            <tr>
                <th>ID</th>
                <th>DAF</th>
                <th>DETECTED TIME</th>
                <th>EVENT</th>
                <th>SEVERITY</th>
            </tr>


            <%                       EventLog eLog = new EventLog();
                ResultSet rs = eLog.getEventLog(dafName);
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



        </table>



        <% }%>
  