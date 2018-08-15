package edu.uct.dbr;


import edu.uct.dbr.gui.*;
import edu.uct.dbr.implication.*;
import edu.uct.dbr.util.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.Iterator;

import edu.ksu.cis.bnj.bbn.inference.elimbel.*;
import edu.ksu.cis.bnj.bbn.inference.*;
import edu.ksu.cis.bnj.bbn.BBNGraph;
import edu.ksu.cis.bnj.bbn.BBNNode;


public class DBR {

  private static BNGraph graph = null;

  public static void main(String[] args) {
    io.output("~~~~~~~~ Welcome to DBR ~~~~~~~~~~");
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
    BNNode t = graph.getNode("Tuberculosis");
    BNNode x = graph.getNode("Smoking");
    System.out.println("Relation: "+graph.getRelationship(x, t));

    */
    dbr();

    io.output("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
  }


  private static void dbr(){

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
          if (graph != null) io.output("This does nothing for now"); else io.output("\nNo network loaded\n");
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

  private static void load(){

    io.output("\nPlease enter the path to the file to load\n>");
    String userInput = io.input();

    String filepath = "";

    if (userInput.equals("a")) filepath = "./examples/asia/asia.bif";
    else filepath = userInput;

    try{
      graph = new BNGraph(filepath);
    } catch(Throwable e){
      io.output("\nSomething went wrong with opening that file. Is the path correct?");
    }
  }

  private static void inference(){
    BNInference inferencer = new BNInference(graph);
    InferenceResult result = inferencer.getMarginals();
    io.output(result.toString());
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

    if (userInput.equals("c")){
      io.output("\nEnter antecedent node name\n>");
      String antecedentName = io.input();
      io.output("\nEnter consequent node name\n>");
      String consequentName = io.input();
      BNNode antecedentNode = graph.getNode(antecedentName);
      BNNode consequentNode = graph.getNode(consequentName);

      if (antecedentNode != null && consequentNode != null){
        impl = new ClassicalImplication(antecedentNode, consequentNode);
      }
      else {io.output("Cannot find nodes with those names");}
    }
    else if (userInput.equals("d")){
      io.output("\nEnter antecedent node name\n>");
      String antecedentName = io.input();
      io.output("\nEnter consequent node name\n>");
      String consequentName = io.input();
      BNNode antecedentNode = graph.getNode(antecedentName);
      BNNode consequentNode = graph.getNode(consequentName);

      if (antecedentNode != null && consequentNode != null){
        impl = new DefeasibleImplication(antecedentNode, consequentNode);
      }
      else {io.output("Cannot find nodes with those names");}
    }
    if (impl != null) graph.addImplicationStatement(impl);
  }

  private static void viewImplicationStatements(){
    ArrayList<Implication> implicationStatements = graph.getImplicationStatements();
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
