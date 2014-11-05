/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetfunctionmanagement.taskservice;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataItem;

import at.ac.tuwien.dsg.dataassetfunctionmanagement.util.IOUtils;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.util.JAXBUtils;
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

        IOUtils ioUtils = new IOUtils();
        String daXML = ioUtils.readData(dawID);
        DataAsset da = null;
        try {
            da = JAXBUtils.unmarshal(daXML, DataAsset.class);  
            
        } catch (JAXBException ex) {
            Logger.getLogger(Sampling.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if (da!=null) {
            List<DataItem> listOfDataItems = da.getListOfDataItems();
        
            int noOfRemovingDataItems = (int) Math.round(listOfDataItems.size()*(100-samplingPercentage)*1.0/100);
            
            for (int i=0;i<noOfRemovingDataItems;i++) {
                
                int removedIndex = randomInt(0,listOfDataItems.size()-1);
                listOfDataItems.remove(removedIndex);
                
            }
            
            try {
                daXML = JAXBUtils.marshal(da, DataAsset.class);
                ioUtils.writeData(daXML, dawID);
                
            } catch (JAXBException ex) {
                Logger.getLogger(Sampling.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
        System.out.println("Sampling Completed ...");
    }
    
    
    private int randomInt(int min, int max){
        
        Random random = new Random();
        
        int randomNumber = random.nextInt(max+1 - min) + min;
        return randomNumber;
    }
}
