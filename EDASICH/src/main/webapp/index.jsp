
<%@page import="at.ac.tuwien.dsg.edasich.utils.EventLog"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="at.ac.tuwien.dsg.edasich.configuration.Configuration"%>
<%@page import="at.ac.tuwien.dsg.edasich.service.engine.AnalyticController"%>
<%@page import="javax.xml.bind.JAXBException"%>
<%@page import="at.ac.tuwien.dsg.edasich.utils.JAXBUtils"%>
<%@page import="at.ac.tuwien.dsg.edasich.entity.stream.DataAssetFunctionStreamingData"%>
<%@page import="at.ac.tuwien.dsg.edasich.utils.IOUtils"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.util.logging.Logger"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>EDASICH</title>
    </head>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js"></script>
    <script>
        var auto_refresh = setInterval(
                function ()
                {
                    $.ajaxSetup({cache: false});
                    $('#eventDiv').load("eventlog.jsp");


                }, 5000);
    </script>

    <% Logger logger = Logger.getLogger(this.getClass().getName());%>
    <body>
        
        
        <%
            if (request.getParameter("start") != null) {
                
                
                
                
                logger.log(Level.INFO, "Start button pressed");
                String dafName = request.getParameter("daf").toString();

                Cookie dafCookie = new Cookie("dafName", dafName);
                dafCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(dafCookie);

                String dafType = request.getParameter("engine").toString();
                logger.log(Level.INFO, dafName + " : " + dafType);

                String xmlData = IOUtils.readData(dafName);
                logger.log(Level.INFO, xmlData);
                DataAssetFunctionStreamingData daf = null;
                try {
                    daf = JAXBUtils.unmarshal(xmlData, DataAssetFunctionStreamingData.class);
                    logger.log(Level.INFO, "f name: " + daf.getDaFunctionName());

                    AnalyticController controler = new AnalyticController();
                    controler.submitDataAssetFunctionStreamingData(dafType, daf);
                    Configuration.submitMOMConf(dafType);
                    controler.startAnalyticEngine(dafType, daf.getDaFunctionID());

                } catch (JAXBException ex) {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
            
            
            
            if (request.getParameter("stop") != null) {
            
                logger.log(Level.INFO, "Stop button pressed");
                String dafName = request.getParameter("daf").toString();

                Cookie dafCookie = new Cookie("dafName", "daf");
                dafCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(dafCookie);

                String dafType = request.getParameter("engine").toString();
                logger.log(Level.INFO, dafName + " : " + dafType);
                
                AnalyticController controler = new AnalyticController();
                    controler.stopAnalyticEngine(dafType, dafName);
            
            }

              if (request.getParameter("start") == null) {

                Cookie dafCookie = new Cookie("dafName", "daf");
                dafCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(dafCookie);
            }

        %>
        
        
        <div id="maindiv">
        <h3>EDASICH</h3>

        <form action="index.jsp"> 

            <table border="0">
                <tr>
                    <td>Type</td>
                    <td><select name="engine">
                            <option value="es" selected>Streaming Data</option>
                            <option value="hd">Batch Data</option>
                        </select></td> 
                </tr>
                <tr>
                    <td>Data Asset Function</td>
                    <td><select name="daf">

                            <%                                
            
                  String dafName = "daf1";
                  
                  if (request.getParameter("daf") != null) {
                      
                      dafName = request.getParameter("daf").toString();
                  }
              
              
                                EventLog eLog = new EventLog();
                                ResultSet rs = eLog.getDAF();
                                try {
                                    while (rs.next()) {
                                        String d_id = rs.getString("id");
                                        String d_name = rs.getString("name");
                                        if (dafName.equals(d_name)) {
                            %>

                            <option value="<%= d_name%>" selected  ><%= d_name%></option>

                            <%

                                        } else {
                                            
                                            %>

                            <option value="<%= d_name%>"  ><%= d_name%></option>

                            <%
                                            
                                            
                                            
                                        }

                                    }

                                } catch (Exception ex) {

                                }
                            %>

                        </select></td> 
                </tr>

                <tr>
                    <td><input type="submit" name="start" value ="Start"/></td>
                    <td></td> 
                </tr>
                <tr>
                    <td><input type="submit" name="stop" value ="Stop"/></td>
                    <td></td> 
                </tr>

            </table>
        </form>


        

        </div>

        <br>
        <hr>
        <br>
        <div id="eventDiv">

        </div>

    </body>
</html>
