/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.entity;

/**
 *
 * @author Jun
 */
public class DataAssetFunction {
    
    String daFunctionID;
    String daFunctionName;

    public DataAssetFunction() {
    }

    public DataAssetFunction(String daFunctionID, String daFunctionName) {
        this.daFunctionID = daFunctionID;
        this.daFunctionName = daFunctionName;
    }

    public String getDaFunctionID() {
        return daFunctionID;
    }

    public void setDaFunctionID(String daFunctionID) {
        this.daFunctionID = daFunctionID;
    }

    public String getDaFunctionName() {
        return daFunctionName;
    }

    public void setDaFunctionName(String daFunctionName) {
        this.daFunctionName = daFunctionName;
    }
    
    

    
    
  
}
