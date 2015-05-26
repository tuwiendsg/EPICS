/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.generator;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AnalyticTask;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class NewMain1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            
            List<Parameter> analyticTaskParameters = new ArrayList<Parameter>();
            Parameter analayticParameter = new Parameter("stopCondition", "int", "5");
            analyticTaskParameters.add(analayticParameter);
            
            AnalyticTask analyticTask = new AnalyticTask("kmeans", analyticTaskParameters);
            
            
            String abc = JAXBUtils.marshal(analyticTask, AnalyticTask.class);
            
            System.out.println(abc);
            
        } catch (JAXBException ex) {
            Logger.getLogger(NewMain1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
