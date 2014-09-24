/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.eda;

/**
 *
 * @author Jun
 */
public class DataAssetFunction {
    String name;
    String daw;
    
    public DataAssetFunction() {
    }

    public DataAssetFunction(String name, String daw) {
        this.name = name;
        this.daw = daw;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDaw() {
        return daw;
    }

    public void setDaw(String daw) {
        this.daw = daw;
    }
 
}
