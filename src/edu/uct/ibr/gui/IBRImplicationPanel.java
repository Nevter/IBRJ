package edu.uct.ibr.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.swing.*;

import edu.uct.ibr.bayesnet.BNGraph;
import edu.uct.ibr.implication.Implication;

public class IBRImplicationPanel extends JPanel {

	private BNGraph graph;
	private JTextArea textField;

	public IBRImplicationPanel(BNGraph g){
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