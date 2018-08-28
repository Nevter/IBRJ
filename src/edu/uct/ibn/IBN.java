package edu.uct.ibn;

//InferencialBayesianNetwork

import edu.uct.ibn.gui.*;
import edu.uct.ibn.implication.*;
import edu.uct.ibn.util.*;
import edu.uct.ibn.cli.*;
import edu.uct.ibn.bayesnet.BNNode.Relationship;
import edu.uct.ibn.bayesnet.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Set;
import java.util.*;
import java.util.Iterator;

import edu.ksu.cis.bnj.bbn.inference.elimbel.*;
import edu.ksu.cis.bnj.bbn.inference.*;
import edu.ksu.cis.bnj.bbn.BBNGraph;
import edu.ksu.cis.bnj.bbn.BBNNode;
import edu.ksu.cis.bnj.bbn.BBNValue;
import edu.ksu.cis.bnj.bbn.BBNDiscreteValue;
import edu.ksu.cis.bnj.bbn.*;

/**
 * TODO:
 *  * remove all references to BNJ from anywhere other than 
 *    API classes (classes in bayesnet package)
 *  * Add Classes descriptors 
 *  * Add method descriptors 
 *  * Make GUI
 * 
 * REFACTOR TODO: 
 *  * Change all CLI methods to be just related to receiving input ect. and the actual
 *    classes to do the operations 
 *  * Fix the mess that is Implication
 */

public class IBN {

  public static void main(String[] args) {
    
    
    IBNCLI.run();
    
    //help();

    //an option for running it as a GUI

    //testEnvironment();
    
  }  

  public static void help(){

  }

  public static void testEnvironment(){

    BNGraph graph = new BNGraph("./examples/asia/asia.bif");
    //System.out.println(graph.getGraphDescription());
    Set nodes = graph.getGraphNodes();
    for (Iterator i=nodes.iterator(); i.hasNext(); ) {
      BNNode n = (BNNode) i.next();
      System.out.println(n);
    }
    System.out.println("~~~~~~~~~~");
    
    //ElimBel variableElimination = new ElimBel(graph.getBBNGraph());
    //io.output(variableElimination.getMarginals());
    
    BNNode lungCancer = graph.getNode("Cancer");
    BBNDiscreteValue lcV = (BBNDiscreteValue) lungCancer.getNode().getValues();
    io.output(lcV.toArray()[0]);
    
    
    BBNNode lc = lungCancer.getNode();
    BBNCPF cpf = lc.getCPF();
    io.output(cpf.getTable());
    io.output("\n\n");
    
    Hashtable re = new Hashtable();
    re.put("Smoking","NonSmoker");
    re.put("Cancer","Absent");
    cpf.remove(re);
    
    io.output(cpf.getTable());
    io.output("\n\n");
    
    cpf.put(re, new BBNConstant(0.99));
    
    io.output(cpf.getTable());
    io.output("\n\n");
    
    Enumeration v = cpf.getTable().elements();  
    while (v.hasMoreElements()){
      io.output(v.nextElement().getClass());
      io.output("\n");
    }
    Enumeration k = cpf.getTable().keys();
    while (k.hasMoreElements()){
      Hashtable ht = (Hashtable) k.nextElement();
      io.output(ht);
      io.output("\n");
      Enumeration k2 = ht.elements();
      while(k2.hasMoreElements()){
        io.output(k2.nextElement());
        io.output("\n");
        io.output(k2.nextElement().getClass());
        io.output("\n");
      }
      
      io.output("\n"); io.output("\n");
    }
    
    
    //variableElimination = new ElimBel(graph.getBBNGraph());
    //io.output(variableElimination.getMarginals());
  }

}
