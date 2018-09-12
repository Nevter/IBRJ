package edu.uct.ibr;


import edu.uct.ibr.cli.IBRCLI;

//THE FOLLOWING CAN BE REMOVED WHEN 'TEST ENVIRONMENT' IS REMOVED
import edu.uct.ibr.gui.*;
import edu.uct.ibr.implication.*;
import edu.uct.ibr.util.*;
import edu.uct.ibr.bayesnet.*;
import edu.uct.ibr.bayesnet.BNNode.Relationship;

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
 * 
 * 
 *  * Write tests
 * 
 *  * Write algo that checks for cycles 
 *  * add in cycle checking functionality
 *  * if a cycle is found, bounce the user 
 *  * if a impl cycle is found, do crazy node manipulation shiat
 *  
 *  * add entailment checking for defeasible stuff 
 *  * etc. 
 *
 */

public class IBR {

  public static void main(String[] args) {
    if (args.length > 0){
      if (args[0].equals("-cli")) IBRCLI.run();
      else if (args[0].equals("-gui")) {}//run the gui
      else help();
    }
    
    //IBRGUI.main(new String[0]);
    //IBRCLI.run();
    testEnvironment();
  }  

  public static void help(){
    System.out.println("There will be some help info here at some point");
  }

  public static void testEnvironment(){

    BNGraph graph = new BNGraph("./examples/asia/asia.bif");
    BNNode a = graph.getNode("VisitAsia");
    BNNode b = graph.getNode("XRay");
    
    ArrayList<Implication> kb = new ArrayList<Implication>();
    ArrayList<Implication> obs = new ArrayList<Implication>();
    kb.add(new ClassicalImplication(a,b,graph));

    System.out.println(Entailment.entails(kb, obs));

  
    /*
    System.out.println(GraphUtil.hasCycles(graph));
    graph.addEdge(b, a);
    //System.out.println(GraphUtil.hasCycles(graph));

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
