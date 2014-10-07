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
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 *
 * @author Anindita
 */
public class ObjectMonitor {
    public Map<String, List<String>> monitoredObjectInformation(String subject, String predicate)
    {
        
        Map<String, List<String>> monitoredInformation=new HashMap<String, List<String>>();
        LinkedList<String> monitoredObject=new DataModelManipulation().queryResult(subject, predicate);
        
         String storageName=new StringTokenizer(subject,"#").nextToken();
        
        Query sparql = QueryFactory.create("SELECT ?s ?p ?o FROM <"+storageName+"> WHERE { ?s ?p ?o }");
        
        String url="jdbc:virtuoso://localhost:1111";
        VirtGraph graph=new VirtGraph(storageName,url,"dba","dba");
        if(graph.isEmpty())
            {
               System.out.println("there have no content under the uri of subject");
            }
        else
           {
                System.out.println("graph.getCount() = " + graph.getCount());  
                VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, graph);

                //Node subjectNode=Node.createURI(subject1);
                Node predicateNode=Node.createURI(predicate);
                
                for(int i=0;i<monitoredObject.size();i++)
                {
                Node objectNode=Node.createLiteral(monitoredObject.get(i).substring(1,monitoredObject.get(i).length()-1));
                List<String> monitoredSensorName=new ArrayList<String>();
                ExtendedIterator iter=graph.find(Node.ANY, predicateNode, objectNode);
                for(;iter.hasNext();)
                    {

                        Triple tr=(Triple)iter.next();
                        String subjectSensorName=tr.getSubject().toString();
                        monitoredSensorName.add(subjectSensorName);
                        System.out.println("object="+subjectSensorName);
                    }
                monitoredInformation.put(monitoredObject.get(i), monitoredSensorName);
        }
        	
    }
        return monitoredInformation;
}
}
