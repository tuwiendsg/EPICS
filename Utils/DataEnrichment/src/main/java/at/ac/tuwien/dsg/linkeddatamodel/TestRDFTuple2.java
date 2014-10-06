/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.linkeddatamodel;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.mem.ModelMem;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.io.File;
import java.io.FileReader;
import virtuoso.jena.driver.VirtGraph;

/**
 *
 * @author dsg
 */
public class TestRDFTuple2 {
    public void GraphStore(String fileName)throws Exception 
    {
        File f=new File("./example/BuildingBTest.rdf");
        FileReader fr=new FileReader(f);
        Model model=new ModelMem();
        
        model.read(fr,RDFS.getURI());
        StmtIterator iter=model.listStatements();
        Statement stmt;
        Property predicate;
        RDFNode obj;
        Resource subj;
        String url="jdbc:virtuoso://localhost:1111";
        VirtGraph virtgraph=new VirtGraph("test1",url,"dba","dba");
        virtgraph.clear();
        //virtgraph.add(graph);
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
