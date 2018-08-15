package edu.uct.dbr.implication;

import edu.uct.dbr.BNNode;

public abstract class Implication {
  BNNode antecedentNode = null;
  BNNode consequentNode = null;


  public Implication(BNNode antecedentNode, BNNode consequentNode){
    this.antecedentNode = antecedentNode;
    this.consequentNode = consequentNode;
  }

  public BNNode getAntecedentNote(){
    return antecedentNode;
  }

  public BNNode getConsequentNode(){
    return consequentNode;
  }

  public String toString(){
    StringBuffer buffer = new StringBuffer();
    buffer.append("Antecedent Node: " + antecedentNode.getName() + "\n");
    buffer.append("Consequent Node: " + consequentNode.getName() + "\n");

    return buffer.toString();
  }

}
