/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity;

import java.util.List;

/**
 *
 * @author Jun
 */
public class ElasticDataObject {
    List<DataObjectFunction> listOfDataObjectFunctions;

    public ElasticDataObject() {
    }

    public ElasticDataObject(List<DataObjectFunction> listOfDataObjectFunctions) {
        this.listOfDataObjectFunctions = listOfDataObjectFunctions;
    }
    
    
    
}
