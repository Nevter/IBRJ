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

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Iterator;
import java.util.Objects;

import edu.ksu.cis.bnj.bbn.BBNNode;
import edu.ksu.cis.bnj.bbn.BBNGraph;
import edu.ksu.cis.bnj.bbn.BBNValue;
import edu.ksu.cis.bnj.bbn.BBNCPF;
import edu.ksu.cis.bnj.bbn.BBNDiscreteValue;

public class BNNode {

  BBNNode node = null;
  public enum Relationship {PARENT, CHILD, ANCESTOR, DESCENDANT, SELF, NONE};


  public BNNode(BBNNode node){
    this.node = node;
  }

  public BNNode(BNGraph graph, String nodeName){
    this(new BBNNode(graph.getBBNGraph(),nodeName));
  }

  /**
   * Get the name of this name
   * @return
   */
  public String getName(){
    if (node != null) return node.getName();
    else return "";
  }

  /**
   * Get the BBNNode
   * @return
   */
  public BBNNode getNode(){
    return node;
  }

  /**
   * Get the CPT of this table
   * @return
   */
  public BBNCPF getCPF(){
    return node.getCPF();
  }

  /**
   * Get the possible values this node can take
   * @return
   */
  public BBNDiscreteValue getPossibleValues(){
    BBNDiscreteValue values = (BBNDiscreteValue) node.getValues();
    return values;
  }

  /**
   * Get the possible values this node can take as a string
   * @return
   */
  public String getPossibleValuesOutput(){
    return node.getValues().toString();
  }

  /**
   * Get the truth value name of this node
   * @return
   */
  public String getTruthValueName(){
    BBNDiscreteValue values = (BBNDiscreteValue) node.getValues();
    return values.toArray()[0].toString();
  }

  /**
   * Get the false value name of this node
   * @return
   */
  public String getFalseValueName(){
    BBNDiscreteValue values = (BBNDiscreteValue) node.getValues();
    return values.toArray()[1].toString();
  }

  /**
   * Get the observed value name of this node
   * @return
   */
  public String getObservedValue(){
    if (isObserved()) return node.getEvidenceValue().toString();
    else return null;
  }

  /**
   * check if this node has been observerd
   * @return
   */
  public boolean isObserved(){
    return node.isEvidence();
  }

  /**
   * Observe this node
   * @param valueName
   */
  public void observe(String valueName){
    BBNDiscreteValue values = getPossibleValues();
    for (Iterator itr = values.iterator(); itr.hasNext(); ){
      String val = itr.next().toString();
      if (val.toLowerCase().equals(valueName.toLowerCase())){
        node.setEvidenceValue(val);
      }
    }
  }

  /**
   * Get the children of this node
   * @return
   */
  public Set getChildren(){
    List<BBNNode> childrenBBNNode = node.getChildren();
    HashSet children = new HashSet();
    for (BBNNode n : childrenBBNNode){
      children.add(new BNNode(n));
    }
    return children;
  }

  /**
   * Get the parents of this node
   * @return
   */
  public Set<BNNode> getParents(){
    List<BBNNode> parentsBBNNode = node.getParents();
    HashSet<BNNode> parents = new HashSet();
    for (BBNNode n : parentsBBNNode){
      parents.add(new BNNode(n));
    }
    return parents;
  }

  /**
   * Get the ancestors of this node (does not include parents)
   * @return
   */
  public Set getAncestors(){
    Set<BBNNode> ancestorsBBNNode = node.getAncestors();
    HashSet ancestors = new HashSet();
    for (BBNNode n : ancestorsBBNNode){
      ancestors.add(new BNNode(n));
    }
    Set parents = getParents();
    ancestors.removeAll(parents);
    return ancestors;
  }

  /**
   * Get the descendants of this node (does not include children)
   * @return
   */
  public Set getDescendants(){
    Set<BBNNode> descendantsBBNNode = node.getDescendants();
    HashSet descendants = new HashSet();
    for (BBNNode n : descendantsBBNNode){
      descendants.add(new BNNode(n));
    }
    Set children = getChildren();
    descendants.removeAll(children);
    return descendants;
  }

  /**
   * Get a verbose output string
   * @return
   */
  public String toVerboseString(){
    return node.toVerboseString();
  }

  @Override
  public String toString(){
    return getName();
  }

  public boolean equals(BNNode o){
    return node.equals(o.getNode());
  }
  
  @Override
  public boolean equals(Object anObject) {
    if (!(anObject instanceof BNNode)) {
        return false;
    }
    BNNode otherMember = (BNNode)anObject;
    return otherMember.toVerboseString().equals(toVerboseString());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), isObserved());
  }

}
