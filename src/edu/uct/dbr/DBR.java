package edu.uct.dbr;


import edu.uct.dbr.gui.*;
import edu.uct.dbr.implication.*;

import java.util.Scanner;
import java.util.ArrayList;

import edu.ksu.cis.bnj.bbn.inference.elimbel.*;
import edu.ksu.cis.bnj.bbn.inference.*;
import edu.ksu.cis.bnj.bbn.BBNGraph;


public class DBR {

  private static BNGraph graph = null;

  public static void main(String[] args) {
    output("~~~~~~~~ Welcome to DBR ~~~~~~~~~~");
    output("\n~ A bayesian reasoner with logic ~\n");

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
    Implication impl = null
    if (userInput.equals("c")){
      impl = new ClassicalImplication(graph);
    }
    else if (userInput.equals("d")){
      impl = new DefeasibleImplication(graph);
    }
    if (impl != null) graph.addImplicationStatement(impl);
  }

  private static void viewImplicationStatements(){
    ArrayList<Implication> implicationStatements = graph.getImplicationStatements();
    for (Implication impl : implicationStatements){
      output(impl);
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
                      "\n(l)oad a BN, draw (i)nference, (a)dd implication,"+
                      "\nview (im)plication, view (C)PT, view (g)raph, (q)uit"+
                      "\n>";
    return menuText;
  }

  private static void output(Object output){
    System.out.print(output);
  }

}
