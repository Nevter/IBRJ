package edu.uct.ibn.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.swing.*;

import edu.uct.ibn.bayesnet.BNGraph;
import edu.uct.ibn.implication.Implication;

public class IBNImplicationPanel extends JPanel {

	private BNGraph graph;

	public IBNImplicationPanel(BNGraph g){
		super();
		graph = g;
		init();
	}

	private void init(){
		JLabel textLabel = new JLabel("The Implication Panel");
		add(textLabel, BorderLayout.CENTER);
		setOpaque(true);
		setBackground(Color.CYAN);
	}

	public void setGraph(BNGraph graph){
		this.graph = graph;
	}
	
	public void reloadImplications(){
		ArrayList<Implication> kb = graph.getKnowledgebase();
		for (Implication impl : kb){
			add(new JLabel(impl.toString()), BorderLayout.CENTER);
		}
	}
}