package edu.uct.dbr.implication;

import edu.uct.dbr.BNNode;


public abstract class Implication {
  BNNode antecedentNode = null;
  BNNode consequentNode = null;


  public Implication(BNNode antecedentNode, BNNode consequentNode){
    this.antecedentNode = antecedentNode;
    this.consequentNode = consequentNode;
  }

  public String toString(){
    return antecedentNode.getName() + " -> " + consequentNode.getName();
  }

}
