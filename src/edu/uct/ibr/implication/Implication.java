package edu.uct.ibr.implication;

import edu.uct.ibr.bayesnet.BNNode;
import edu.uct.ibr.bayesnet.BNNode.Relationship;
import edu.uct.ibr.bayesnet.BNGraph;

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
