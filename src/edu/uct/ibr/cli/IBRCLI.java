/*
 * This file is part of Implicative Bayesian Reasoner for Java (IBRJ).
 *
 * IBRJ is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either v3.0 of the License, or
 * (at your option) any later version.
 *
 * IBRJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License v3.0
 * along with IBRJ in LICENSE.txt file; if not, refer to the GNU GPL website.
 */

package edu.uct.ibr.cli;

import edu.uct.ibr.implication.*;
import edu.uct.ibr.util.*;
import edu.uct.ibr.bayesnet.BNNode.Relationship;
import edu.uct.ibr.bayesnet.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Set;
import java.util.*;
import java.util.Iterator;

public class IBRCLI {

  private static BNGraph graph = null;

  /**
   * Run the IBR Command Line Interface
   */
  public static void run(){
      io.output(message.CLI_HEADER);

      ibr();
    
      io.output(message.LINE);
  }

  /**
   * The main loop that controls displays and controls user input on the Command line
   */
  private static void ibr(){
    while(true){
      
      String userInput = io.input(menuText());
      
      if (userInput.equals("l") || userInput.equals("load")){
        load();
      }
      else if (userInput.equals("s") || userInput.equals("save")){
        if (graph != null) save(); else io.output(message.ERROR_NO_GRAPH);
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
    String filePath = io.input(message.INPUT_FILE_PATH);
    
    try{
      graph = new BNGraph(filePath);
    } catch(Throwable e){
      io.output(message.ERROR_BAD_FILE_PATH);
    }
  }
  /**
   * Save the network
   */
  private static void save(){
    String outputFile = io.input(message.OUTPUT_FILE_PATH);
    graph.save(outputFile);
  }
  
  /**
   * Allows a user to input observe a nodes value
   */
  private static void observeNode(){
    String observationType = io.input(message.CLI_OBSERVATION_TYPE);
  
    if(observationType.equals("l")){
      //logical observation
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
  
      Implication impl = new ClassicalImplication(antecedentNode, consequentNode, graph);
      graph.observe(impl);
    }
    else if(observationType.equals("p")){
      //probabilistic observation
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
    else {
      return;
    }
  
    
  }

  /**
   * Use Variable Elimination to get the marginals on the graph
   */
  private static void inference(){
    String result = BNInference.getMarginalsOutput(graph);
    io.output(result);
  }

  /**
   * Add an implication statement to this network
   */
  private static void addImplicationStatement(){    

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

    Implication impl = new ClassicalImplication(antecedentNode, consequentNode, graph);
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