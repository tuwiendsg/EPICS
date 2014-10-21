/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.app.graphstorage;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.mem.ModelMem;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDFS;
//import com.mkyong.app.OperateProperty;
import java.io.File;
import java.io.FileReader;
import virtuoso.jena.driver.VirtGraph;

/**
 *
 * @author Anindita
 */
public class RDFGraphStorage {
    public void GraphStore(String fileName,String fileURI)throws Exception 
    {
        
        //consider the rdf file
        File rdfFileName=new File(fileName);
        FileReader rdfFileRead=new FileReader(rdfFileName);
        
        Model model=new ModelMem();
        model.read(rdfFileRead,RDFS.getURI());
        
        StmtIterator iter=model.listStatements();
        Statement stmt;
        
        
        OperateProperty operateProperty=new OperateProperty();
        String url=operateProperty.getGraphStorageURI();
        String username=operateProperty.getGraphStorageUserName();
        String password=operateProperty.getGraphStoragePassword();
        
       /*String url="jdbc:virtuoso://"+Configuration.getConfig("VIRTUOSO.IP")+":"+Configuration.getConfig("VIRTUOSO.PORT");
        String username=Configuration.getConfig("VIRTUOSO.USERNAME");
        String password=Configuration.getConfig("VIRTUOSO.PASSWORD");*/
        VirtGraph virtgraph=new VirtGraph(fileURI,url,username,password);
         
       
        
        while (iter.hasNext())
        {
            stmt=(Statement)iter.next();
            System.out.println("subject="+stmt.getSubject().getURI());
            System.out.println("Predicate="+stmt.getPredicate().getLocalName());
            System.out.println("object="+stmt.getObject().toString());
            virtgraph.add(new Triple(Node.createURI(stmt.getSubject().getURI())
                    ,Node.createURI(stmt.getPredicate().getLocalName())
                    ,Node.createLiteral(stmt.getObject().toString())));
        }
        
    }
}
