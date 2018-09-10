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
	private JTextArea textField;

	public IBNImplicationPanel(BNGraph g){
		super();
		graph = g;
		init();
	}

	private void init(){
		setLayout(new BorderLayout());
		JLabel textLabel = new JLabel("Implications");
		add(textLabel, BorderLayout.NORTH);
		
		textField = new JTextArea();
		textField.setEditable(false);
		add(textField, BorderLayout.CENTER);
	
	}

	public void setGraph(BNGraph graph){
		this.graph = graph;
	}
	
	public void reloadImplications(){
		textField.setText("");
		ArrayList<Implication> kb = graph.getKnowledgebase();
		for (Implication impl : kb){
			textField.append(impl.toString()+"\n");
		}
	}
}