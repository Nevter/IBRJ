package edu.uct.ibn.bayesnet;

import edu.uct.ibn.implication.*;
import edu.uct.ibn.bayesnet.BNNode.Relationship;

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
  private String path = null;
  private ArrayList<Implication> knowledgeBase = new ArrayList<Implication>();

  public BNGraph(String path){
    graph = new BBNGraph();
    this.path = path;
    load(path);
  }

  public void load(String path){
    graph = graph.load(path);
  }

  /**
   * we have to make a whole new CPF because: when a CPF is made, it takes in a list of node names that can
   * be in the CPF. As we are adding new values to the CPF, it doesnt accept them as they arent in this 
   * initial list. There is no way of adding nodes to this list. Thus we have to make a whole new CPF and 
   * 'reset' the nodes CPF
   * @param src
   * @param dest
   */
  public void addEdge(BNNode src, BNNode dest){
    graph.addEdge(src.getNode(), dest.getNode());

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

  public void addImplicationStatement(Implication impl){
    if (impl != null) knowledgeBase.add(impl);
    impl.supplementNetwork();
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
