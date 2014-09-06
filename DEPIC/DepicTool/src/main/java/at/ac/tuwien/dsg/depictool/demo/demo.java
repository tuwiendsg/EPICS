/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.demo;

import at.ac.tuwien.dsg.depictool.app.Generator;
import at.ac.tuwien.dsg.depictool.entity.ControlAction;
import at.ac.tuwien.dsg.depictool.entity.DataElasticityMetric;
import at.ac.tuwien.dsg.depictool.entity.DataElasticityProcessConfiguration;
import at.ac.tuwien.dsg.depictool.entity.DataObjectFunction;
import at.ac.tuwien.dsg.depictool.entity.ElasticDataObject;
import at.ac.tuwien.dsg.depictool.entity.ElasticState;
import at.ac.tuwien.dsg.depictool.entity.MetricElasticityProcess;
import at.ac.tuwien.dsg.depictool.entity.MonitorAction;
import at.ac.tuwien.dsg.depictool.entity.Parameter;
import at.ac.tuwien.dsg.depictool.entity.Range;
import at.ac.tuwien.dsg.depictool.entity.TriggerValues;
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
        ElasticDataObject edo = sampleData.sampleEDO();
        DataElasticityProcessConfiguration elasticityProcessConfiguration = sampleData.sampleDEPConf();
        
        Generator generator = new Generator(edo, elasticityProcessConfiguration);
        
        generator.generateElasticityProcesses();
        
        
        
        /*
        
        YamlUtils yamlUtils = new YamlUtils();
        yamlUtils.convertElasticDataObject2Yaml(sampleEDO());
        
        yamlUtils.convertElasticProcessConfiguration2Yaml(sampleDEPConf());
        */
        //System.out.println("EDO: " +xmlString);
        
    
    }
    
    
   
    
}
