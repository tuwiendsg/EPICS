/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.restws;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetloader.store.DataAssetStore;
import at.ac.tuwien.dsg.dataassetloader.datasource.DataLoader;
import at.ac.tuwien.dsg.dataassetloader.store.DataAssetFunctionStore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

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
    public void requestDataAsset(String dataAssetID) {

        DataAssetFunctionStore dafStore = new DataAssetFunctionStore();
        String dataAssetFunctionXML = dafStore.getDataAssetFunction(dataAssetID);
        
        DataLoader dataLoader = new DataLoader();
        dataLoader.loadDataAsset(dataAssetFunctionXML);
        
        String log ="RECEIVED: " + dataAssetFunctionXML;
        Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, log);
    }
    
    
    @PUT
    @Path("daget")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getDataAsset(String daName) {
        
        DataAssetStore dataAssetStore = new DataAssetStore();  
        String daXml= dataAssetStore.getDataAssetXML(daName);
      
        Logger.getLogger(DataassetResource.class.getName()).log(Level.INFO, "SEND: " + daXml);
        return daXml;
    }
    
    @PUT
    @Path("dastore")
    @Consumes("application/xml")
    public void storeDataAsset(String daXML) {
        
        
        DataAsset da=null;
        try {
            da = JAXBUtils.unmarshal(daXML, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataAssetStore dataAssetStore = new DataAssetStore();  
        dataAssetStore.removeDataAsset(da.getName());
        dataAssetStore.saveDataAsset(daXML, da.getName());

    }

}
