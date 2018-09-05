package edu.uct.ibn;


import edu.uct.ibn.cli.IBNCLI;

//THE FOLLOWING CAN BE REMOVED WHEN 'TEST ENVIRONMENT' IS REMOVED
import edu.uct.ibn.gui.*;
import edu.uct.ibn.implication.*;
import edu.uct.ibn.util.*;
import edu.uct.ibn.bayesnet.*;
import edu.uct.ibn.bayesnet.BNNode.Relationship;

import java.util.*;

import edu.ksu.cis.bnj.bbn.inference.elimbel.*;
import edu.ksu.cis.bnj.bbn.inference.*;
import edu.ksu.cis.bnj.bbn.*;

/**
 * TODO:
 *  * remove all references to BNJ from anywhere other than 
 *    API classes (classes in bayesnet package) - ClassicalImplication
 *  * Add Classes descriptors 
 *  * Add method descriptors 
 * 
 *  * Make GUI
 *  * Write tests
 *
 */

public class IBN {

  public static void main(String[] args) {
    if (args.length > 0){
      if (args[0].equals("-cli")) IBNCLI.run();
      else if (args[0].equals("-gui")) {}//run the gui
      else help();
    }
    
    //IBNCLI.run();
    //testEnvironment();
    IBNGUI.main(new String[0]);
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
    

    BNNode asia = graph.getNode("VisitAsia");
    BNNode cancer = graph.getNode("Cancer");

    io.output(cancer.toVerboseString());

    graph.addEdge(asia, cancer);
    graph.addImplicationStatement(new ClassicalImplication(asia, cancer, graph));

    io.output("\n\n~~~~~\n"+cancer.toVerboseString());
    
    /*
    variableElimination = new ElimBel(graph.getBBNGraph());
    io.output(variableElimination.getMarginals());

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
      */
    
    //variableElimination = new ElimBel(graph.getBBNGraph());
    //io.output(variableElimination.getMarginals());
  }

}
