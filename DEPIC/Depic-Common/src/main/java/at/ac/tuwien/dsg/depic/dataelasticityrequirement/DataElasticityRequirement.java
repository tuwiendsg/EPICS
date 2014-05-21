package at.ac.tuwien.dsg.depic.dataelasticityrequirement;






import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.CategoricalElasticityBoundary;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.CategoricalElasticityCapability;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.Cost;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.DataElasticityConfiguration;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.DataSource;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.DataSourceValue;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.NumericalElasticityBoundary;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.NumericalElasticityCapability;
import at.ac.tuwien.dsg.depic.utility.CategoricalElCapCostDB;
import at.ac.tuwien.dsg.depic.utility.DataSourceCostDB;
import at.ac.tuwien.dsg.depic.utility.NumericalElCapCostDB;
import at.ac.tuwien.dsg.depic.utility.Utilities;
import java.util.ArrayList;
import java.util.List;
import javax.tools.ForwardingJavaFileObject;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jun
 */
@XmlSeeAlso({NumericalElasticityCapability.class, CategoricalElasticityCapability.class, DataSource.class, Cost.class})

@XmlRootElement (name = "DataElasticityRequirement")
public class DataElasticityRequirement {
    
    
    List dataElCapSet;

    public DataElasticityRequirement() {
        dataElCapSet = new ArrayList();
    }

  
    public void addRequirement(NumericalElasticityCapability elCap) {
        DataElasticityConfiguration elConf = elCap.getDataElasticityConfiguration();
        Utilities util = new Utilities();
        NumericalElCapCostDB elCapCostDB = util.unMarshalXML2NumericalElCapCostDB(elConf.getPriceURL());
        
        elCap.setElasticityCapabilityName(elCapCostDB.getName());
        elCap.setElastictyBoundary(new NumericalElasticityBoundary(elCapCostDB.getUpperBound(), elCapCostDB.getLowerBound()));
        elCap.setElasticityValues(elCapCostDB.getElasticityValues());
        
        dataElCapSet.add(elCap);
        
    }
    
    public void addRequirement(CategoricalElasticityCapability elCap) {
        
        DataElasticityConfiguration elConf = elCap.getDataElasticityConfiguration();
        Utilities util = new Utilities();
        CategoricalElCapCostDB elCapCostDB = util.unMarsharlXML2CategoricalElCapCostDB(elConf.getPriceURL());
        
        elCap.setElasticityCapabilityName(elCapCostDB.getName());
        elCap.setElastictyBoundary(new CategoricalElasticityBoundary(elCapCostDB.getUpperBound(), elCapCostDB.getLowerBound()));
        elCap.setElasticityValues(elCapCostDB.getElasticityValues());
        
        dataElCapSet.add(elCap);
    }
    
    public void addRequirement(DataSource elCap) {
        List<DataSourceValue> ds1 = elCap.getDataSourceValues();
        Utilities util = new Utilities();
        System.out.println("Data Cost URL: " + elCap.getCostURL());
        DataSourceCostDB elCapCostDB = util.unMarshalXML2DataSourceCostDB(elCap.getCostURL());
        List<DataSourceValue> ds2 = elCapCostDB.getDataSourceValues();
        
        for (int i=0;i<ds1.size();i++) {
            DataSourceValue vali = ds1.get(i);
            
            for (int j=0;j<ds2.size();j++) {
                DataSourceValue valj = ds2.get(j);
                if (vali.getSourceName().equals(valj.getSourceName())) {
                    vali.setCost(valj.getCost());
                }
                
            }
            
        }
        
        dataElCapSet.add(elCap);
    }
    
    public void addRequirement(Cost elCap) {
        dataElCapSet.add(elCap);
    }

    
    public List getDataElCapSet() {
        return dataElCapSet;
    }

    @XmlElement (name = "DataElasticityCapability")
    public void setDataElCapSet(List dataElCapSet) {
        this.dataElCapSet = dataElCapSet;
    }
    
    
 
    
    
}
