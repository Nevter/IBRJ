package edu.uct.ibn;

import edu.ksu.cis.bnj.bbn.inference.elimbel.*;
import edu.ksu.cis.bnj.bbn.inference.*;


public class BNInference {

  private BNGraph graph = null;
  private ElimBel variableElimination = null;

  public BNInference(BNGraph graph){
    this.graph = graph;
  }

  public InferenceResult getMarginals(){
    variableElimination = new ElimBel(graph.getBBNGraph());
    return variableElimination.getMarginals();
  }

  


}
