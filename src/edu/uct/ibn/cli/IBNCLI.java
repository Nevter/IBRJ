package edu.uct.ibn.cli;


import edu.uct.ibn.gui.*;
import edu.uct.ibn.implication.*;
import edu.uct.ibn.util.*;
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

public class IBNCLI {

    private static BNGraph graph = null;

    public static void run(){
        io.output("~~~~~~~~ Welcome to IBN CLI ~~~~~~~~~~");
        io.output("\n~~~ A bayesian reasoner with logic ~~~\n");

        ibn();
     
        io.output("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }


    private static void ibn(){
    
        String userInput = io.input(menuText());
        
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
    
          userInput = io.input(menuText());
        }
    
    }

    /**
     * Load a graph from a given input file
     */
    private static void load(){
      String userInput = io.input("\nEnter the path to the file to load");
      
      String filePath = "";
      
      if (userInput.equals("a")) filePath = "./examples/asia/asia.bif";
      else filePath = userInput;
      
      try{
        graph = new BNGraph(filePath);
      } catch(Throwable e){
        io.output("\nSomething went wrong with opening that file. Is the path correct?");
      }
    }
    


    private static void observeNode(){
      String observationNodeName = io.input("\nEnter node name");
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


    private static void inference(){
      //BNInference inferencer = new BNInference();
      ArrayList<InferenceResult> results = BNInference.getMarginals(graph);
      io.outputInfResults(results);
    }
  
    private static void addImplicationStatement(){    
      
      String userInput = io.input("\n(c)lassical or (d)efeasible implication?");
  
      Implication impl = null;
  
      io.output(graph.getNodeNames());
  
      String antecedentName = io.input("\nEnter antecedent node name");
      String consequentName = io.input("\nEnter consequent node name");
      
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
  
    /**
     * View a text description of the currently loaded graph's nodes CPTs. 
     */
    private static void viewCPT(){
      io.output(graph.getNodeDescriptions());
    }
  
    /**
     * View a text description of the currently loaded graph. 
     */
    private static void viewGraph(){
      io.output(graph.getGraphDescription());
    }
  
    /**
     * Get the correct menu text to display 
     */
    private static String menuText(){
      String networkName = (graph == null) ? "None" : graph.getName();
      return message.menuText(networkName);
    }
    

}