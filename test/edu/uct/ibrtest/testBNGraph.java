package edu.uct.ibrtest;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.JUnitCore;
import static org.junit.Assert.*;

import edu.uct.ibr.bayesnet.*;
import edu.uct.ibr.bayesnet.BNNode.Relationship;
import edu.uct.ibr.implication.*;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class testBNGraph {

    BNGraph graph = null;
    BNNode visit = null;
    BNNode tb = null;
    BNNode smoke = null;
    BNNode cancer = null;
    BNNode bronch = null;
    BNNode tbca = null;
    BNNode xray = null;
    BNNode dyspnea = null;

    @Before 
    public void init(){
        //create a graph
        graph = new BNGraph("./examples/asia/asia.bif");
        visit = graph.getNode("VisitAsia");
        tb = graph.getNode("Tuberculosis");
        smoke = graph.getNode("Smoking");
        cancer = graph.getNode("Cancer");
        bronch = graph.getNode("Bronchitis");
        tbca = graph.getNode("TbOrCa");
        xray = graph.getNode("XRay");
        dyspnea = graph.getNode("Dyspnea");
    }

    @Test 
    public void testAddEdge(){
        //add edge between VisitAsia and XRay
        Set<BNNode> visitChildren = new HashSet<BNNode>();
        visitChildren.add(tb);

        assertEquals(visitChildren,visit.getChildren());

        graph.addEdge(visit, xray);
        visitChildren.add(xray);
        assertEquals(visitChildren,visit.getChildren());
    }

    @Test 
    public void testGetGraphNodes(){
        Set<BNNode> nodes = new HashSet<BNNode>();
        nodes.add(visit);
        nodes.add(tb);
        nodes.add(smoke);
        nodes.add(cancer);
        nodes.add(bronch);
        nodes.add(tbca);
        nodes.add(xray);
        nodes.add(dyspnea);
        assertEquals(nodes,graph.getGraphNodes());
    }

    @Test 
    public void testRelationships(){
        assertEquals(Relationship.ANCESTOR,graph.getRelationship(visit, xray));
        assertEquals(Relationship.DESCENDANT,graph.getRelationship(xray, visit));
        assertEquals(Relationship.NONE,graph.getRelationship(visit, smoke));
        assertEquals(Relationship.SELF,graph.getRelationship(visit, visit));
        assertEquals(Relationship.PARENT,graph.getRelationship(visit, tb));
        assertEquals(Relationship.CHILD,graph.getRelationship(cancer, smoke));
    }

    @Test 
    public void testAddImplication(){
        assertFalse(graph.addImplicationStatement(new ClassicalImplication(tbca,visit,graph)));
        assertTrue(graph.addImplicationStatement(new ClassicalImplication(smoke,visit,graph)));
        ArrayList<Implication> kb = new ArrayList<Implication>();
        kb.add(new ClassicalImplication(smoke,visit,graph));
        
        assertEquals(kb.toString(),graph.getKnowledgebase().toString());
    }
    
    @Test 
    public void testObserve(){
        graph.observe(smoke, "Smoker");
        assertEquals("Smoker",smoke.getObservedValue());
    }
    
    
    @Test 
    public void testCollapseNode(){
        int sizePreCycle = graph.getGraphNodes().size();
        graph.addImplicationStatement(new ClassicalImplication(tb,tbca,graph));
        graph.addImplicationStatement(new ClassicalImplication(tbca,cancer,graph));
        graph.addImplicationStatement(new ClassicalImplication(cancer,tb,graph));

        int sizePostCycle = graph.getGraphNodes().size();
        
        assertSame(sizePreCycle-2,sizePostCycle);
    }

}