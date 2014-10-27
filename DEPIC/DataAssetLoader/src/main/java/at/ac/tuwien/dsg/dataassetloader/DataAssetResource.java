/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataassetloader;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.dataassetloader.dastore.DataAssetStore;
import at.ac.tuwien.dsg.dataassetloader.datasource.DataLoader;
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
import javax.xml.bind.JAXBException;

/**
 * REST Web Service
 *
 * @author Jun
 */
@Path("DataAsset")
public class DataAssetResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DataAssetResource
     */
    public DataAssetResource() {
    }


    @PUT
    @Path("daloader")
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
        DataAsset da= dataAssetStore.getDataAsset(daName);
        String daXml="";
        try {
            daXml = JAXBUtils.marshal(daName, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return daXml;
    }
    
    
}
