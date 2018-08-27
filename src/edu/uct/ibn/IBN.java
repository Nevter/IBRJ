package edu.uct.ibn;


import edu.uct.ibn.BNNode.Relationship;

//InferencialBayesianNetwork

import edu.uct.ibn.gui.*;
import edu.uct.ibn.implication.*;
import edu.uct.ibn.util.*;
import edu.uct.ibn.BNNode.Relationship;

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
 *  * remove all references to BNJ from anywhere other than API classes
 *  * Add Classes descriptors 
 *  * Add method descriptors 
 */

public class IBN {

  private static BNGraph graph = null;
  
  public static void main(String[] args) {
    io.output("~~~~~~~~ Welcome to IBN ~~~~~~~~~~");
    io.output("\n~ A bayesian reasoner with logic ~\n");
    
    /*
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
    */
    
    
    //variableElimination = new ElimBel(graph.getBBNGraph());
    //io.output(variableElimination.getMarginals());
    
    
    /*
    */
    ibn();
    
    io.output("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
  }


  private static void ibn(){

    io.output(menuText());

    String userInput = io.input().toLowerCase();
    while(true){
      switch(userInput){
        case "l":
        case "load":
          load();
          break;
        case "i":
        case "inference":
          if (graph != null) inference(); else io.output("\nNo network loaded\n");
          break;
        case "a":
        case "add":
          if (graph != null) addImplicationStatement(); else io.output("\nNo network loaded\n");
          break;
        case "im":
        case "implication":
          if (graph != null) viewImplicationStatements(); else io.output("\nNo network loaded\n");
          break;
        case "o":
        case "observe":
          if (graph != null) observeNode(); else io.output("\nNo network loaded\n");
          break;
        case "c":
        case "cpt":
          if (graph != null) viewCPT(); else io.output("\nNo network loaded\n");
          break;
        case "g":
        case "graph":
          if (graph != null) viewGraph(); else io.output("\nNo network loaded\n");
          break;
        case "q":
        case "quit":
          return;
        default:
          io.output("\nUser input not recognised");
      }

      io.output(menuText());
      userInput = io.input().toLowerCase();
    }

  }


  private static void observeNode(){
    io.output("\nEnter node name\n>");
    String observationNodeName = io.input();
    BNNode observationNode = graph.getNode(observationNodeName);
    if (observationNode == null){
      io.output("Cannot find node with that name");
      return;
    }
    io.output(observationNode.getPossibleValues());
    io.output("\nEnter observed value:\n>");
    String valueName = io.rawInput();
    observationNode.observe(valueName);

  }

  private static void load(){

    io.output("\nPlease enter the path to the file to load\n>");
    String userInput = io.input();

    String filePath = "";

    if (userInput.equals("a")) filePath = "./examples/asia/asia.bif";
    else filePath = userInput;

    try{
      graph = new BNGraph(filePath);
    } catch(Throwable e){
      io.output("\nSomething went wrong with opening that file. Is the path correct?");
    }
  }

  private static void inference(){
    //BNInference inferencer = new BNInference();
    ArrayList<InferenceResult> results = BNInference.getMarginals(graph);
    io.output(results);
  }

  private static void addImplicationStatement(){
    io.output("\n(c)lassical or (d)efeasible implication?\n>");

    String userInput = io.input();

    Implication impl = null;

    Set nodes = graph.getGraphNodes();
    for (Iterator i=nodes.iterator(); i.hasNext(); ) {
        BNNode n = (BNNode) i.next();
        io.output(n);
    }

      io.output("\nEnter antecedent node name\n>");
      String antecedentName = io.input();
      io.output("\nEnter consequent node name\n>");
      String consequentName = io.input();
      BNNode antecedentNode = graph.getNode(antecedentName);
      BNNode consequentNode = graph.getNode(consequentName);

    if (antecedentNode == null && consequentNode == null) {
      io.output("Cannot find nodes with those names");
      return;
    }

    Relationship relationship = graph.getRelationship(antecedentNode, consequentNode);

    if (relationship == Relationship.SELF){
      io.output("Cannot add implication statement to same node");
      return;
    }

    if (userInput.equals("c")){
      impl = new ClassicalImplication(antecedentNode, consequentNode, relationship);
      }
    else if (userInput.equals("d")){
      impl = new DefeasibleImplication(antecedentNode, consequentNode, relationship);
    }
    graph.addImplicationStatement(impl);
  }

  private static void viewImplicationStatements(){
    ArrayList<Implication> implicationStatements = graph.getKnowledgebase();
    for (Implication impl : implicationStatements){
      io.output(impl+"\n");
    }
  }

  private static void viewCPT(){
    CPTDisplay cptView = new CPTDisplay(graph);
    Set nodes = graph.getGraphNodes();
    for (Iterator i=nodes.iterator(); i.hasNext(); ) {
        BNNode n = (BNNode) i.next();
        io.output(n.toVerboseString());
    }
  }

  private static void viewGraph(){
    GraphDisplay graphView = new GraphDisplay(graph);
    io.output(graph.getGraphDescription());
  }

  private static String menuText(){
    String networkName = (graph == null) ? "None" : graph.getName();
    return message.menuText(networkName);
  }

}
