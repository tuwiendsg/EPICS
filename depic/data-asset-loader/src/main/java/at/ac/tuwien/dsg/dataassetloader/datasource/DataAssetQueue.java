/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.datasource;

import java.util.List;

/**
 *
 * @author Jun
 */
public class DataAssetQueue {
    
    String eDaaS;
    String dataAssetID;
    int dataAssetCounter;
    List<String> listOfDataAssetWindows;

    public DataAssetQueue(String eDaaS, String dataAssetID, List<String> listOfDataAssetWindows) {
        this.eDaaS = eDaaS;
        this.dataAssetID = dataAssetID;
        this.listOfDataAssetWindows = listOfDataAssetWindows;
        dataAssetCounter=0;
    }

    public String geteDaaS() {
        return eDaaS;
    }

    public void seteDaaS(String eDaaS) {
        this.eDaaS = eDaaS;
    }

    public String getDataAssetID() {
        return dataAssetID;
    }

    public void setDataAssetID(String dataAssetID) {
        this.dataAssetID = dataAssetID;
    }

    public List<String> getListOfDataAssetWindows() {
        return listOfDataAssetWindows;
    }

    public void setListOfDataAssetWindows(List<String> listOfDataAssetWindows) {
        this.listOfDataAssetWindows = listOfDataAssetWindows;
    }

    public int getDataAssetCounter() {
        return dataAssetCounter;
    }
    
    public void increaseCounter(){
        
        dataAssetCounter++;
    }
    
    

   
    
    
    
    
}
