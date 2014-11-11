/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.datasource.test;

import at.ac.tuwien.dsg.common.utils.IOUtils;
import at.ac.tuwien.dsg.dataassetloader.datasource.DataLoader;

/**
 *
 * @author Jun
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        IOUtils io = new IOUtils();
        
        
        String dataAssetFunctionXML = io.readData("pc100");
        
       
        
        DataLoader dataLoader = new DataLoader();
        dataLoader.loadDataAsset(dataAssetFunctionXML);
    }
    
}
