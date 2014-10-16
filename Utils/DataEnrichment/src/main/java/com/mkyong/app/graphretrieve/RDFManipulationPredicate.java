/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.app.graphretrieve;


import at.ac.tuwien.dsg.dataenrichment.Configuration;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
//import com.mkyong.app.OperateProperty;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import virtuoso.jena.driver.VirtGraph;
import virtuoso.jena.driver.VirtuosoQueryExecution;
import virtuoso.jena.driver.VirtuosoQueryExecutionFactory;

/**
 *
 * @author Anindita
 */
public class RDFManipulationPredicate {
 
    public LinkedList<String> queryResultPredicate(String predicate)
    {
        
        LinkedList<String> monitoringInformation=new LinkedList<String>();
        //OperateProperty operateProperty=new OperateProperty();
        //LinkedList<String> monitoringPredicatParameter=new LinkedList<String>();
        //String storageName=new StringTokenizer(subject1,"#").nextToken();
        //String storageName="http://windtunnel.com";
        //String storageName=operateProperty.getrdfURI();
        String storageName=Configuration.getConfig("RDFURI");
        String subject1=storageName+"#"+predicate;
        
        
        Query sparql = QueryFactory.create("SELECT ?s ?p ?o FROM <"+storageName+"> WHERE { ?s ?p ?o }");
        
        
        //String url=operateProperty.getGraphStorageURI();/////
        String url="jdbc:virtuoso://"+Configuration.getConfig("VIRTUOSO.IP")+":"+Configuration.getConfig("VIRTUOSO.PORT");
        String username=Configuration.getConfig("VIRTUOSO.USERNAME");
        String password=Configuration.getConfig("VIRTUOSO.PASSWORD");
        VirtGraph graph=new VirtGraph(storageName,url,username,password);
        if(graph.isEmpty())
            {
                Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null);
               //System.out.println("there have no content under the uri of subject");
            }
        else
           {
               // System.out.println("graph.getCount() = " + graph.getCount());  
                //VirtuosoQueryExecution vqe = 
                        VirtuosoQueryExecutionFactory.create (sparql, graph);

                Node predicateNode=Node.createURI(subject1);
                
                ExtendedIterator iter=graph.find(Node.ANY, predicateNode, Node.ANY);
                for(;iter.hasNext();)
                    {

                        Triple tr=(Triple)iter.next();
                        String subject=tr.getSubject().toString();
                        monitoringInformation.add(subject);
                        //System.out.println("subject="+subject);
                        String object=tr.getObject().toString();
                        monitoringInformation.add(object);
                        //System.out.println("object="+object);
                    }
          
        
                
        }
        	
        return monitoringInformation;
}
}


