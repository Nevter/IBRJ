package edu.uct.dbr;


import edu.uct.dbr.gui.*;
import edu.uct.dbr.implication.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;

import edu.ksu.cis.bnj.bbn.inference.elimbel.*;
import edu.ksu.cis.bnj.bbn.inference.*;
import edu.ksu.cis.bnj.bbn.BBNGraph;
import edu.ksu.cis.bnj.bbn.BBNNode;


public class DBR {

  private static BNGraph graph = null;

  public static void main(String[] args) {
    output("~~~~~~~~ Welcome to DBR ~~~~~~~~~~");
    output("\n~ A bayesian reasoner with logic ~\n");
    /*
    BNGraph graph = new BNGraph("./examples/asia/asia.bif");

    //System.out.println(graph);
    Set nodes = graph.getGraphNodes();
    for (Iterator i=nodes.iterator(); i.hasNext(); ) {
        BBNNode n = (BBNNode) i.next();
        System.out.println(n);
    }
    */
    dbr();

    output("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
  }


  private static void dbr(){

    output(menuText());
    Scanner inputScanner = new Scanner(System.in);
    String userInput = inputScanner.next().toLowerCase();
    while(true){
      switch(userInput){
        case "l":
        case "load":
          load();
          break;
        case "i":
        case "inference":
          if (graph != null) inference(); else output("\nNo network loaded\n");
          break;
        case "a":
        case "add":
          if (graph != null) addImplicationStatement(); else output("\nNo network loaded\n");
          break;
        case "im":
        case "implication":
          if (graph != null) viewImplicationStatements(); else output("\nNo network loaded\n");
          break;
        case "o":
        case "observe":
          if (graph != null) output("This does nothing for now"); else output("\nNo network loaded\n");
          break;
        case "c":
        case "cpt":
          if (graph != null) viewCPT(); else output("\nNo network loaded\n");
          break;
        case "g":
        case "graph":
          if (graph != null) viewGraph(); else output("\nNo network loaded\n");
          break;
        case "q":
        case "quit":
          return;
        default:
          output("\nUser input not recognised");
      }

      output(menuText());
      userInput = inputScanner.next().toLowerCase();
    }

  }

  private static void load(){
    Scanner inputScanner = new Scanner(System.in);
    output("\nPlease enter the path to the file to load\n>");
    String userInput = inputScanner.next();

    String filepath = "";

    if (userInput.equals("a")) filepath = "./examples/asia/asia.bif";
    else filepath = userInput;

    try{
      graph = new BNGraph(filepath);
    } catch(Throwable e){
      output("\nSomething went wrong with opening that file. Is the path correct?");
    }
  }

  private static void inference(){
    BNInference inferencer = new BNInference(graph);
    InferenceResult result = inferencer.getMarginals();
    output(result.toString());
  }

  private static void addImplicationStatement(){
    output("\n(c)lassical or (d)efeasible implication?\n>");
    Scanner inputScanner = new Scanner(System.in);
    String userInput = inputScanner.next();
    Implication impl = null;

    Set nodes = graph.getGraphNodes();
    for (Iterator i=nodes.iterator(); i.hasNext(); ) {
        BNNode n = (BNNode) i.next();
        System.out.println(n);
    }

    if (userInput.equals("c")){
      output("\nEnter antecedent node name\n>");
      String antecedentName = inputScanner.next();
      output("\nEnter consequent node name\n>");
      String consequentName = inputScanner.next();
      BNNode antecedentNode = graph.getNode(antecedentName);
      BNNode consequentNode = graph.getNode(consequentName);

      if (antecedentNode != null && consequentNode != null){
        impl = new ClassicalImplication(antecedentNode, consequentNode);
      }
      else {output("Cannot find nodes with those names");}
    }
    else if (userInput.equals("d")){
      output("\nEnter antecedent node name\n>");
      String antecedentName = inputScanner.next();
      output("\nEnter consequent node name\n>");
      String consequentName = inputScanner.next();
      BNNode antecedentNode = graph.getNode(antecedentName);
      BNNode consequentNode = graph.getNode(consequentName);

      if (antecedentNode != null && consequentNode != null){
        impl = new DefeasibleImplication(antecedentNode, consequentNode);
      }
      else {output("Cannot find nodes with those names");}
    }
    if (impl != null) graph.addImplicationStatement(impl);
  }

  private static void viewImplicationStatements(){
    ArrayList<Implication> implicationStatements = graph.getImplicationStatements();
    for (Implication impl : implicationStatements){
      output(impl+"\n");
    }
  }

  private static void viewCPT(){
    CPTDisplay cptView = new CPTDisplay(graph);
  }

  private static void viewGraph(){
    GraphDisplay graphView = new GraphDisplay(graph);
  }

  private static String menuText(){
    String networkName = (graph == null) ? "None" : graph.getName();
    String menuText = "\n~DBR Menu~"+
                      "\nNetwork loaded: "+networkName+
                      "\n(l)oad a BN, draw (i)nference, (a)dd implication, (o)bserve, "+
                      "\nview (im)plication, view (C)PT, view (g)raph, (q)uit"+
                      "\n>";
    return menuText;
  }

  private static void output(Object output){
    System.out.print(output);
  }

}
