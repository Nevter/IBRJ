package edu.uct.ibr.implication;

import edu.uct.ibr.bayesnet.BNNode;
import edu.uct.ibr.bayesnet.BNNode.Relationship;
import edu.uct.ibr.bayesnet.BNGraph;


public class DefeasibleImplication extends Implication {


  public DefeasibleImplication(BNNode antecedentNode, BNNode consequentNode, BNGraph graph){
    super(antecedentNode, consequentNode, graph);
  }

  public boolean supplementNetwork(){
    System.out.println("Cannot supplement network with defeasible logic (yet)");
    return true;
  }


  public String toString(){
    StringBuffer buffer = new StringBuffer();

    buffer.append(antecedentNode.getName());
    buffer.append(" ~> ");
    buffer.append(consequentNode.getName());

    return buffer.toString();
  }

}
