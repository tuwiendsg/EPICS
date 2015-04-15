/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.taskservice;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAssetForm;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAttribute;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.store.MySqlDataAssetStore;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.util.JAXBUtils;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class Output {
    private static final Output INSTANCE = new Output();
    
    public static Output getInstance() {
        return INSTANCE;
    }

    public void start(String formOfData, String dafID) {
        System.out.println("Output Starting ...");
      
        System.out.println("form of data: " + formOfData);
  
        outputWithFormat(formOfData, dafID);

    }

    private void outputWithFormat(String formOfData, String dafID) {

        if (formOfData.equals(DataAssetForm.CSV.getDataAssetForm())) {

            MySqlDataAssetStore das = new MySqlDataAssetStore();

            ResultSet rs = das.getDataAssetByID(dafID);

            try {
                while (rs.next()) {

                    String dataPartitionID = rs.getString("dataPartitionID");
                    InputStream inputStream = rs.getBinaryStream("data");

                    StringWriter writer = new StringWriter();
                    String encoding = StandardCharsets.UTF_8.name();
                    org.apache.commons.io.IOUtils.copy(inputStream, writer, encoding);
                    String daXML = writer.toString();

                    DataAsset da = JAXBUtils.unmarshal(daXML, DataAsset.class);

                    daXML = convertToCSV(da);
                    System.out.println("CONVERT CSV: " + daXML);
                    das.updateDataAsset(dafID, dataPartitionID, daXML);

                }

                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(Sampling.class.getName()).log(Level.SEVERE, ex.toString());
            }

        } else if (formOfData.equals(DataAssetForm.XML.getDataAssetForm())) {
            // data in xml by default
        } else if (formOfData.equals(DataAssetForm.FIGURE.getDataAssetForm())) {
            // not implemented yet
        }
        

    }

    private String convertToCSV(DataAsset da){
        
        List<DataItem> listOfDataItems = da.getListOfDataItems();
        List<DataItem> listOfCSVDataItems = new ArrayList<DataItem>();
        
        for (DataItem dataItem : listOfDataItems){
            List<DataAttribute> listOfDataAttributes = dataItem.getListOfAttributes();
            String dataItemStr ="";
            for (DataAttribute dataAttribute : listOfDataAttributes){
                dataItemStr += dataAttribute.getAttributeValue()+"\t";
            }
            
            List<DataAttribute> csvAttributes = new ArrayList<DataAttribute>();
            DataAttribute csvAttribute = new DataAttribute("csv", dataItemStr);
            csvAttributes.add(csvAttribute);
            
            DataItem csvDataItem = new DataItem(csvAttributes);
            listOfCSVDataItems.add(csvDataItem);
        }
        
        DataAsset csvDataAsset = new DataAsset(da.getDataAssetID(), da.getPartition(), listOfCSVDataItems);
        
        String daXML="";
        try {
            daXML = JAXBUtils.marshal(csvDataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return daXML;
    }
}
