package edu.uct.dbr;

import edu.ksu.cis.bnj.bbn.BBNNode;

public class BNNode {

  BBNNode node = null;

  public BNNode(BBNNode node){
    this.node = node;
  }

  public String getName(){
    if(node != null) return node.getName();
    else return "";
  }

  public String toString(){
    return getName();
  }

}
