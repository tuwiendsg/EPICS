/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.entity.stream;

import at.ac.tuwien.dsg.edasich.entity.DataAssetFunction;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "DataAssetFunctionStreamingData")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataAssetFunctionStreamingData extends  DataAssetFunction{
    
    @XmlElement(name = "listOfEventPatterns", required = true)
    List<EventPattern> listOfEventPatterns;

    public DataAssetFunctionStreamingData() {
    }

    public DataAssetFunctionStreamingData(List<EventPattern> listOfEventPatterns, String daFunctionID, String daFunctionName) {
        super(daFunctionID, daFunctionName);
        this.listOfEventPatterns = listOfEventPatterns;
    }

    public List<EventPattern> getListOfEventPatterns() {
        return listOfEventPatterns;
    }

    public void setListOfEventPatterns(List<EventPattern> listOfEventPatterns) {
        this.listOfEventPatterns = listOfEventPatterns;
    }
    
    
    
    
}
