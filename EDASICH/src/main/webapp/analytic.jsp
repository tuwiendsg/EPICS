
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
                    $('#eventLogDiv').fadeOut('slow').load("eventlog.jsp").fadeIn("slow");
                    
                   
                }, 5000);
    </script>
    
    <% Logger logger = Logger.getLogger(this.getClass().getName());%>
    <body>
        <h3>File Uploaded!</h3>



        <form action="analytic.jsp"> <br>

            <table border="0">
                <tr>
                    <td>Type</td>
                    <td><select name="engine">
                            <option value="es" selected>Streaming Data</option>
                            <option value="hd">Batch Data</option>
                        </select></td> 
                </tr>
                <tr>
                    <td><input type="submit" name="start" value ="Start"/></td>
                    <td><input type="text" name="daf"></td> 
                </tr>

            </table>
        </form>


        <%
            if (request.getParameter("start") != null) {
                logger.log(Level.INFO, "Start button pressed");
                String dafName = request.getParameter("daf").toString();
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


        %>

        <br>
        <hr>
        <br>
        <div id="eventLogDiv">

        </div>

    </body>
</html>
