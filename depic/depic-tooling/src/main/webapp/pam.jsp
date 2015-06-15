<%-- 
    Document   : pam
    Created on : Jun 9, 2015, 3:59:59 PM
    Author     : Jun
--%>

<%@page import="at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlAction"%>
<%@page import="at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction"%>
<%@page import="java.util.List"%>
<%@page import="at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction"%>
<%@page import="at.ac.tuwien.dsg.depic.repository.PrimitiveActionMetadataManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Depic Tooling</title>
        <link rel="stylesheet" href="css/kanso.css?v=2.0.1">
        <link rel="stylesheet" href="css/dropdown.css">
        <link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>
        <link href="http://fonts.googleapis.com/css?family=Allerta" rel="stylesheet" type="text/css">


        <link rel="stylesheet" href="http://jquery-plugins.bassistance.de/treeview/jquery.treeview.css">
        <link rel="stylesheet" href="http://jquery-plugins.bassistance.de/treeview/demo/screen.css">

        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/jquery.cookie.js" type="text/javascript"></script>
        <script src="js/jquery.treeview.js" type="text/javascript"></script>

        <script type="text/javascript" src="js/demo.js"></script>



    </head>
    <body>

        <h3>DEPIC</h3>
        <header>

            <div class="nav">
                <ul>
                    <li class="tool"><a  href="index.jsp">Depic Tooling</a></li>
                    <li class="daf"><a class="active" href="pam.jsp">PAM</a></li>
                </ul>
            </div>

        </header>

        <br>

        <%
            PrimitiveActionMetadataManager pamm = new PrimitiveActionMetadataManager();
            List<MonitoringAction> listOfMonitoringActions = pamm.getMonitoringActionList();
            List<AdjustmentAction> listOfAdjustmentActions = pamm.getAdjustmentActionList();
            List<ResourceControlAction> listOfResourceControlActions = pamm.getResourceControlActionList();


        %>

        <div>
            <ul id="navigation" class="treeview">
                <li class="expandable"><div class="hitarea expandable-hitarea"></div>Monitoring Action
                    <ul style="display: none;">

                        <%                    for (MonitoringAction ma : listOfMonitoringActions) {
                        %>
                        <li><%=ma.getMonitoringActionName()%> - <%=ma.getAssociatedQoRMetric()%></li>
                            <%
                                }
                            %>

                    </ul>
                </li>
                <li class="expandable"><div class="hitarea expandable-hitarea"></div>Adjustment Action
                    <ul style="display: none;">

                        <%                    for (AdjustmentAction aa : listOfAdjustmentActions) {
                        %>
                        <li><%=aa.getActionName()%> - <%=aa.getAssociatedQoRMetric()%></li>
                            <%
                                }
                            %>


                    </ul>
                </li>
                <li class="expandable"><div class="hitarea expandable-hitarea"></div>Resource Control Action
                    <ul style="display: none;">
                        <%                    for (ResourceControlAction ra : listOfResourceControlActions) {
                        %>
                        <li><%=ra.getActionName()%> - <%=ra.getAssociatedQoRMetric()%></li>
                            <%
                                }
                            %>
                    </ul>
                </li>


            </ul>

            <form action="addMonitoringAction.jsp">
                <input type="submit" value="Add Monitoring Action">
            </form>

            <form action="addAdjustmentAction.jsp">
                <input type="submit" value="Add Adjustment Action">
            </form>

            <form action="addResourceControlAction.jsp">
                <input type="submit" value="Add Resource Control Action">
            </form>



        </div>                  
    </body>
</html>
