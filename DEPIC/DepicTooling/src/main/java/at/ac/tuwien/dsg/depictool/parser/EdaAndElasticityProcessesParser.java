/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.parser;

import at.ac.tuwien.dsg.common.entity.process.MetricProcess;
import at.ac.tuwien.dsg.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class EdaAndElasticityProcessesParser {
    
    public static QoRModel parseQoRModel(String xmlString) {
        QoRModel qoRModel = null;
        try {
            qoRModel = JAXBUtils.unmarshal(xmlString, QoRModel.class);
        } catch (JAXBException ex) {
            Logger.getLogger(EdaAndElasticityProcessesParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return  qoRModel;
        
    }
    
    
    public static MetricProcess parseElasticityProcesses(String xmlString) {
        MetricProcess elprs = null;
        try {
            elprs = JAXBUtils.unmarshal(xmlString, MetricProcess.class);
        } catch (JAXBException ex) {
            Logger.getLogger(EdaAndElasticityProcessesParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return  elprs;
        
    }
    
    
}
