/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.datasource;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class DataBufferSession {
    
    String sessionID;
    int retrivedDataAssetIndex;
    
    List<String> listOfDataAssetWindows;
    List<String> listOfCopiedDataAssetWindows;

    public DataBufferSession(String sessionID) {
        this.sessionID = sessionID;
        retrivedDataAssetIndex = 0;
        listOfDataAssetWindows = new ArrayList<String>();
        listOfCopiedDataAssetWindows = new ArrayList<String>();
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public int getRetrivedDataAssetIndex() {
        return retrivedDataAssetIndex;
    }

    public void setRetrivedDataAssetIndex(int retrivedDataAssetIndex) {
        this.retrivedDataAssetIndex = retrivedDataAssetIndex;
    }

    public List<String> getListOfDataAssetWindows() {
        return listOfDataAssetWindows;
    }

    public void setListOfDataAssetWindows(List<String> listOfDataAssetWindows) {
        this.listOfDataAssetWindows = listOfDataAssetWindows;
    }

    public List<String> getListOfCopiedDataAssetWindows() {
        return listOfCopiedDataAssetWindows;
    }

    public void setListOfCopiedDataAssetWindows(List<String> listOfCopiedDataAssetWindows) {
        this.listOfCopiedDataAssetWindows = listOfCopiedDataAssetWindows;
    }
    
    
    
    
    
}
