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

package edu.uct.ibr.bayesnet;

import edu.uct.ibr.implication.*;
import edu.uct.ibr.bayesnet.BNNode.Relationship;
import edu.uct.ibr.util.ImplicationUtil;
import edu.uct.ibr.util.ibifFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Hashtable;

import edu.ksu.cis.bnj.bbn.BBNGraph;
import edu.ksu.cis.bnj.bbn.BBNNode;
import edu.ksu.cis.bnj.bbn.BBNCPF;
import edu.ksu.cis.bnj.bbn.BBNConstant;
import edu.ksu.cis.bnj.bbn.BBNDiscreteValue;

public class BNGraph {

  private BBNGraph graph = null;
  private BBNGraph botGraph = null;
  private String path = null;
  private ArrayList<Implication> knowledgeBase = new ArrayList<Implication>();
  private ArrayList<Implication> logicalObservations = new ArrayList<Implication>();
  

  public BNGraph(BBNGraph graph){
    this.graph = graph;
    this.botGraph = graph;
    this.path = "";
  }

  public BNGraph(String path){
    graph = new BBNGraph();
    botGraph = new BBNGraph();
    this.path = path;
    load(path);
  }

  /**
   * Load a file into this BNGraph
   * @param path
   */
  public void load(String path){
    String format = path.substring(path.lastIndexOf('.'), path.length());
    if (format.equals(".ibif")){
      //if the path is a .ibif use the ibif factory
      ArrayList<String[]> loadedKB = ibifFactory.load(path);
      graph = graph.load("temp.bif");
      botGraph = botGraph.load("temp.bif");
      for (String[] implPair : loadedKB){
        BNNode antecedent = this.getNode(implPair[0]);
        BNNode consequent = this.getNode(implPair[1]);
        if (antecedent != null && consequent != null){
          this.addImplicationStatement(new ClassicalImplication(antecedent,consequent,this));
        }
      }
    }
    else{
      graph = graph.load(path);
      botGraph = botGraph.load(path);
    }
  }

  /**
   * Add an edge between the src and dest nodes. Returns a boolean as the success of the operation
   * we have to make a whole new CPF because: when a CPF is made, it takes in a list of node names that can
   * be in the CPF. As we are adding new values to the CPF, it doesnt accept them as they arent in this 
   * initial list. There is no way of adding nodes to this list. Thus we have to make a whole new CPF and 
   * 'reset' the nodes CPF
   * @param src
   * @param dest
   */
  public boolean addEdge(BNNode src, BNNode dest){
    try{
      graph.addEdge(src.getNode(), dest.getNode());
    } catch (Exception e){
      return false;
    }

    //Create the new CPT for the dest node (with this added parent):
  
    BBNCPF destCPF = (BBNCPF)dest.getCPF().clone();
    Hashtable destTable = destCPF.getTable();
    Set<Hashtable> destTableKeys = destTable.keySet();

    Set nodeNames = dest.getNode().getParentNames();
    nodeNames.add(dest.getName());
    BBNCPF newDestCPF = new BBNCPF(nodeNames);

    //Go through each entry. Create two new entries that are the same as the previous but one with the new
    //node+it's true value, and the other with the new node+it's false value. Then add both of these to a new
    //CPT
    for (Hashtable h : destTableKeys){
      Hashtable truthEntry = (Hashtable<String,String>)h.clone();
      truthEntry.put(src.getName(), src.getTruthValueName());
      Hashtable falseEntry = (Hashtable<String,String>)h.clone();
      falseEntry.put(src.getName(), src.getFalseValueName());
      BBNConstant entryValue = (BBNConstant) destTable.get(h);
      newDestCPF.put(truthEntry, new BBNConstant(0.0));
      newDestCPF.put(falseEntry, entryValue);
    }

    dest.getNode().setCPF(newDestCPF);
    return true;
  }

  /**
   * Get the bot BBNGraph
   * @return
   */
  public BBNGraph getBotBBNGraph(){
    return botGraph;
  }

  /**
   * Save this BNGraph to the outputFile. The file type will be .bif
   * if Knowledge base is empty, else it will be a .ibif.
   */
  public void save(String outputFile){
    if(knowledgeBase.size() < 1){
      //there are no implication statements, save normally. 
      graph.save(outputFile+".bif");
    }
    else {
      //there are implication statements, save in ibif.
      ibifFactory.save(this, outputFile+".ibif");
    }

  }

  /**
   * Get a node in the graph
   * @param nodeName
   * @return
   */
  public BNNode getNode(String nodeName){
    Set nodes = getGraphNodes();
    for (Iterator i=nodes.iterator(); i.hasNext(); ) {
        BNNode n = (BNNode) i.next();
        if (n.getName().toLowerCase().equals(nodeName.toLowerCase())){
          return n;
        }
    }
    return null;
  }

  /**
   * get a set of all the nodes in the graph
   * @return
   */
  public Set getGraphNodes(){
    HashSet nodes = new HashSet();
    for (Iterator i = graph.getNodes().iterator(); i.hasNext(); ){
        BBNNode n = (BBNNode) i.next();
        nodes.add(new BNNode(n));
    }
    return nodes;
  }

  /**
   * Get all logical observations made on the IBN
   * @return
   */
  public ArrayList<Implication> getLogicalObservations(){
    return logicalObservations;
  }

  /**
   * Get the relationship between the two nodes. Relationships defined in BNNode
   * @param antecedent
   * @param consequent
   * @return
   */
  public Relationship getRelationship(BNNode antecedent, BNNode consequent){
    if (antecedent.equals(consequent)){
      return Relationship.SELF;
    }

    Set antecedentChildren = antecedent.getChildren();
    for (Iterator i=antecedentChildren.iterator(); i.hasNext(); ) {
        BNNode n = (BNNode) i.next();
        if (n.equals(consequent)) {
          return Relationship.PARENT;
        }
    }

    Set antecedentParents = antecedent.getParents();
    for (Iterator i=antecedentParents.iterator(); i.hasNext(); ) {
        BNNode n = (BNNode) i.next();
        if (n.equals(consequent)) {
          return Relationship.CHILD;
        }
    }

    Set antecedentDescendents = antecedent.getDescendants();
    for (Iterator i=antecedentDescendents.iterator(); i.hasNext(); ) {
        BNNode n = (BNNode) i.next();
        if (n.equals(consequent)) {
          return Relationship.ANCESTOR;
        }
    }

    Set antecedentAncestors = antecedent.getAncestors();
    for (Iterator i=antecedentAncestors.iterator(); i.hasNext(); ) {
        BNNode n = (BNNode) i.next();
        if (n.equals(consequent)) {
          return Relationship.DESCENDANT;
        }
    }

    return Relationship.NONE;
  }

  /**
   * Get this BBNGraph
   * @return
   */
  public BBNGraph getBBNGraph(){
    return graph;
  }

  /**
   * Get the name of this graph
   * @return
   */
  public String getName(){
    return graph.getName();
  }

  /**
   * Add an implication statement to the knowledge base and modify the graph correctly
   * @param impl
   * @return
   */
  public boolean addImplicationStatement(Implication impl){
    boolean success = true;
    if (impl != null){
      if (ImplicationUtil.hasClassicalCycle(knowledgeBase, impl)){
        if (!knowledgeBase.contains(impl)){
          knowledgeBase.add(impl);
        }
        collapseCycle();
      }
      else if (impl.supplementNetwork()){
        if (!knowledgeBase.contains(impl)){
          knowledgeBase.add(impl);
        }
      }
      else {
        success = false;
      }
    }
    else {
      success = false;
    }

    //if (ImplicationUtil.hasClassicalCycle(knowledgeBase)){
    //  collapseCycle();
    //}

    return success;
  }

  /**
   * Collapse nodes that form part of an implication cycle into one node
   */
  private void collapseCycle(){
    Set<String> cycleNodeNames = ImplicationUtil.getConnectedClassicalImplications(knowledgeBase);

    Set<BNNode> cycleNodes = new HashSet<BNNode>();

    for (String s : cycleNodeNames){
      cycleNodes.add(getNode(s));
    }

    Set<BNNode> cycleParents = new HashSet<BNNode>();
    Set<BNNode> cycleChildren = new HashSet<BNNode>();

    //1)2) Identify parents and children
    for (BNNode node : cycleNodes){
      Set<BNNode> parents = node.getParents();
      Set<BNNode> children = node.getChildren();
      cycleParents.addAll(parents);
      cycleChildren.addAll(children);
    }
    cycleParents.removeAll(cycleNodes);
    cycleChildren.removeAll(cycleNodes);

    //3)Remove all cycle nodes from the graph
    for (BNNode n : cycleNodes) {
      graph.removeNode(n.getNode());
    }

    String cycleNodeName = "";
    for (BNNode n : cycleNodes) {
      cycleNodeName += n.getName() + "+";
    }
    cycleNodeName = cycleNodeName.substring(0,cycleNodeName.length()-1);

    //4)Add new cycleNode
    BNNode cycleNode = new BNNode(this,cycleNodeName);
    graph.addNode(cycleNode.getNode());

    //5)Add edges from parents to cycleNode
    for (BNNode p : cycleParents) {
      graph.addEdge(p.getNode(),cycleNode.getNode());
    }

    //6)Add edges from cycleNode to children
    for (BNNode c : cycleChildren) {
      graph.addEdge(cycleNode.getNode(),c.getNode());
    }

    //7)Build CPT for cycleNode
    //7.1)Add each entry to cycleNodeCPF set to 0.5
    Set<String> cycleParentNames = new HashSet<String>();
    for (BNNode p : cycleParents) {
      cycleParentNames.add(p.getName());
    }
    Set<String> nodesInCycleCPF = new HashSet<String>();
    nodesInCycleCPF.addAll(cycleParentNames);
    nodesInCycleCPF.add(cycleNodeName);
    BBNCPF cycleNodeCPF = new BBNCPF(nodesInCycleCPF);
    List<Hashtable> cpfEntries = new ArrayList<Hashtable>();  
    int numEntries = (int)Math.pow(2,nodesInCycleCPF.size());
    for (int i = 0; i < numEntries; i++){
      cpfEntries.add(new Hashtable<String,String>());
    }
    int groupSize = numEntries;
    for (String entryName : cycleParentNames){
      groupSize = groupSize/2;
      int counter = 1;
      for (Hashtable entry : cpfEntries){
        if (counter <= groupSize){
          entry.put(entryName, new BNNode((BBNNode) botGraph.getNode(entryName)).getTruthValueName());
          counter++;
        }
        else /* (counter > groupSize)*/{
          entry.put(entryName, new BNNode((BBNNode) botGraph.getNode(entryName)).getFalseValueName());
          counter++;
        }
        if (counter > groupSize*2){
          counter = 1;
        }
      }
    }
    boolean truth = true;
    for (Hashtable entry : cpfEntries){
      if (truth){
        entry.put(cycleNodeName, "true");
        truth = false;
      }
      else{
        entry.put(cycleNodeName, "false");
        truth = true;
      }
    }

    //7.2)Set each entry in the CPF to the right value
    for (Hashtable entry : cpfEntries){
      double entryProbValue = 0.5;
      Hashtable<String[],Hashtable<String,String>> conditionalProbabilities = new Hashtable<String[],Hashtable<String,String>>();
      Hashtable<String,String> observations = new Hashtable<String, String>();     
      for (String parentName : cycleParentNames){
        observations.put(parentName, (String) entry.get(parentName));
      }
      for (String cycleN : cycleNodeNames){
        String[] queryNode = {cycleN, new BNNode((BBNNode)botGraph.getNode(cycleN)).getTruthValueName()};
        conditionalProbabilities.put(queryNode, (Hashtable<String,String>)observations.clone());
        observations.put(queryNode[0], queryNode[1]);
      }
      entryProbValue = getConditionalProbabilities(conditionalProbabilities);
      if (entry.get(cycleNodeName).equals("false")){
        entryProbValue = 1 - entryProbValue;
      }
      cycleNodeCPF.put(entry, new BBNConstant(entryProbValue));
    }
    cycleNode.getNode().setValues(new BBNDiscreteValue(new String[]{"true","false"}));
    cycleNode.getNode().setCPF(cycleNodeCPF);

    //8)Modify CPTs of each child
    for (BNNode childNode : cycleChildren) {
      //8.1)Ignore child only has one parent in the cycle
      //8.2)If child has more than one parent in the cycle, remove entry from CPT if both parents dont hold the same truth value
      Set<String> cycleNodeNamesParents = cycleNodeNames.stream().collect(Collectors.toSet());
      Set<String> botParents = botGraph.getNode(childNode.getName()).getParentNames();
      cycleNodeNamesParents.retainAll(botParents);
      if (cycleNodeNamesParents.size() > 1){
        BBNCPF childCPF = (BBNCPF)childNode.getCPF();
        Hashtable childTable = childCPF.getTable();
        Set<Hashtable> childTableKeys = childTable.keySet();
        Set<Hashtable> entriesToRemove = new HashSet<Hashtable>();        
        Iterator cycleParentsItr = cycleNodeNamesParents.iterator();
        String parentName = (String) cycleParentsItr.next();    
        for (int i = 0; i < cycleNodeNamesParents.size() - 1; i++){
          String parentNameNext = (String) cycleParentsItr.next();
          for (Hashtable entry : childTableKeys) {
            if (entry.get(parentName).equals(new BNNode((BBNNode) botGraph.getNode(parentName)).getTruthValueName())){
              if (!entry.get(parentNameNext).equals(new BNNode((BBNNode) botGraph.getNode(parentNameNext)).getTruthValueName())){
                //add this hastable to a list of things that need to be removed
                entriesToRemove.add(entry);
              }
            }
            if (entry.get(parentName).equals(new BNNode((BBNNode) botGraph.getNode(parentName)).getFalseValueName())){
              if (!entry.get(parentNameNext).equals(new BNNode((BBNNode) botGraph.getNode(parentNameNext)).getFalseValueName())){
                //add this hastable to a list of things that need to be removed
                entriesToRemove.add(entry);
              }
            }
          }
          parentName = parentNameNext;
        }
        for (Hashtable ht : entriesToRemove) {
          childCPF.remove(ht);
        }
      }

      //8.3) change all references in the CPT to be to the cycle node instead of parent node
      BBNCPF childCPF = childNode.getCPF();
      Set<BNNode> childParents = childNode.getParents();
      Set<String> cpfNodeNames = new HashSet<String>();
      for (BNNode n : childParents) {
        cpfNodeNames.add(n.getName());
      }
      cpfNodeNames.add(childNode.getName());
      BBNCPF newChildCPF = new BBNCPF(cpfNodeNames);
      Hashtable childCPTTable = childCPF.getTable();
      Set<Hashtable> oldTableEntries = childCPF.getTable().keySet();
      for (Hashtable entry : oldTableEntries){
      
        //work out if positive or negative cycle entry
        boolean positive = false;
        for (String cycleNN : cycleNodeNames){
          if (entry.containsKey(cycleNN)){
            if (new BNNode((BBNNode)botGraph.getNode(cycleNN)).getTruthValueName().equals(entry.get(cycleNN))){
              positive = true;
              break;
            }
          }
        }
        Hashtable newEntry = (Hashtable) entry.clone();
        if (positive){
          //dealing with a 'true' entry
          for (String cycleNN : cycleNodeNames){
            newEntry.remove(cycleNN);
          }
          newEntry.put(cycleNodeName,"true");
          newChildCPF.put(newEntry, (BBNConstant)childCPTTable.get(entry));
        }
        else{
          //dealing with a 'false' entry
          for (String cycleNN : cycleNodeNames){
            newEntry.remove(cycleNN);
          }
          newEntry.put(cycleNodeName,"false");
          newChildCPF.put(newEntry, (BBNConstant)childCPTTable.get(entry));
        }
        childNode.getNode().setCPF(newChildCPF);
      }

    }
  }

  /**
   * Get the probability table, given a hashtable of observations
   * @param cP
   * @return
   */
  public double getConditionalProbabilities(Hashtable<String[],Hashtable<String,String>> cP){
    double val = 1.0;

    Set<String[]> queries = cP.keySet();

    for (String[] query : queries){
      val *= queryBotNetwork(query[0], query[1], cP.get(query));
    }
    return val;
  }

  /**
   * Make a query on the bottom network given a set of observations
   * @param observations
   * @return
   */
  public String queryBotNetwork(Hashtable<String,String> observations){
    Set<String> obsKeySet = observations.keySet();
    BBNGraph botGraphDash = (BBNGraph)botGraph.clone();
    for (String node : obsKeySet){
      BBNNode n = (BBNNode)botGraphDash.getNode(node);
      n.setEvidenceValue(observations.get(node));
    }
    String result = BNInference.getMarginalsOutput(new BNGraph(botGraphDash));
    return result;
  }

  /**
   * Make a query on a specific node and value given a set of observations
   * @param queryNode
   * @param queryValue
   * @param observations
   * @return
   */
  public double queryBotNetwork(String queryNode, String queryValue, Hashtable<String,String> observations){
    String queryResult = queryBotNetwork(observations);
    String[] queryLines = queryResult.split("\n");
    for (String line : queryLines) {
      String[] lineSegments = line.split("=");    
      for (int i = 0; i<lineSegments.length;i++){
        lineSegments[i] = lineSegments[i].replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "");
      }
      if (lineSegments[0].equals(queryNode)){
        if (lineSegments[1].equals(queryValue)){
          return Double.parseDouble(lineSegments[2]);
        }
      }
    }
    
    return 0.0;
  }

  /**
   * Get the knowledge base
   * @return
   */
  public ArrayList<Implication> getKnowledgebase(){
    return knowledgeBase;
  }
  
  /**
   * Get the knowledgebase as a set of strings
   * @return
   */
  public ArrayList<String> getKnowledgebaseOutput(){
    ArrayList<String> kbOutput = new ArrayList<String>();
    for (Implication i : knowledgeBase){
      kbOutput.add(i.toString());
    }
    return kbOutput;
  }

  /**
   * The output of this graph
   */
  public String toString(){
    return graph.toString();
  }

  /**
   * Get the probabilistic observations make on this network
   * @return
   */
  public ArrayList<String> getObservations(){
    Set<BNNode> nodes = getGraphNodes();
    ArrayList<String> observations = new ArrayList<String>();
    for (BNNode n : nodes){
      if (n.isObserved()){
        observations.add(n.getName()+" = "+n.getObservedValue());
      } 
    }
    for (Implication i : logicalObservations){
      observations.add(i.toString());
    }
    return observations;
  }

  /**
   * Make a probabilistic observation on a node in this network
   * @param node
   * @param value
   */
  public void observe(BNNode node, String value){
    node.observe(value);
  }

  /**
   * Make a logical observation on this network
   * @param impl
   */
  public void observe(Implication impl){
    logicalObservations.add(impl);
  }

  /**
   * Get a toString of this graph
   * @return
   */
  public String getGraphOutput(){
    return graph.debugGetEdgePrintout();
  }


  /**
   * Get the names of the nodes in this network
   * @return
   */
  public ArrayList<String> getNodeNames(){
    ArrayList<String> nodeNames = new ArrayList<String>();
    Set nodes = getGraphNodes();
    for (Iterator i=nodes.iterator(); i.hasNext(); ) {
      BNNode n = (BNNode) i.next();
      nodeNames.add(n.getName());
    }
    return nodeNames;
  }

  /**
   * Get the output names for all the nodes in this network
   * @return
   */
  public ArrayList<String> getNodeOutputs(){
    ArrayList<String> nodeDescriptions = new ArrayList<String>();
    Set nodes = getGraphNodes();
    for (Iterator i=nodes.iterator(); i.hasNext(); ) {
      BNNode n = (BNNode) i.next();
      nodeDescriptions.add(n.toVerboseString());
    }
    return nodeDescriptions;
  }

}
