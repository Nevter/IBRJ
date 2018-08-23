package edu.uct.ibn;


import edu.ksu.cis.bnj.bbn.inference.elimbel.*;
import edu.ksu.cis.bnj.bbn.inference.*;

import java.util.ArrayList;

import edu.uct.ibn.implication.*;


public class BNInference {

  private BNGraph graph = null;
  private ElimBel variableElimination = null;

  public BNInference(BNGraph graph){
    this.graph = graph;
  }

  public ArrayList<InferenceResult> getMarginals(){
    ArrayList<InferenceResult> results = new ArrayList<InferenceResult>();

    ArrayList<Implication> kb = graph.getKnowledgebase();
    
    ArrayList<ArrayList<Implication>> rankings = rationalClosure(kb);
    
    for (ArrayList<Implication> kbRank : rankings){
      BNGraph graphDash = supplementNetwork(graph, kbRank);
      variableElimination = new ElimBel(graphDash.getBBNGraph());
      results.add(variableElimination.getMarginals());
    }

    return results;
  }

  /**
   * TODO: 
   * Implement this
   */
  public ArrayList<ArrayList<Implication>> rationalClosure(ArrayList<Implication> kb){
    ArrayList<ArrayList<Implication>> kbRankings = new ArrayList<ArrayList<Implication>>();

    kbRankings.add(kb);

    return kbRankings;
  }


  public BNGraph supplementNetwork(BNGraph graph, ArrayList<Implication> kb){
    

    return graph;
  }


}
