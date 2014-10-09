/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.app;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import java.util.LinkedList;
import java.util.StringTokenizer;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 *
 * @author Anindita
 */
public class DataModelManipulation1 {
    public LinkedList<String> queryResult(String subject2)
    {
        
        LinkedList<String> monitoringInformation=new LinkedList<String>();
        LinkedList<String> monitoringPredicatParameter=new LinkedList<String>();
        //String storageName=new StringTokenizer(subject1,"#").nextToken();
        String storageName="http://somewhere/index";
        String subject1=storageName+"#"+subject2;
        String predicate1="MonitoredObjectInformation";
        
        String predicate3="SensorLocation";
        
        Query sparql = QueryFactory.create("SELECT ?s ?p ?o FROM <"+storageName+"> WHERE { ?s ?p ?o }");
        
        String url="jdbc:virtuoso://128.130.172.230:1111";
        VirtGraph graph=new VirtGraph(storageName,url,"dba","dba");
        if(graph.isEmpty())
            {
               System.out.println("there have no content under the uri of subject");
            }
        else
           {
                System.out.println("graph.getCount() = " + graph.getCount());  
                VirtuosoQueryExecution vqe = VirtuosoQueryExecutionFactory.create (sparql, graph);

                Node subjectNode=Node.createURI(subject1);
                //Node predicateNode=Node.createURI(predicate1);
                //ExtendedIterator iter=graph.find(subjectNode, predicateNode, Node.ANY);
                ExtendedIterator iter=graph.find(subjectNode, Node.ANY, Node.ANY);
                for(;iter.hasNext();)
                    {

                        Triple tr=(Triple)iter.next();
                        String predicate=tr.getPredicate().toString();
                        monitoringInformation.add(predicate);
                        System.out.println("predicate="+predicate);
                        String object=tr.getObject().toString();
                        monitoringInformation.add(object);
                        System.out.println("object="+object);
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
