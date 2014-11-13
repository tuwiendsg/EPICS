/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.test;

import at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing.DataAnalyticFunctionCep;
import at.ac.tuwien.dsg.edasich.service.core.dafstore.DafStore;
import at.ac.tuwien.dsg.edasich.utils.JAXBUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class testdafstore {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        DafStore dafStore = new DafStore();
        String dafXML = dafStore.getDafXML("daf3");
        
        System.out.println("XML: " + dafXML);
        
        try {
            DataAnalyticFunctionCep daf = JAXBUtils.unmarshal(dafXML, DataAnalyticFunctionCep.class);
            System.out.println("Daf Name: " + daf.getDafName());
            System.out.println("Daf ELP: " + daf.getDafAnalyticCep().getEplStatement());
            
            
        } catch (JAXBException ex) {
            Logger.getLogger(testdafstore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
