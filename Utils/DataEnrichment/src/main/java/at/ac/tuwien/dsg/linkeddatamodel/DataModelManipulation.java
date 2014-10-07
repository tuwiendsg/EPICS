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
import java.util.LinkedList;
import java.util.StringTokenizer;
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
        
        LinkedList<String> monitoringInformation=new LinkedList<String>();
        String storageName=new StringTokenizer(subject1,"#").nextToken();
        
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
        }
        	
        return monitoringInformation;
}
}
