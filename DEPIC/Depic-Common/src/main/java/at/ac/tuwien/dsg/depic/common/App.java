package at.ac.tuwien.dsg.depic.common;

import at.ac.tuwien.dsg.depic.utility.Utilities;
import at.ac.tuwien.dsg.sybl.elasticstate.CategoricalElCapState;
import at.ac.tuwien.dsg.sybl.elasticstate.ElasticState;
import at.ac.tuwien.dsg.sybl.elasticstate.NumericalElCapState;
import java.io.StringReader;
import java.net.URL;
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
        //System.out.println( "Hello World!" );
        //convertElasticState2XML();
        
        try {
            
        String urlStr = "http://128.130.172.216/elfinder/files/download/dataAccuracyPrice.xml";
        Utilities util = new Utilities();
        
        System.out.print("TEST: " + util.urlToString(urlStr));
      
        
        
        }
        catch (Exception ex) {
            
        }
        
        
        
    }
    
    public static void convertElasticState2XML(){
        
        ElasticState eState = new ElasticState();
        eState.setTotalCost(9);
        eState.setStatus("READY");        
        eState.setCurrentBudget(1.5);
        
        List elCapStates = new ArrayList();
        
        CategoricalElCapState elCapState1  = new CategoricalElCapState("DataAccuracy", 1.5, 80);
        NumericalElCapState elCapState2 = new NumericalElCapState("DataPreprocessing", 2.5, 3);
        elCapStates.add(elCapState1);
        elCapStates.add(elCapState2);
        eState.setElCapStates(elCapStates);
        
        Utilities util = new Utilities();
        String xmlStr  = util.convertElasticStateToXML(eState);
        
        System.out.println("XML STR: " + xmlStr);
  
        
        
   
        ElasticState elasticState = util.unMarshalXML2ElasticState(xmlStr);
        
        System.out.println("Status: " + elasticState.getStatus());
        
    }
    
  
}
