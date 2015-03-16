/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetfunctionmanagement.util;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAttribute;
import at.ac.tuwien.dsg.common.entity.eda.da.DataItem;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.store.CassandraDataAssetStore;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class CsvImporter {
    public  void insertDataToCassandra(String rowNumbers) {
        
        int noOfRows = Integer.parseInt(rowNumbers);
        
        String csvFilePath = "/home/ubuntu/kdd/track2/training.txt";
        BufferedReader br = null;
        int i = 0;

        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(csvFilePath));

            while ((sCurrentLine = br.readLine()) != null) {
                //System.out.println(sCurrentLine);

                String[] strs = sCurrentLine.split("\t");
                //System.out.println("No Of Elements:" + strs.length);
                

                List<DataAttribute> listOfDataAttributes = new ArrayList<DataAttribute>();
                DataAttribute id = new DataAttribute("id", String.valueOf(i));
                DataAttribute adID = new DataAttribute("adID", strs[3]);
                DataAttribute userID = new DataAttribute("userID", strs[11]);
                DataAttribute click = new DataAttribute("click", strs[0]);
                DataAttribute data = new DataAttribute("data", sCurrentLine);
                listOfDataAttributes.add(adID);
                listOfDataAttributes.add(userID);
                listOfDataAttributes.add(click);
                listOfDataAttributes.add(data);
                listOfDataAttributes.add(id);
                
                
                System.out.println(i + " - impression: "+strs[1]+ "  depth: " + strs[5] + " - position: " + strs[6]);
                
                
                
                DataItem item = new DataItem(listOfDataAttributes);
                List<DataItem> listOfDataItems = new ArrayList<DataItem>();
                listOfDataItems.add(item);

                DataAsset dataAsset = new DataAsset("", 0, listOfDataItems);

                
                CassandraDataAssetStore.insertKDD(dataAsset);

//                RestfulWSClient ws = new RestfulWSClient(url);
//                System.out.println(i + " - Cassandra Insert " + ws.callPutMethod(xml));

                i++;

                if (i == noOfRows) {
                    break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
