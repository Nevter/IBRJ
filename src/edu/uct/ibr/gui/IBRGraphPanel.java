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

package edu.uct.ibr.gui;


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.swing.*;

import edu.ksu.cis.bnj.gui.components.*;
import edu.uct.ibr.bayesnet.BNGraph;
import salvo.jesus.graph.*;
import salvo.jesus.graph.visual.*;



public class IBRGraphPanel extends GraphScrollPane {

  protected GraphPanel graphPanel;
  protected VisualGraph vGraph;
  protected VariablePainterFactory varPainterFactory = new VariablePainterFactory();
  protected EdgePainterFactory edgePainterFactory = new EdgePainterFactory();
  protected ChanceVariableState randomVarState;
  protected DecisionVariableState decisionVarState;
  protected UtilityVariableState utilityVarState;
  protected EdgeState edgeState;
  protected NormalState normalState;
  protected AutoLayouter autoLayouter;
  protected DefaultLayouter defaultLayouter;
  protected BNJMainPanel owner = null;
  protected NodeManager nodeManager;
  protected static Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);

  public IBRGraphPanel( ){
    super();
    init();
  }

  private void init(){
    setSize(700, 500);
    graphPanel = (GraphPanel) getViewport().getView();
    vGraph = super.getVisualGraph();
    //nodeManager = new NodeManager(vGraph);
    //nodeManager.addListener(this);
    vGraph.setVisualEdgePainterFactory(edgePainterFactory);
    vGraph.setVisualVertexPainterFactory(varPainterFactory);
    defaultLayouter = new DefaultLayouter(vGraph);
    autoLayouter = new AutoLayouter(vGraph);
    normalState = new NormalState(nodeManager, graphPanel);
    edgeState = new EdgeState(nodeManager, graphPanel);
    randomVarState = new ChanceVariableState(nodeManager, graphPanel);
    decisionVarState = new DecisionVariableState(nodeManager, graphPanel);
    utilityVarState = new UtilityVariableState(nodeManager, graphPanel);
    changeToNormalState();
  }
  public void changeToNormalState() {
    processChangeStateEvent(new ChangeStateEvent(this, normalState));
  }

  public void defaultLayout() {
    vGraph.setGraphLayoutManager(defaultLayouter);
    vGraph.layout();
}

  public void setGraph(BNGraph g) {
    vGraph.setGraph(g.getBBNGraph());
    defaultLayout();
    randomVarState.resetCounter();
    decisionVarState.resetCounter();
    utilityVarState.resetCounter();
}

}
