package at.ac.tuwien.dsg.qoranalytics.daw.test;

import at.ac.tuwien.dsg.qoranalytics.analytic.Sampling;
import at.ac.tuwien.dsg.qoranalytics.configuration.Configuration;
import at.ac.tuwien.dsg.qoranalytics.connector.MOMConnector;
import at.ac.tuwien.dsg.qoranalytics.daw.engine.WorkflowEngine;

public class demo {

    public static final void main(String[] args) {
        //WorkflowEngine wfEngine = new WorkflowEngine("daw1");
        //wfEngine.startWFEngine();
        
        /*
        MOMConnector connector = new MOMConnector();
        connector.openConnection();
        */
        
        Sampling s = new Sampling();
        s.start(10);
        
    }

}
