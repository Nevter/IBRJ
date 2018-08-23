package edu.uct.ibn;

import edu.uct.ibn.implication.*;
import edu.uct.ibn.BNNode.Relationship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

import edu.ksu.cis.bnj.bbn.BBNGraph;
import edu.ksu.cis.bnj.bbn.BBNNode;

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
    for (Iterator i = graph.getNodes().iterator(); i.hasNext(); )
    {
        BBNNode n = (BBNNode) i.next();
        nodes.add(new BNNode(n));
    }
    return nodes;
  }

  public Relationship getRelationship(BNNode antecedent, BNNode consequent){

    System.out.println("Check Relationship between:");
    System.out.println(antecedent);
    System.out.println(consequent);
    System.out.println();

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
    knowledgeBase.add(impl);
  }

  public ArrayList<Implication> getKnowledgebase(){
    return knowledgeBase;
  }

  public String toString(){
    return graph.toString();
  }

  public String getGraphDescription(){
    return graph.debugGetEdgePrintout();
  }

}
