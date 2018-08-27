package edu.uct.ibn;


import edu.ksu.cis.bnj.bbn.inference.elimbel.*;
import edu.ksu.cis.bnj.bbn.inference.*;

import java.util.ArrayList;

import edu.uct.ibn.util.io;
import edu.uct.ibn.implication.*;
import edu.uct.ibn.BNNode.Relationship;



public class BNInference {

 
  public static ArrayList<InferenceResult> getMarginals(BNGraph graph){
    
    ArrayList<InferenceResult> results = new ArrayList<InferenceResult>();

    ArrayList<Implication> kb = graph.getKnowledgebase();
    
    ArrayList<ArrayList<Implication>> rankings = rationalClosure(kb);
    
    for (ArrayList<Implication> kbRank : rankings){
      BNGraph graphDash = supplementNetwork(graph, kbRank);
      ElimBel variableElimination = new ElimBel(graphDash.getBBNGraph());
      results.add(variableElimination.getMarginals());
    }

    return results;
  }

  /**
   * TODO: 
   * Implement this
   */
  public static ArrayList<ArrayList<Implication>> rationalClosure(ArrayList<Implication> kb){
    ArrayList<ArrayList<Implication>> kbRankings = new ArrayList<ArrayList<Implication>>();

    kbRankings.add(kb);

    return kbRankings;
  }


  private static BNGraph supplementNetwork(BNGraph graph, ArrayList<Implication> kb){
    
    BNGraph graphDash = graph;


    for (Implication impl : kb){
      graphDash = impl.supplementNetwork(graphDash); 
    }

    return graphDash;
  }


}
