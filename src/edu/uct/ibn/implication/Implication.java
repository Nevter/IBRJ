package edu.uct.ibn.implication;

import edu.uct.ibn.BNNode;
import edu.uct.ibn.BNNode.Relationship;
import edu.uct.ibn.BNGraph;

public abstract class Implication {
  BNNode antecedentNode = null;
  BNNode consequentNode = null;
  Relationship relationship = null;


  public Implication(BNNode antecedentNode, BNNode consequentNode, Relationship relationship){
    this.antecedentNode = antecedentNode;
    this.consequentNode = consequentNode;
    this.relationship = relationship;
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
  
  public abstract BNGraph supplementNetwork(BNGraph graph);

  public abstract String toString();

}
