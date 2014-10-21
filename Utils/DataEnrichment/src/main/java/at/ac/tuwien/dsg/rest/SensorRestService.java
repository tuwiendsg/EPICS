package at.ac.tuwien.dsg.rest;

import at.ac.tuwien.dsg.dataenrichment.Configuration;
import at.ac.tuwien.dsg.dataenrichment.Utils;
import at.ac.tuwien.dsg.app.graphretrieve.*;
import java.util.LinkedList;
import java.util.UUID;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


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
            String header="The information related of : "+sensor;

            for (int i = 0; i < monitoringresult.size(); i = i + 2) {
                rowStrs = rowStrs + "                <tr>\n";
                rowStrs = rowStrs + "                    <td>" + monitoringresult.get(i) + "</td>\n";
                rowStrs = rowStrs + "                    <td>" + monitoringresult.get(i + 1) + "</td>\n";
                rowStrs = rowStrs + "                </tr>\n";

            }

            String tabStr = "<h3>"+header+"</h3>"
                    + "<table id=\"ver-minimalist\" >\n"
                    + "            <thead>\n"
                    + "                <tr>\n"
                    + "                    <th>Predicate</th>\n"
                    + "                    <th>Object</th>\n"
                    + "                </tr>\n"
                    + "            </thead>\n"
                    + "            <tbody>\n"
                    + rowStrs
                    + "            </tbody>\n"
                    + "        </table>\n" ;
                 
                   

            tabStrs += tabStr;

        }

        String htmlData = "<html>\n"
                + "    <head>\n"
                + "        <title>The information related of </title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "        <link rel=\"stylesheet\" href=\"style.css\">"
                + "    </head>\n"
                + "    <body>\n"
                + tabStrs
                + "    </body>\n"
                + "</html>\n"
                + "";
        UUID uuid = UUID.randomUUID();

        String fileName = uuid.toString() + ".html";
        Utils util = new Utils();
        util.writeToHTMLFile(htmlData, fileName);
        

        String htmlUrl = "http://"+Configuration.getConfig("ENRICHMENT.IP")+":"+Configuration.getConfig("ENRICHMENT.PORT")+"/DataEnrichment/"+ fileName;

        return htmlUrl;

    }

}
