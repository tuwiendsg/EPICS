/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity;

/**
 *
 * @author Jun
 */
public class DataAssetFunction {
    String name;
    String query;
    int numberOfDataItems;

    public DataAssetFunction() {
    }

    public DataAssetFunction(String name, String query, int numberOfDataItems) {
        this.name = name;
        this.query = query;
        this.numberOfDataItems = numberOfDataItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getNumberOfDataItems() {
        return numberOfDataItems;
    }

    public void setNumberOfDataItems(int numberOfDataItems) {
        this.numberOfDataItems = numberOfDataItems;
    }

    
    
    
}
