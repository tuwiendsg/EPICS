package at.ac.tuwien.dsg.common.tooling.edo.test;

import at.ac.tuwien.dsg.common.tooling.edo.DepicConfiguration;

import java.util.ArrayList;
import java.util.List;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        
       
        NumericalMetricGenerator ng = new NumericalMetricGenerator();
       
        List listOfMetricConfs = new ArrayList();
        
        listOfMetricConfs.add(ng.getNumericalMetricConfig());
        
        String edoID="EDO TYPE1";
        
        
        
        DepicConfiguration conf = new DepicConfiguration(edoID, listOfMetricConfs);
        
       
        
        YamlUtils util = new YamlUtils();
        util.convertObject2Yaml(conf);
      
        
              
    }
    
 
    
    
    
   
    
    
}
