package com.mkyong.rest;

import at.ac.tuwien.dsg.dataenrichment.Utils;
import com.mkyong.app.graphretrieve.*;
import java.util.LinkedList;
import java.util.UUID;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//http://localhost:8080/RESTfulExample/rest/message/hello%20world
@Path("/sensor")
public class SensorRestService {

    @GET
    @Path("/{param}")
    @Produces(MediaType.TEXT_HTML)
    public String printMessage(@PathParam("param") String sensorList) {

        String[] sensors = sensorList.split(",");

        String tabStrs = "";

        for (String sensor : sensors) {
            LinkedList<String> monitoringresult = new RDFManipulationSubject().queryResultSubject(sensor);

            String rowStrs = "";

            for (int i = 0; i < monitoringresult.size(); i = i + 2) {
                rowStrs = rowStrs + "                <tr>\n";
                rowStrs = rowStrs + "                    <td>" + monitoringresult.get(i) + "</td>\n";
                rowStrs = rowStrs + "                    <td>" + monitoringresult.get(i + 1) + "</td>\n";
                rowStrs = rowStrs + "                </tr>\n";

            }

            String tabStr = "<table border=\"1\">\n"
                    + "            <thead>\n"
                    + "                <tr>\n"
                    + "                    <th>Predicate</th>\n"
                    + "                    <th>Object</th>\n"
                    + "                </tr>\n"
                    + "            </thead>\n"
                    + "            <tbody>\n"
                    + rowStrs
                    + "            </tbody>\n"
                    + "        </table>\n"
                    + "\n"
                    + " <br> <br> <br>      \n";

            tabStrs += tabStr;

        }
        
        String htmlData = "<html>\n"
                + "    <head>\n"
                + "        <title>The information related of </title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + tabStrs
                + "    </body>\n"
                + "</html>\n"
                + "";
        UUID uuid = UUID.randomUUID();

        String fileName = uuid.toString() + ".html";
        Utils util  = new Utils();
        util.writeToHTMLFile(htmlData, fileName);
        //return "The information related Sensor "+result.toString();
        return "";

    }

}
