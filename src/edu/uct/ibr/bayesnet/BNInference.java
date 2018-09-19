/*
 * This file is part of Implicative Bayesian Reasoner for Java (IBRJ).
 *
 * IBRJ is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either v3.0 of the License, or
 * (at your option) any later version.
 *
 * IBRJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License v3.0
 * along with IBRJ in LICENSE.txt file; if not, refer to the GNU GPL website.
 */

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
