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
    String dataFunctionalityURL;

    public DataFunctionality() {
    }
    
    
    
    public DataFunctionality withDataFunctionalityURL(String url) {
    
            this.dataFunctionalityURL = url;
            return this;
    }

    public String getDataFunctionalityURL() {
        return dataFunctionalityURL;
    }

    public void setDataFunctionalityURL(String dataFunctionalityURL) {
        this.dataFunctionalityURL = dataFunctionalityURL;
    }
    
    
    
    
}
