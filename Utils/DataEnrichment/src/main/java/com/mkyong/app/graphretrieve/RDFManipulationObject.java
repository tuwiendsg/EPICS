/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.app.graphretrieve;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.mkyong.app.OperateProperty;
import java.util.LinkedList;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 *
 * @author dsg
 */
public class RDFManipulationObject {

    public LinkedList<String> queryResultObject(String object)
    {
        
        LinkedList<String> monitoringInformation=new LinkedList<String>();
        OperateProperty operateProperty=new OperateProperty();
        //LinkedList<String> monitoringPredicatParameter=new LinkedList<String>();
        //String storageName=new StringTokenizer(subject1,"#").nextToken();
        //String storageName="http://windtunnel.com";
        String storageName=operateProperty.getrdfURI();
        String object1=storageName+"#"+object;
        //String predicate1="MonitoredObjectInformation";
        
        //String predicate3="SensorLocation";
        
        Query sparql = QueryFactory.create("SELECT ?s ?p ?o FROM <"+storageName+"> WHERE { ?s ?p ?o }");
        
        //String url="jdbc:virtuoso://localhost:1111";
        String url=operateProperty.getGraphStorageURI();
        String username=operateProperty.getGraphStorageUserName();
        String password=operateProperty.getGraphStoragePassword();
        VirtGraph graph=new VirtGraph(storageName,url,username,password);
        if(graph.isEmpty())
            {
               System.out.println("there have no content under the uri of subject");
            }
        else
           {
                System.out.println("graph.getCount() = " + graph.getCount());  
                VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, graph);

                Node objectNode=Node.createLiteral(object1);
                //Node predicateNode=Node.createURI(predicate1);
                //ExtendedIterator iter=graph.find(subjectNode, predicateNode, Node.ANY);
                ExtendedIterator iter=graph.find(Node.ANY, Node.ANY, objectNode);
                for(;iter.hasNext();)
                    {

                        Triple tr=(Triple)iter.next();
                        String subject=tr.getSubject().toString();
                        monitoringInformation.add(subject);
                        System.out.println("subject="+subject);
                        String predicate=tr.getPredicate().toString();
                        monitoringInformation.add(predicate);
                        System.out.println("predicate="+predicate);
                        
                    }
          
        
                /*Node predicateNode3=Node.createURI(predicate3);
                ExtendedIterator iter3=graph.find(subjectNode, predicateNode3, Node.ANY);
                for(;iter3.hasNext();)
                    {

                        Triple tr=(Triple)iter.next();
                        String Location=tr.getObject().toString();
                        monitoringInformation.add(Location);
                        System.out.println("Location="+Location);
                    }*/
        }
        	
        return monitoringInformation;
}
}


