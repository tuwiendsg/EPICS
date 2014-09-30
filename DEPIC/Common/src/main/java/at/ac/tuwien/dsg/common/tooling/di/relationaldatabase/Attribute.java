/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.tooling.di.relationaldatabase;

/**
 *
 * @author Jun
 */
public class Attribute {
    String atrributeName;
    String dataType;

    public Attribute(String atrributeName, String dataType) {
        this.atrributeName = atrributeName;
        this.dataType = dataType;
    }

    public String getAtrributeName() {
        return atrributeName;
    }

    public void setAtrributeName(String atrributeName) {
        this.atrributeName = atrributeName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    
    
    
    
}
