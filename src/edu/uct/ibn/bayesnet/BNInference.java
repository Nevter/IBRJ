package edu.uct.ibn.bayesnet;


import edu.ksu.cis.bnj.bbn.inference.elimbel.*;
import edu.ksu.cis.bnj.bbn.inference.*;

import java.util.ArrayList;

import edu.uct.ibn.util.io;
import edu.uct.ibn.implication.*;
import edu.uct.ibn.bayesnet.BNNode.Relationship;



public class BNInference {

  
  public static ArrayList<String> getMarginalsOutput(BNGraph graph){
    ArrayList<InferenceResult> results = getMarginals(graph);
    ArrayList<String> resultOutput = new ArrayList<String>();

    if (results.size() == 1){
      resultOutput.add(results.get(0).toString());
    }
    else{
      resultOutput.add("Marginals in order of typicality:");
      int worldRank = 1;
      for (InferenceResult r : results){
        resultOutput.add("World Rank: "+worldRank++);
        resultOutput.add(r.toString());
      }
    }

    return resultOutput;
  }


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
