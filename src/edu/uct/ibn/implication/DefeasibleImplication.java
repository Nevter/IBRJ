package edu.uct.ibn.implication;

import edu.uct.ibn.BNNode;
import edu.uct.ibn.BNNode.Relationship;
import edu.uct.ibn.BNGraph;


public class DefeasibleImplication extends Implication {


  public DefeasibleImplication(BNNode antecedentNode, BNNode consequentNode, Relationship relationship){
    super(antecedentNode, consequentNode, relationship);
  }

  public BNGraph supplementNetwork(BNGraph graph){
    System.out.println("Cannot supplement network with defeasible logic");
    return graph;
  }


  public String toString(){
    StringBuffer buffer = new StringBuffer();

    buffer.append(antecedentNode.getName());
    buffer.append(" ~> ");
    buffer.append(consequentNode.getName());

    return buffer.toString();
  }

}
