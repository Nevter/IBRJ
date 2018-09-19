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

package edu.uct.ibr.implication;

import edu.uct.ibr.bayesnet.BNNode;
import edu.uct.ibr.bayesnet.BNNode.Relationship;
import edu.uct.ibr.bayesnet.BNGraph;

public abstract class Implication {
  BNNode antecedentNode = null;
  BNNode consequentNode = null;
  Relationship relationship = null;
  BNGraph graph = null;


  public Implication(BNNode antecedentNode, BNNode consequentNode, BNGraph graph){
    this.antecedentNode = antecedentNode;
    this.consequentNode = consequentNode;
    this.relationship = graph.getRelationship(antecedentNode, consequentNode);
    this.graph = graph;
  }

  
  public BNNode getAntecedentNode(){
    return antecedentNode;
  }
  
  public BNNode getConsequentNode(){
    return consequentNode;
  }
  
  public Relationship getRelationship(){
    return relationship;
  }
  
  public abstract boolean supplementNetwork();

  public abstract String toString();

}
