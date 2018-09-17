package edu.uct.ibrtest;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.JUnitCore;
import static org.junit.Assert.*;

import edu.uct.ibr.util.*;
import edu.uct.ibr.bayesnet.*;
import edu.uct.ibr.implication.*;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class testIBRUtil {

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

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Entailment.java ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test 
    public void testEntailmentNoObservations() {
        ArrayList<Implication> kb = new ArrayList<Implication>();
        kb.add(new ClassicalImplication(visit,tb,graph)); 
        kb.add(new ClassicalImplication(smoke,cancer,graph));    
        kb.add(new ClassicalImplication(xray,dyspnea,graph));
    
        ArrayList<Implication> obs = new ArrayList<Implication>();

        boolean entails = Entailment.entails(kb, obs);
        assertTrue(entails);
    }

    @Test 
    public void testEntailmentNoKnowledgebase() {
        ArrayList<Implication> kb = new ArrayList<Implication>();
    
        ArrayList<Implication> obs = new ArrayList<Implication>();
        obs.add(new ClassicalImplication(visit,tb,graph)); 

        boolean entails = Entailment.entails(kb, obs);
        assertTrue(entails);
    }

    @Test 
    public void testEntailmentEntailedKB() {
        ArrayList<Implication> kb = new ArrayList<Implication>();
        kb.add(new ClassicalImplication(visit,tb,graph)); 
        kb.add(new ClassicalImplication(smoke,cancer,graph));    
        kb.add(new ClassicalImplication(xray,dyspnea,graph));
    
        ArrayList<Implication> obs = new ArrayList<Implication>();
        obs.add(new ClassicalImplication(visit,tb,graph)); 
        obs.add(new ClassicalImplication(bronch,tbca,graph)); 

        boolean entails = Entailment.entails(kb, obs);
        assertTrue(entails);
    }

    @Test 
    public void testEntailmentNotEntailedKB() {
        ArrayList<Implication> kb = new ArrayList<Implication>();
        kb.add(new ClassicalImplication(visit,tb,graph)); 
        kb.add(new ClassicalImplication(smoke,cancer,graph));    
        kb.add(new ClassicalImplication(xray,dyspnea,graph));
    
        ArrayList<Implication> obs = new ArrayList<Implication>();
        obs.add(new ClassicalImplication(tb,visit,graph)); 
        obs.add(new ClassicalImplication(bronch,tbca,graph)); 

        boolean entails = Entailment.entails(kb, obs);
        assertFalse(entails);
    }

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ImplicationUtil.java ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test 
    public void testhasClassicalCycle(){
        ArrayList<Implication> kb = new ArrayList<Implication>();
        kb.add(new ClassicalImplication(visit,tb,graph)); 
        kb.add(new ClassicalImplication(smoke,cancer,graph));    
        kb.add(new ClassicalImplication(xray,dyspnea,graph));

        boolean noCycle = ImplicationUtil.hasClassicalCycle(kb);
        assertFalse(noCycle);

        kb.add(new ClassicalImplication(tb,cancer,graph));
        ClassicalImplication cancerToVisitAsia = new ClassicalImplication(cancer,visit,graph);
        boolean cycle = ImplicationUtil.hasClassicalCycle(kb, cancerToVisitAsia);
        assertTrue(cycle);
        
        kb.add(cancerToVisitAsia);
        cycle = ImplicationUtil.hasClassicalCycle(kb);
        assertTrue(cycle);

    }

    @Test 
    public void testgetConnectedClassicalImplications() {
        ArrayList<Implication> kb = new ArrayList<Implication>();
        kb.add(new ClassicalImplication(visit,tb,graph)); 
        kb.add(new ClassicalImplication(smoke,cancer,graph));    
        kb.add(new ClassicalImplication(xray,dyspnea,graph));
        kb.add(new ClassicalImplication(tb,cancer,graph));
        kb.add(new ClassicalImplication(cancer,visit,graph));

        Set<String> resultConnected = ImplicationUtil.getConnectedClassicalImplications(kb);

        Set<String> connected = new HashSet<String>();
        connected.add("VisitAsia");
        connected.add("Tuberculosis");
        connected.add("Cancer");

        assertEquals(connected, resultConnected);
        
        connected.add("XRay");

        assertNotSame(connected, resultConnected);

    }

    

}