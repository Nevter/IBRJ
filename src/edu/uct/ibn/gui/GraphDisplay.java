package edu.uct.ibn.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


import edu.uct.ibn.BNGraph;

public class GraphDisplay extends JFrame {

  BNGraph graph = null;

  public GraphDisplay(){}

  public GraphDisplay(BNGraph graph){
    System.out.println("Display Graph");
    this.graph = graph;
    //init();
  }

  private void init(){
    System.out.println("GraphDisplay init");

    setTitle("Graph");
    setSize(800,600);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    JPanel contentPane = new JPanel();
    contentPane.setLayout(new BorderLayout());
    setContentPane(contentPane);

    setVisible(true);
  }

  public String toString(){
    return "GraphDisplay";
  }


}
