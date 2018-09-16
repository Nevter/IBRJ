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

  public String getName(){
    if (node != null) return node.getName();
    else return "";
  }

  public BBNNode getNode(){
    return node;
  }

  public BBNCPF getCPF(){
    return node.getCPF();
  }

  public String toString(){
    return getName();
  }

  public boolean equals(BNNode o){
    return node.equals(o.getNode());
  }

  public BBNDiscreteValue getPossibleValues(){
    BBNDiscreteValue values = (BBNDiscreteValue) node.getValues();
    return values;
  }

  public String getPossibleValuesOutput(){
    return node.getValues().toString();
  }

  public String getTruthValueName(){
    BBNDiscreteValue values = (BBNDiscreteValue) node.getValues();
    return values.toArray()[0].toString();
  }

  public String getFalseValueName(){
    BBNDiscreteValue values = (BBNDiscreteValue) node.getValues();
    return values.toArray()[1].toString();
  }

  public void observe(String valueName){
    BBNDiscreteValue values = getPossibleValues();
    for (Iterator itr = values.iterator(); itr.hasNext(); ){
      String val = itr.next().toString();
      if (val.toLowerCase().equals(valueName.toLowerCase())){
        node.setEvidenceValue(val);
      }
    }
  }

  public String getObservedValue(){
    if (isObserved()) return node.getEvidenceValue().toString();
    else return null;
  }

  public boolean isObserved(){
    return node.isEvidence();
  }

  public String toVerboseString(){
    return node.toVerboseString();
  }

  public Set getChildren(){
    List<BBNNode> childrenBBNNode = node.getChildren();
    HashSet children = new HashSet();
    for (BBNNode n : childrenBBNNode){
      children.add(new BNNode(n));
    }
    return children;
  }

  public Set<BNNode> getParents(){
    List<BBNNode> parentsBBNNode = node.getParents();
    HashSet<BNNode> parents = new HashSet();
    for (BBNNode n : parentsBBNNode){
      parents.add(new BNNode(n));
    }
    return parents;
  }

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
