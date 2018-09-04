package edu.uct.ibn.implication;

import edu.uct.ibn.bayesnet.BNNode;
import edu.uct.ibn.bayesnet.BNNode.Relationship;
import edu.uct.ibn.bayesnet.BNGraph;


public class DefeasibleImplication extends Implication {


  public DefeasibleImplication(BNNode antecedentNode, BNNode consequentNode, BNGraph graph){
    super(antecedentNode, consequentNode, graph);
  }

  public void supplementNetwork(){
    System.out.println("Cannot supplement network with defeasible logic (yet)");
  }


  public String toString(){
    StringBuffer buffer = new StringBuffer();

    buffer.append(antecedentNode.getName());
    buffer.append(" ~> ");
    buffer.append(consequentNode.getName());

    return buffer.toString();
  }

}
