package com.mkyong.rest;

import com.mkyong.app.DataModelManipulation1;
import java.util.LinkedList;
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
        @Produces(MediaType.TEXT_PLAIN)
	public String printMessage(@PathParam("param") String sensorName) {
            LinkedList<String> monitoringresult=new DataModelManipulation1().queryResult(sensorName);

           StringBuffer result=new StringBuffer();		
//String result = sensorName + " is monitoring Methane gas at silo No.1. Concentration of Methane gas is 2.2 mol/L";
            for(int i=0;i<monitoringresult.size();i++)
            {
            result=result.append("\n "+monitoringresult.get(i));
            }
		return "hello    "+result.toString();

	}

}