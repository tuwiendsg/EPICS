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
import java.util.StringTokenizer;
import javax.persistence.Tuple;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 *
 * @author Anindita
 */
public class DataModelManipulation {
    public LinkedList<String> queryResult(String subject1,String predicate1)
    {
        //String subject="http://somewhere/index#SensorA";
        //String predicate="MonitoredObjectName";
        String storageName=new StringTokenizer(subject1,"#").nextToken();
        //System.out.println("String tokenizer@@@@@"+test1.nextToken());
        //String test1="http://somewhere/index";
    Query sparql = QueryFactory.create("SELECT ?s ?p ?o FROM <"+storageName+"> WHERE { ?s ?p ?o }");
    String url="jdbc:virtuoso://localhost:1111";
    VirtGraph graph=new VirtGraph("test1",url,"dba","dba");
    LinkedList<String> monitoringInformation=new LinkedList<String>();
    
        System.out.println("graph.isEmpty() = " + graph.isEmpty());
	System.out.println("graph.getCount() = " + graph.getCount());
        VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, graph);
        
        Node subjectNode=Node.createURI(subject1);
        Node predicateNode=Node.createURI(predicate1);
        ExtendedIterator iter=graph.find(subjectNode, predicateNode, Node.ANY);

        
		
        for(;iter.hasNext();)
                {
                    
                    Triple tr=(Triple)iter.next();
                    String object=tr.getObject().toString();
                    monitoringInformation.add(object);
                    System.out.println("object="+object);
                }
        	/*ResultSet results = vqe.execSelect();
		
                while (results.hasNext()) {
			QuerySolution result = results.nextSolution();
		    //RDFNode graph1 = result.get("graph");
		    RDFNode s = result.get("s");
                    
		    RDFNode p = result.get("p");
		    RDFNode o = result.get("o");
		    System.out.println("Subject="+s+" Predicate="+p+" object="+o);
		}*/
        return monitoringInformation;
}
}
