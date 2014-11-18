/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.parser;

import at.ac.tuwien.dsg.common.entity.process.MetricProcess;
import at.ac.tuwien.dsg.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depictool.util.YamlUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class ElasticityProcessesParser {
    
    public QoRModel parseQoRModel(String xmlString) {
   
        QoRModel qoRModel  = YamlUtils.unmarshallYaml(QoRModel.class, xmlString);

        return  qoRModel;
        
    }
    
    
    public MetricProcess parseElasticityProcesses(String xmlString) {
        MetricProcess elprs = YamlUtils.unmarshallYaml(MetricProcess.class, xmlString);
        return  elprs;
        
    }
    
    
}
