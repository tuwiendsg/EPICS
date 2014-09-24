/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.demo;

import at.ac.tuwien.dsg.depictool.app.Generator;
import at.ac.tuwien.dsg.depictool.entity.others.ActionDependency;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.common.entity.qor.QoRMetric;
import at.ac.tuwien.dsg.depictool.others.entity.DataElasticityProcessConfiguration;
import at.ac.tuwien.dsg.common.entity.eda.DataAssetFunction;
import at.ac.tuwien.dsg.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.common.entity.eda.ElasticState;
import at.ac.tuwien.dsg.depothers.ictool.entity.MetricElasticityProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorAction;
import at.ac.tuwien.others.dsg.depictool.entity.Parameter;
import at.ac.tuwien.dsg.common.entity.qor.Range;
import at.ac.tuwien.dsg.depictool.en.qortity.TriggerValues;
import at.ac.tuwien.dsg.depictool.util.ElasticityProcessRepositorty;
import at.ac.tuwien.dsg.depictool.util.YamlUtils;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        SampleData sampleData = new SampleData();
        ElasticDataAsset edo = sampleData.sampleEDO();
        DataElasticityProcessConfiguration elasticityProcessConfiguration = sampleData.sampleDEPConf();
        
        Generator generator = new Generator(edo, elasticityProcessConfiguration);
        
        generator.generateElasticityProcesses();
        //generator.generateAPIs();
        
      
     /*   
        
        YamlUtils yamlUtils = new YamlUtils();
        yamlUtils.convertElasticDataObject2Yaml( sampleData.sampleEDO());
        
        yamlUtils.convertElasticProcessConfiguration2Yaml( sampleData.sampleDEPConf());
       */ 
        //System.out.println("EDO: " +xmlString);
        
    
    }
    
    
   
    
}
