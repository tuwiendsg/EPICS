package at.ac.tuwien.dsg.qoranalytics.daw.test;

import at.ac.tuwien.dsg.qoranalytics.analytic.LeastSquareRegression;
import at.ac.tuwien.dsg.qoranalytics.analytic.Sampling;
import at.ac.tuwien.dsg.qoranalytics.configuration.Configuration;
import at.ac.tuwien.dsg.qoranalytics.connector.MOMConnector;
import at.ac.tuwien.dsg.qoranalytics.daw.engine.WorkflowEngine;
import at.ac.tuwien.dsg.smartcom.model.Message;

public class demo {

    public static final void main(String[] args) {
        /*
        WorkflowEngine wfEngine = new WorkflowEngine("daw1");
        wfEngine.startWFEngine();
        */
        
        MOMConnector connector = new MOMConnector();
        connector.openConnection();
       


// connector.sendCriticalMessage();
        
        /*
        Sampling s = new Sampling();
        s.start(10);
        */
        
        
        /*
        LeastSquareRegression lsr = new LeastSquareRegression();
        lsr.start(5, 2);
        */
        
    }

}
