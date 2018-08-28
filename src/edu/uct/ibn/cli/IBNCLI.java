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


public class IBNCLI {

  private static BNGraph graph = null;

  /**
   * Run the IBN Command Line Interface
   */
  public static void run(){
      io.output(message.CLI_HEADER);

      ibn();
    
      io.output(message.LINE);
  }

  /**
   * The main loop that controls displays and controls user input on the Command line
   */
  private static void ibn(){
    while(true){
      
      String userInput = io.input(menuText());
      
      if (userInput.equals("l") || userInput.equals("load")){
        load();
      }
      else if (userInput.equals("i") || userInput.equals("inference")){
        if (graph != null) inference(); else io.output(message.ERROR_NO_GRAPH);
      }
      else if (userInput.equals("a") || userInput.equals("add")){
        if (graph != null) addImplicationStatement(); else io.output(message.ERROR_NO_GRAPH);
      }
      else if (userInput.equals("im") || userInput.equals("implication")){
        if (graph != null) viewImplicationStatements(); else io.output(message.ERROR_NO_GRAPH);
      }
      else if (userInput.equals("o") || userInput.equals("observe")){
        if (graph != null) observeNode(); else io.output(message.ERROR_NO_GRAPH);
      }
      else if (userInput.equals("c") || userInput.equals("cpt")){
        if (graph != null) viewCPT(); else io.output(message.ERROR_NO_GRAPH);
      }
      else if (userInput.equals("g") || userInput.equals("graph")){
        if (graph != null) viewGraph(); else io.output(message.ERROR_NO_GRAPH);
      }
      else if (userInput.equals("q") || userInput.equals("quit")){
        return;
      }
      else {
        io.output(message.ERROR_BAD_INPUT);
      }
    }
  }

  /**
   * Load a graph from a given input file
   */
  private static void load(){
    String userInput = io.input(message.INPUT_FILE_PATH);
    
    //NOTE: The following is for ease of use during development
    String filePath = "";
    if (userInput.equals("a")) filePath = "./examples/asia/asia.bif";
    else filePath = userInput;
    
    try{
      graph = new BNGraph(filePath);
    } catch(Throwable e){
      io.output(message.ERROR_BAD_FILE_PATH);
    }
  }
  

  /**
   * Allows a user to input observe a nodes value
   */
  private static void observeNode(){
    io.output("Nodes: " + graph.getNodeNames());
    String observationNodeName = io.input(message.INPUT_NODE_NAME);
    BNNode observationNode = graph.getNode(observationNodeName);
    if (observationNode == null){
      io.output(message.ERROR_BAD_NODE_NAME+observationNodeName);
      return;
    }
    io.output(observationNode.getPossibleValuesOutput());
    String valueName = io.input(message.INPUT_OBSERVED_VALUE);
    observationNode.observe(valueName);
  }

  /**
   * Use Variable Elimination to get the marginals on the graph
   */
  private static void inference(){
    ArrayList<String> results = BNInference.getMarginalsOutput(graph);
    io.output(results);
  }

  /**
   * TODO: Refactor this method
   */
  private static void addImplicationStatement(){    
    String userInput = io.input(message.INPUT_IMPLICATION_TYPE);

    io.output("Nodes: " + graph.getNodeNames());

    String antecedentName = io.input(message.INPUT_ANTECEDENT_NODE_NAME);
    BNNode antecedentNode = graph.getNode(antecedentName);
    if (antecedentNode == null) {
      io.output(message.ERROR_BAD_NODE_NAME+antecedentName);
      return;
    }
    
    String consequentName = io.input(message.INPUT_CONSEQUENT_NODE_NAME);
    BNNode consequentNode = graph.getNode(consequentName); 
    if (consequentNode == null) {
      io.output(message.ERROR_BAD_NODE_NAME+consequentName);
      return;
    }

    Relationship relationship = graph.getRelationship(antecedentNode, consequentNode);

    if (relationship == Relationship.SELF){
      io.output(message.ERROR_RELATIONSHIP_SELF);
      return;
    }
    Implication impl = null;
    if (userInput.equals("c")){
      impl = new ClassicalImplication(antecedentNode, consequentNode, relationship);
      }
    else if (userInput.equals("d")){
      impl = new DefeasibleImplication(antecedentNode, consequentNode, relationship);
    }
    graph.addImplicationStatement(impl);
  }

  /**
   * View the implication statements that have been added to the network
   */
  private static void viewImplicationStatements(){
    io.output(graph.getKnowledgebaseOutput());
  }

  /**
   * View a text description of the currently loaded graph's nodes CPTs. 
   */
  private static void viewCPT(){
    io.output(graph.getNodeOutputs());
  }

  /**
   * View a text description of the currently loaded graph. 
   */
  private static void viewGraph(){
    io.output(graph.getGraphOutput());
  }

  /**
   * Get the correct menu text to display 
   */
  private static String menuText(){
    String networkName = (graph == null) ? "None" : graph.getName();
    return message.menuText(networkName);
  }
  

}