/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic;

/**
 *
 * @author Jun
 */
public class DataFunctionality {
    String dataFunctionalityID;

    public DataFunctionality() {
    }
    
    
    
    public DataFunctionality withDataFunctionalityID(String url) {
    
            this.dataFunctionalityID = url;
            return this;
    }

    public String getDataFunctionalityID() {
        return dataFunctionalityID;
    }

    public void setDataFunctionalityID(String dataFunctionalityID) {
        this.dataFunctionalityID = dataFunctionalityID;
    }

    
    
}
