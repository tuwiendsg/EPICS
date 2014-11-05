/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.restws;

import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetloader.dastore.DataAssetStore;
import at.ac.tuwien.dsg.dataassetloader.datasource.DataLoader;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Jun
 */
@Path("dataasset")
public class DataassetResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DataassetResource
     */
    public DataassetResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.dataassetloader.restws.DataassetResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("daw")
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object
        Configuration config = new Configuration();
        return config.getConfig("EDA.REPOSITORY.IP"); 
    }

    @PUT
    @Path("dafmanagement")
    @Consumes("application/xml")
    public void requestDataAsset(String dataAssetFunctionXML) {
        
        DataLoader dataLoader = new DataLoader();
        dataLoader.loadDataAsset(dataAssetFunctionXML);
   
    }
    
    
    @PUT
    @Path("darepo")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getDataAsset(String daName) {
        
        DataAssetStore dataAssetStore = new DataAssetStore();  
        String daXml= dataAssetStore.getDataAssetXML(daName);
      
        return daXml;
    }

}
