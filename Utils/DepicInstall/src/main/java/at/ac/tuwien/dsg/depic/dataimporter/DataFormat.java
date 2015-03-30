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
public enum DataFormat {
    
    DOUBLE("DOUBLE"), INT("INT"), TEXT("TEXT");

    private String formatType;

    private DataFormat(String formatType) {
        this.formatType = formatType;
    }

    public String getFormatType() {
        return formatType;
    }
}
