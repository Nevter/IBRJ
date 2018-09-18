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
import java.util.Hashtable;

public class testIBRJIntegration {
    BNGraph graph = null;
    BNNode tbca = null;
    BNNode dyspnea = null;

    @Before 
    public void init(){
        //create a graph
        graph = new BNGraph("./examples/asia/asia.bif");
        tbca = graph.getNode("TbOrCa");
        dyspnea = graph.getNode("Dyspnea");
    }

    @Test 
    public void testNoImplication(){
        
        String expected = "----------------------------\n"+
        "[VisitAsia = NoVisit] = 0.99\n"+
        "[VisitAsia = Visit] = 0.01\n"+
        "[Smoking = NonSmoker] = 0.5\n"+
        "[Smoking = Smoker] = 0.5\n"+
        "[Dyspnea = Present] = 0.43597060000000004\n"+
        "[Dyspnea = Absent] = 0.5640294\n"+
        "[TbOrCa = False] = 0.935172\n"+
        "[TbOrCa = True] = 0.06482800000000002\n"+
        "[Tuberculosis = Present] = 0.010400000000000003\n"+
        "[Tuberculosis = Absent] = 0.9896\n"+
        "[XRay = Normal] = 0.88970996\n"+
        "[XRay = Abnormal] = 0.11029004000000002\n"+
        "[Cancer = Present] = 0.055000000000000014\n"+
        "[Cancer = Absent] = 0.945\n"+
        "[Bronchitis = Present] = 0.45\n"+
        "[Bronchitis = Absent] = 0.55\n"+
        "----------------------------";

        String e[] = expected.split("\n");
        Set<String> expectedLines = new HashSet<String>();
        for (String l : e) {
            expectedLines.add(l);
        }

        String result = BNInference.getMarginalsOutput(graph);
        String r[] = result.split("\n");
        Set<String> resultLines = new HashSet<String>();
        for (String l : r) {
            resultLines.add(l);
        }

        assertEquals(resultLines,expectedLines);
    }

    @Test 
    public void testImplication(){
        graph.addImplicationStatement(new ClassicalImplication(tbca,dyspnea,graph));
        String expected = "----------------------------\n"+
        "[VisitAsia = NoVisit] = 0.99\n"+
        "[Smoking = NonSmoker] = 0.5\n"+
        "[VisitAsia = Visit] = 0.01\n"+
        "[Smoking = Smoker] = 0.5\n"+
        "[Dyspnea = Present] = 0.44824852\n"+
        "[Dyspnea = Absent] = 0.5517514800000001\n"+
        "[TbOrCa = False] = 0.935172\n"+
        "[TbOrCa = True] = 0.06482800000000002\n"+
        "[Tuberculosis = Present] = 0.010400000000000003\n"+
        "[XRay = Normal] = 0.88970996\n"+
        "[Tuberculosis = Absent] = 0.9896\n"+
        "[XRay = Abnormal] = 0.11029004000000002\n"+
        "[Cancer = Present] = 0.055000000000000014\n"+
        "[Cancer = Absent] = 0.945\n"+
        "[Bronchitis = Present] = 0.45\n"+
        "[Bronchitis = Absent] = 0.55\n"+
        "----------------------------";

        String e[] = expected.split("\n");
        Set<String> expectedLines = new HashSet<String>();
        for (String l : e) {
            expectedLines.add(l);
        }

        String result = BNInference.getMarginalsOutput(graph);
        String r[] = result.split("\n");
        Set<String> resultLines = new HashSet<String>();
        for (String l : r) {
            resultLines.add(l);
        }

        assertEquals(resultLines,expectedLines);
    }

    @Test 
    public void testImplicationObservation(){
        graph.addImplicationStatement(new ClassicalImplication(tbca,dyspnea,graph));
        graph.observe(tbca, "True");
        String expected = "----------------------------\n"+
        "[VisitAsia = NoVisit] = 0.9842274942925897\n"+
        "[VisitAsia = Visit] = 0.01577250570741038\n"+
        "[Smoking = NonSmoker] = 0.1565372986980934\n"+
        "[Smoking = Smoker] = 0.8434627013019066\n"+
        "[Dyspnea = Present] = 1.0\n"+
        "[Dyspnea = Absent] = 0.0\n"+
        "[TbOrCa = False] = 0.0\n"+
        "[TbOrCa = True] = 1.0\n"+
        "[Tuberculosis = Present] = 0.16042450792867283\n"+
        "[Tuberculosis = Absent] = 0.8395754920713272\n"+
        "[XRay = Abnormal] = 0.98\n"+
        "[XRay = Normal] = 0.02\n"+
        "[Cancer = Present] = 0.8483988400074042\n"+
        "[Cancer = Absent] = 0.15160115999259577\n"+
        "[Bronchitis = Present] = 0.553038810390572\n"+
        "[Bronchitis = Absent] = 0.44696118960942804\n"+
        "----------------------------";

        String e[] = expected.split("\n");
        Set<String> expectedLines = new HashSet<String>();
        for (String l : e) {
            expectedLines.add(l);
        }

        String result = BNInference.getMarginalsOutput(graph);
        String r[] = result.split("\n");
        Set<String> resultLines = new HashSet<String>();
        for (String l : r) {
            resultLines.add(l);
        }

        assertEquals(resultLines,expectedLines);
    }
    
}