/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.utility;

import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.CategoricalElasticityValue;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.DataSourceValue;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement (name = "DataSources")
public class DataSourceCostDB {
   List<DataSourceValue> dataSourceValues; 
   String name;

    public DataSourceCostDB() {
        dataSourceValues = new ArrayList<DataSourceValue>();
                
    }

    
    public void addNewValue(DataSourceValue elVal){
        dataSourceValues.add(elVal);
        
    }
    
    public List<DataSourceValue> getDataSourceValues() {
        return dataSourceValues;
    }

    @XmlElement  (name ="DataSource")
    public void setDataSourceValues(List<DataSourceValue> dataSourceValues) {
        this.dataSourceValues = dataSourceValues;
    }

    public String getName() {
        return name;
    }

    @XmlAttribute (name = "name")
    public void setName(String name) {
        this.name = name;
    }

    
   
   
   
   
}
