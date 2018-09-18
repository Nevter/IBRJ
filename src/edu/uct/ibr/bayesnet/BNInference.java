package edu.uct.ibr.bayesnet;

import edu.uct.ibr.util.Entailment;;

import edu.ksu.cis.bnj.bbn.inference.elimbel.*;
import edu.ksu.cis.bnj.bbn.inference.*;

/**
 * TODO: Extend to allow for other algorithms 
 * Do this by having a static variable that determins what algorithm type to use. 
 * have a method that allows this to be changed/set
 * when marginals are requested it uses whatever algo type is defined by the variable
 */

public class BNInference {

  /**
   * Get the prior/ posterior Marginals by using Variable Elimination
   * @param graph
   * @return InferenceResult
   */
  public static InferenceResult getMarginals(BNGraph graph){

    //check if all logical observations are entailed by the Knowledge Base. 
    //if they are, use the full IBN
    if (Entailment.entails(graph.getKnowledgebase(), graph.getLogicalObservations())){

      ElimBel variableElimination = new ElimBel(graph.getBBNGraph());
      return variableElimination.getMarginals();
    }
    //if they aren't, use the BotGraph
    else {
      ElimBel variableElimination = new ElimBel(graph.getBotBBNGraph());
      return variableElimination.getMarginals();
    }
  }
  
  /**
   * Get the prior/ posterior Marginals as a String, used for output
   * @param graph
   * @return String
   */
  public static String getMarginalsOutput(BNGraph graph){
    
    InferenceResult result = getMarginals(graph);
    return result.toString();
  }


}
