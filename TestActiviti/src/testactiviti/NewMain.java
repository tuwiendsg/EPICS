/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testactiviti;

import com.bigbross.bossa.Bossa;
import com.bigbross.bossa.BossaFactory;
import com.bigbross.bossa.PersistenceException;
import com.bigbross.bossa.resource.Resource;
import com.bigbross.bossa.resource.ResourceManager;
import com.bigbross.bossa.wfnet.CaseType;
import com.bigbross.bossa.wfnet.CaseTypeManager;
import com.bigbross.bossa.wfnet.Place;
import com.bigbross.bossa.wfnet.Transition;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here

            Bossa bossa = BossaFactory.defaultBossa();
            
            
            CaseType caseType = new CaseType("TestCaseType");

    Place A = caseType.registerPlace("A", 1);
    Place B = caseType.registerPlace("B");
    Place C = caseType.registerPlace("C");
    Place D = caseType.registerPlace("D");
    Place E = caseType.registerPlace("E");
    Place F = caseType.registerPlace("F");
    Place G = caseType.registerPlace("G");
    Place H = caseType.registerPlace("H");

    Transition a = caseType.registerTransition("a", "requesters");
    Transition b = caseType.registerTransition("b", "sales-$a");
    Transition c = caseType.registerTransition("c", "directors");
    Transition d = caseType.registerTransition("d", "sales");
    Transition e = caseType.registerTransition("e", "sales");
    Transition f = caseType.registerTransition("f", "$a");

    a.input(A,  "1");
    a.output(B, "1");
    b.input(B,  "1");
    b.output(C, "!SOK");
    b.output(D, "SOK && DIR");
    b.output(E, "SOK && !DIR");
    c.input(D,  "1");
    c.output(B, "ADIR == 'BACK'");
    c.output(E, "ADIR == 'OK'");
    c.output(H, "ADIR == 'CANCEL'");
    d.input(E,  "1");
    d.output(F, "1");
    e.input(F,  "1");
    e.output(G, "1");
    f.input(C,  "1");
    f.output(B, "OK");
    f.output(H, "!OK");

    HashMap attributes = new HashMap();
    attributes.put("SOK", new Boolean(false));
    attributes.put("DIR", new Boolean(false));
    attributes.put("ADIR", "");
    attributes.put("OK", new Boolean(false));

    caseType.buildTemplate(attributes);
            
            
       CaseTypeManager caseTypeManager = bossa.getCaseTypeManager();

    caseTypeManager.registerCaseType(caseType);     
    
    
    
    ResourceManager resourceManager = bossa.getResourceManager();

    Resource joe = resourceManager.createResource("joe");
    Resource mary = resourceManager.createResource("mary");
    Resource ana = resourceManager.createResource("ana");
    Resource people = resourceManager.createResource("people");
    Resource managers = resourceManager.createResource("managers");

    people.include(joe);
    people.include(mary);
    people.include(ana);
    managers.include(mary);
    managers.include(ana);
    
    

     
            
        } catch (Exception ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
}
