package edu.uct.ibr.bayesnet;

import edu.uct.ibr.implication.*;
import edu.uct.ibr.bayesnet.BNNode.Relationship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import java.util.HashSet;
import java.util.Hashtable;

import edu.ksu.cis.bnj.bbn.BBNGraph;
import edu.ksu.cis.bnj.bbn.BBNNode;
import edu.ksu.cis.bnj.bbn.BBNCPF;
import edu.ksu.cis.bnj.bbn.BBNConstant;

public class BNGraph {

  private BBNGraph graph = null;
  private BBNGraph botGraph = null;
  private String path = null;
  private ArrayList<Implication> knowledgeBase = new ArrayList<Implication>();
  private ArrayList<Implication> logicalObservations = new ArrayList<Implication>();
  
  public BNGraph(String path){
    graph = new BBNGraph();
    botGraph = new BBNGraph();
    this.path = path;
    load(path);
  }

  public void load(String path){
    graph = graph.load(path);
    botGraph = botGraph.load(path);
  }

  /**
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
      System.out.println(e);
      System.out.println("Addign this edge creates a cycle");
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

  public BBNGraph getBotBBNGraph(){
    return botGraph;
  }

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

  public Set getGraphNodes(){
    HashSet nodes = new HashSet();
    for (Iterator i = graph.getNodes().iterator(); i.hasNext(); ){
        BBNNode n = (BBNNode) i.next();
        nodes.add(new BNNode(n));
    }
    return nodes;
  }

  public ArrayList<Implication> getLogicalObservations(){
    return logicalObservations;
  }

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

  public BBNGraph getBBNGraph(){
    return graph;
  }

  public String getName(){
    return graph.getName();
  }

  public boolean addImplicationStatement(Implication impl){
    boolean success = true;
    if (impl != null){
      if (impl.supplementNetwork()){
        knowledgeBase.add(impl);
      }
      else {
        success = false;
      }
    }
    else {
      success = false;
    }
    return success;
  }

  public ArrayList<Implication> getKnowledgebase(){
    return knowledgeBase;
  }
  
  public ArrayList<String> getKnowledgebaseOutput(){
    ArrayList<String> kbOutput = new ArrayList<String>();
    for (Implication i : knowledgeBase){
      kbOutput.add(i.toString());
    }
    return kbOutput;
  }

  public String toString(){
    return graph.toString();
  }

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




  public void observe(BNNode node, String value){
    node.observe(value);
  }

  public void observe(Implication impl){
    logicalObservations.add(impl);
  }

  public String getGraphOutput(){
    return graph.debugGetEdgePrintout();
  }


  public ArrayList<String> getNodeNames(){
    ArrayList<String> nodeNames = new ArrayList<String>();
    Set nodes = getGraphNodes();
    for (Iterator i=nodes.iterator(); i.hasNext(); ) {
      BNNode n = (BNNode) i.next();
      nodeNames.add(n.getName());
    }
    return nodeNames;
  }

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
