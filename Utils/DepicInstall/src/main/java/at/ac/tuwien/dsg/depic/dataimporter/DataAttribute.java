/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataimporter;

/**
 *
 * @author Jun
 */
public class DataAttribute {
    String attributeName;
    int index;
    DataFormat dataFormat;

    public DataAttribute() {
    }

    public DataAttribute(String attributeName, int index, DataFormat dataFormat) {
        this.attributeName = attributeName;
        this.index = index;
        this.dataFormat = dataFormat;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public DataFormat getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }
    
    
    
    
}
