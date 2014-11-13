/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.datacompletenessmonitor.service;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.datacompletenessmonitor.algorithm.CompletenessMonitor;
import at.ac.tuwien.dsg.datacompletenessmonitor.rest.CompletenessResource;
import at.ac.tuwien.dsg.externalserviceutils.DataAssetStore;
import at.ac.tuwien.dsg.externalserviceutils.JAXBUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class CompletenessService {
    
    public double requestMonitorDataCompletenessService(String daID){
        
        
        DataAssetStore das = new DataAssetStore();
        DataAsset da = das.getDataAsset(daID);
        JAXBUtils jAXBUtils = new JAXBUtils();
        String daXMl = "";
        try {
            daXMl = jAXBUtils.marshal(da, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(CompletenessResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("DA: " + daXMl);
        
        CompletenessMonitor monitor = new CompletenessMonitor(da);
        
        
        double completeness = monitor.measureDataCompleteness();
        
        return completeness;
    }
    
}
