/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.linkeddatamodel;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.LinkedList;
import javax.persistence.Tuple;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 *
 * @author Anindita
 */
public class DataModelManipulation {
    public void queryResult()
    {
        String subject="http://somewhere/index#SensorA";
        String predicate="MonitoredObjectName";
    Query sparql = QueryFactory.create("SELECT ?s ?p ?o FROM <test1> WHERE { ?s ?p ?o }");
    String url="jdbc:virtuoso://localhost:1111";
    VirtGraph graph=new VirtGraph("test1",url,"dba","dba");
    LinkedList<String> monitoringInformation=new LinkedList<String>();
    
        System.out.println("graph.isEmpty() = " + graph.isEmpty());
	System.out.println("graph.getCount() = " + graph.getCount());
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, graph);
        
        Node subjectNode=Node.createURI(subject);
        Node predicateNode=Node.createURI(predicate);
        ExtendedIterator iter=graph.find(subjectNode, predicateNode, Node.ANY);

        
		
        for(;iter.hasNext();)
                {
                    //monitoringInformation.add(iter.next().toString());
                    System.out.println((Triple)iter.next());
                }
        /*for(int i=0;i<monitoringInformation.size();i++)
        {
            System.out.println("monitoring object="+monitoringInformation.get(i));
        }
		ResultSet results = vqe.execSelect();
		
                while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
		    //RDFNode graph1 = result.get("graph");
		    RDFNode s = result.get("s");
                    
		    RDFNode p = result.get("p");
		    RDFNode o = result.get("o");
		    System.out.println("Subject="+s+" Predicate="+p+" object="+o);
		}*/
}
}
