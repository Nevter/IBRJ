package edu.uct.dbr.implication;

import edu.uct.dbr.BNNode;

public class ClassicalImplication extends Implication {


  public ClassicalImplication(BNNode antecedentNode, BNNode consequentNode){
    super(antecedentNode, consequentNode);
  }

  public String toString(){
    StringBuffer buffer = new StringBuffer();

    buffer.append(antecedentNode.getName());
    buffer.append(" -> ");
    buffer.append(consequentNode.getName());

    return buffer.toString();
  }

}
