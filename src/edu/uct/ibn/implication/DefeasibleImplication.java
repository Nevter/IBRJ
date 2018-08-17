package edu.uct.ibn.implication;

import edu.uct.ibn.BNNode;

public class DefeasibleImplication extends Implication {


  public DefeasibleImplication(BNNode antecedentNode, BNNode consequentNode){
    super(antecedentNode, consequentNode);
  }

  public String toString(){
    StringBuffer buffer = new StringBuffer();

    buffer.append(antecedentNode.getName());
    buffer.append(" ~> ");
    buffer.append(consequentNode.getName());

    return buffer.toString();
  }

}