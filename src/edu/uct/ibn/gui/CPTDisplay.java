package edu.uct.ibn.gui;

import edu.uct.ibn.BNGraph;

public class CPTDisplay{
  BNGraph graph = null;

  public CPTDisplay(BNGraph graph){
    System.out.println("Display CPT");
    this.graph = graph;
  }

  public String toString(){
    return "CPTDisplay";
  }

}
