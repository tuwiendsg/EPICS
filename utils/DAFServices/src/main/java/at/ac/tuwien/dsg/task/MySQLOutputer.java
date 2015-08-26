/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.task;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAttribute;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.mlr.util.IOUtils;
import at.ac.tuwien.dsg.mlr.util.RestfulWSClient;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class MySQLOutputer implements Runnable {

    private Thread t;
    String ip;
    String port;
    String db;
    String user;
    String pass;
    int dataAssetCounter;
    String rootPath;
    String dataAssetLoaderEndpoint;

    public MySQLOutputer() {

        ip = "localhost";
        port = "3306";
        db = "EDARepository";
        user = "root";
        pass = "123";
        dataAssetCounter = 0;
        rootPath = "/home/ubuntu/log/temp";
        dataAssetLoaderEndpoint = "http://localhost:8080/data-asset-loader/rest/dataasset/repo/storedataasset";
    }

    public void run() {
        do {
            String filePathString = rootPath + "/kmeans-daf-gps-" + String.valueOf(dataAssetCounter) + ".data";
            File f = new File(filePathString);
            if (f.exists() && !f.isDirectory()) {

                System.out.println("OUTPUTER-FOUND");

                IOUtils iou = new IOUtils(rootPath);
                
                long  currentTime = System.currentTimeMillis();
                String log =  String.valueOf(dataAssetCounter)+"\t" +String.valueOf(currentTime)+"\n";
                
                iou.writeData(log, "dafEndTime.txt");
                
                
                String dataAssetXML = iou.readData("kmeans-daf-gps-" + String.valueOf(dataAssetCounter) + ".data");

                saveDataAsset(dataAssetXML);
                
                
                
                

                dataAssetCounter++;
            } else {
                System.out.println("OUTPUTER-WAITING");
            }

            try {
                Thread.sleep(1000);

            } catch (InterruptedException ex) {

            }
        } while (true);

    }

    public void start() {
        if (t == null) {
            t = new Thread(this, "outputer");
            t.start();
        }
    }

    private void saveDataAsset(String daXML) {

        
       
        RestfulWSClient ws = new RestfulWSClient(dataAssetLoaderEndpoint);
        ws.callPutMethod(daXML);
        
        
        
        
        
        
//        InputStream daStream = new ByteArrayInputStream(daXML.getBytes(StandardCharsets.UTF_8));
//        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);
//
//        String sql = "INSERT INTO DataAsset(daw_name,partitionID,da) VALUES ('" + dafName + "','" + partitionID + "',?)";
//
//        List<InputStream> listOfInputStreams = new ArrayList<InputStream>();
//        listOfInputStreams.add(daStream);
//
//        connectionManager.ExecuteUpdateBlob(sql, listOfInputStreams);

    }

}
