/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.esperstreamprocessing.queueclient;


import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAttribute;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class SensorDataLoader {

    public SensorDataLoader() {
    }

    public void readData() {

   int windowConst = 5000;
        int recordIndex =0;
            int dataAssetID = 0;
            List<DataItem> listOfDataItems = new ArrayList<DataItem>();
            
            for (int fileName = 0; fileName < 96; fileName++) {

                //String dataPath = cfx.getConfigPath();
                String dataPath = "/Volumes/DATA/BigData/data/2014-09-10";
                dataPath = dataPath + "/" + String.valueOf(fileName) + ".txt";

                try {
                    BufferedReader br = new BufferedReader(new FileReader(dataPath));

                    // StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null || dataAssetID<=10) {

                // sb.append(line);
                        //sb.append(System.lineSeparator());
                        line = br.readLine();
                        String[] strs = line.split(",");
                        String newLine = "";
                        List<DataAttribute> listOfDataAttributes = new ArrayList<DataAttribute>();
                        for (int i = 0; i < strs.length; i++) {
                            DataAttribute dataAttribute = new DataAttribute(String.valueOf(i), strs[i]);
                            listOfDataAttributes.add(dataAttribute);
//                            
//                            
//                            if (i != strs.length - 1) {
//                                newLine = newLine + "\t" + strs[i];
//                            } else {
//                                newLine += strs[i];
//                            }
                            
                        }
                        
                        DataItem dataItem = new DataItem(listOfDataAttributes);
                        listOfDataItems.add(dataItem);
                       // System.out.println("Counter: " + recordIndex);
                        recordIndex++;
                 
                        if (recordIndex >= windowConst) {
                            //marshalling and send data asset
                            DataAsset da = new DataAsset("daf-gps-" +String.valueOf(dataAssetID), 0, listOfDataItems);
                            //String daXML = JAXBUtils.marshal(da, DataAsset.class);

//                            MapMessage message = session.createMapMessage();
//                            message.setString("DataAsset", daXML);
                            
                         //   if (dataAssetID<10) {
                            
                            MySqlDataAssetStore msdas = new MySqlDataAssetStore();
                            msdas.storeDataAsset(da);
                          //  }
                            System.out.println("Sending : " +dataAssetID);
                            listOfDataItems.clear();
//                            producer.send(message);
//
//                            try {
//                                Thread.sleep(5000);
//
//                            } catch (InterruptedException ex) {
//
//                            }
                           dataAssetID++;  
                           
                          
                            recordIndex =0;
                        } 
                        
                    }
                    
                    
                    if (dataAssetID>10){
                               break;
                           }

                    br.close();
                } catch (Exception e) {

                }

            }
       

    }

}
