package edu.uct.dbr;

import edu.uct.dbr.implication.*;

import java.util.ArrayList;
import edu.ksu.cis.bnj.bbn.BBNGraph;

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
