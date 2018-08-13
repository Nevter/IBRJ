package edu.uct.dbr;

import edu.uct.dbr.implication.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

import edu.ksu.cis.bnj.bbn.BBNGraph;
import edu.ksu.cis.bnj.bbn.BBNNode;

public class BNGraph {

  private BBNGraph graph = null;
  private String path = null;
  private ArrayList<Implication> implication = new ArrayList<Implication>();

  public BNGraph(String path){
    graph = new BBNGraph();
    this.path = path;
    load(path);
  }

  public void load(String path){
    graph = graph.load(path);
  }

  public BNNode getNode(String nodeName){
    Set nodes = getGraphNodes();

    for (Iterator i=nodes.iterator(); i.hasNext(); ) {
        BNNode n = (BNNode) i.next();
        if (n.getName().toLowerCase().equals(nodeName.toLowerCase())){
          return n;
        }
    }

    return null;
  }

  public Set getGraphNodes(){
    HashSet nodes = new HashSet();

    for (Iterator i = graph.getNodes().iterator(); i.hasNext(); )
    {
        BBNNode n = (BBNNode) i.next();
        nodes.add(new BNNode(n));
    }

    return nodes;
  }

  public BBNGraph getBBNGraph(){
    return graph;
  }

  public String getName(){
    return graph.getName();
  }

  public void addImplicationStatement(Implication impl){
    implication.add(impl);
  }

  public ArrayList<Implication> getImplicationStatements(){
    return implication;
  }

  public String toString(){
    return graph.toString();
  }

}
