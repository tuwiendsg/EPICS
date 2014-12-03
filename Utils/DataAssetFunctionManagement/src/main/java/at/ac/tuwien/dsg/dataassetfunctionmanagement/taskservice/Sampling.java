/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetfunctionmanagement.taskservice;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAttribute;
import at.ac.tuwien.dsg.common.entity.eda.da.DataItem;
import at.ac.tuwien.dsg.common.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.store.DataAssetStore;

import at.ac.tuwien.dsg.dataassetfunctionmanagement.util.IOUtils;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.util.JAXBUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;


/**
 *
 * @author Jun
 */
public class Sampling {
    private static final Sampling INSTANCE = new Sampling();

    public static Sampling getInstance() {
        return INSTANCE;
    }

    public void start(double samplingPercentage, String dawID) {
        System.out.println("Sampling Starting ...");
        //algorithm
        
        System.out.println("Sampling percentage: " + samplingPercentage);
        System.out.println("DAW ID: " + dawID);

        samplingData(samplingPercentage, dawID);
        System.out.println("Sampling Completed ...");
    }
    
    
    private int randomInt(int min, int max){
        
        Random random = new Random();
        
        int randomNumber = random.nextInt(max+1 - min) + min;
        return randomNumber;
    }
    
    private void samplingData(double samplingPercentage, String dawID){
        
        DataAssetStore das = new DataAssetStore();
    
        ResultSet rs = das.getDataAssetByID(dawID);
        
        try {
            while (rs.next()){
                
                String dataPartitionID = rs.getString("dataPartitionID");
                InputStream inputStream = rs.getBinaryStream("data");

                StringWriter writer = new StringWriter();
                String encoding = StandardCharsets.UTF_8.name();
                org.apache.commons.io.IOUtils.copy(inputStream, writer, encoding);
                String daXML = writer.toString();

                DataAsset da = JAXBUtils.unmarshal(daXML, DataAsset.class);
                daXML = samplingDataAsset(da, samplingPercentage);
                das.updateDataAsset(dawID, dataPartitionID, daXML);
                
            }
        } catch (Exception ex) {
            Logger.getLogger(Sampling.class.getName()).log(Level.SEVERE, ex.toString());
        }
        
      
        
    }
    
    private String samplingDataAsset(DataAsset da, double samplingPercentage){
        
        String daXML="";
        
        if (da!=null) {
            List<DataItem> listOfDataItems = da.getListOfDataItems();
        
            int noOfRemovingDataItems = (int) Math.round(listOfDataItems.size()*(100-samplingPercentage)*1.0/100);
            
            for (int i=0;i<noOfRemovingDataItems;i++) {
                
                int removedIndex = randomInt(0,listOfDataItems.size()-1);
                listOfDataItems.remove(removedIndex);         
            }
            
            try {
                daXML = JAXBUtils.marshal(da, DataAsset.class);          
            } catch (JAXBException ex) {
                Logger.getLogger(Sampling.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return daXML;
    }
    
 
}
