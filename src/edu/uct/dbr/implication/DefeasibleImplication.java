package edu.uct.dbr.implication;

import edu.uct.dbr.BNNode;

public class DefeasibleImplication extends Implication {


  public DefeasibleImplication(BNNode antecedentNode, BNNode consequentNode){
    super(antecedentNode, consequentNode);
  }

  public String toString(){
    return "DefeasibleImplication\n" + super.toString();
  }

}
