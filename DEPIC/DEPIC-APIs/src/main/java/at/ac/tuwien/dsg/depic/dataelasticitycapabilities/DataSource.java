/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.dataelasticitycapabilities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author Jun
 */
@XmlSeeAlso({DataSourceValue.class})
@XmlRootElement
public class DataSource {
    
    String costURL;
    List<DataSourceValue> dataSourceValues;

    public DataSource() {
        dataSourceValues = new ArrayList<DataSourceValue>();
    }
    
    
    public DataSource withCostURL(String costRestURL) {
        this.costURL = costRestURL;
        return this;
    }
    
    public void addDataSource(String dataSource) {
        DataSourceValue dsVal = new DataSourceValue(dataSource, 0.0);
        dataSourceValues.add(dsVal);
    }

    public String getCostURL() {
        return costURL;
    }

    @XmlElement
    public void setCostURL(String costURL) {
        this.costURL = costURL;
    }

    public List<DataSourceValue> getDataSourceValues() {
        return dataSourceValues;
    }

    @XmlElement
    public void setDataSourceValues(List<DataSourceValue> dataSourceValues) {
        this.dataSourceValues = dataSourceValues;
    }
    
    
    
}
