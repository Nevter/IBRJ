package edu.uct.dbr;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

import edu.ksu.cis.bnj.bbn.BBNNode;

public class BNNode {

  BBNNode node = null;
  enum Relationship {PARENT, CHILD, ANCESTOR, DESCENDANT, SELF, NONE};

  public BNNode(BBNNode node){
    this.node = node;
  }

  public String getName(){
    if (node != null) return node.getName();
    else return "";
  }

  public BBNNode getNode(){
    return node;
  }

  public String toString(){
    return getName();
  }

  public boolean equals(BNNode o){
    return node.equals(o.getNode());
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

  public Set getParents(){
    List<BBNNode> parentsBBNNode = node.getParents();
    HashSet parents = new HashSet();
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
}
