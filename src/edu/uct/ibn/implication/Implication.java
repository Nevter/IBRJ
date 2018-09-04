package edu.uct.ibn.implication;

import edu.uct.ibn.bayesnet.BNNode;
import edu.uct.ibn.bayesnet.BNNode.Relationship;
import edu.uct.ibn.bayesnet.BNGraph;

public abstract class Implication {
  BNNode antecedentNode = null;
  BNNode consequentNode = null;
  Relationship relationship = null;
  BNGraph graph = null;


  public Implication(BNNode antecedentNode, BNNode consequentNode, BNGraph graph){
    this.antecedentNode = antecedentNode;
    this.consequentNode = consequentNode;
    this.relationship = graph.getRelationship(antecedentNode, consequentNode);
    this.graph = graph;
  }

  
  public BNNode getAntecedentNote(){
    return antecedentNode;
  }
  
  public BNNode getConsequentNode(){
    return consequentNode;
  }
  
  public Relationship getRelationship(){
    return relationship;
  }
  
  public abstract void supplementNetwork();

  public abstract String toString();

}
