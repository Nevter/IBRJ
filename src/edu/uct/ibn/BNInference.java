package edu.uct.ibn;

import edu.ksu.cis.bnj.bbn.inference.elimbel.*;

import java.util.ArrayList;

import edu.ksu.cis.bnj.bbn.inference.*;


import edu.uct.ibn.implication.*;

public class BNInference {

  private BNGraph graph = null;
  private ElimBel variableElimination = null;

  public BNInference(BNGraph graph){
    this.graph = graph;
  }

  public InferenceResult getMarginals(){
    variableElimination = new ElimBel(graph.getBBNGraph());

    ArrayList<Implication> kb = graph.getKnowledgebase();

    

    return variableElimination.getMarginals();
  }

  /**
   * TODO: Implement this
   */
  public ArrayList<ArrayList<Implication>> rationalClosure(ArrayList<Implication> kb){
    ArrayList<ArrayList<Implication>> kbRankings = new ArrayList<ArrayList<Implication>>();

    kbRankings.add(kb);

    return kbRankings;
  }

  


}
