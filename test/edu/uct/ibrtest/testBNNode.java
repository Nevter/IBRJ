package edu.uct.ibrtest;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.JUnitCore;
import static org.junit.Assert.*;

import edu.uct.ibr.bayesnet.*;
import edu.uct.ibr.implication.*;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class testBNNode {

    BNGraph graph = null;
    BNNode visit = null;
    BNNode tb = null;
    BNNode smoke = null;
    BNNode cancer = null;
    BNNode bronch = null;
    BNNode xray = null;
    BNNode tbca = null;
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
        xray = graph.getNode("XRay");
        tbca = graph.getNode("TbOrCa");
        dyspnea = graph.getNode("Dyspnea");
    }

    @Test 
    public void testGetters(){
        assertEquals("Smoking",smoke.getName());
        assertEquals("[Smoker, NonSmoker]",smoke.getPossibleValuesOutput());
        assertFalse(smoke.isObserved());
        smoke.observe("Smoker");
        assertEquals("Smoker",smoke.getObservedValue());

       

    }

    @Test 
    public void testGetRelatives(){
        Set<BNNode> smokerChildren = new HashSet<BNNode>();
        smokerChildren.add(cancer);
        smokerChildren.add(bronch);

        assertEquals(smokerChildren, smoke.getChildren());

        Set<BNNode> tbcaParents = new HashSet<BNNode>();
        tbcaParents.add(cancer);
        tbcaParents.add(tb);

        assertEquals(tbcaParents, tbca.getParents());

        Set<BNNode> smokerDescend = new HashSet<BNNode>();
        smokerDescend.add(tbca);
        smokerDescend.add(dyspnea);
        smokerDescend.add(xray);

        assertEquals(smokerDescend, smoke.getDescendants());

        Set<BNNode> xrayAnscestors = new HashSet<BNNode>();
        xrayAnscestors.add(visit);
        xrayAnscestors.add(tb);
        xrayAnscestors.add(cancer);
        xrayAnscestors.add(smoke);

        assertEquals(xrayAnscestors, xray.getAncestors());
    }

    @Test 
    public void testEquals(){
        assertTrue(smoke.equals(smoke));
        assertFalse(smoke.equals(xray));
    }

}