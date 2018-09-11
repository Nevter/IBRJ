package edu.uct.ibr.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.swing.*;
import edu.uct.ibr.bayesnet.BNGraph;
import edu.uct.ibr.implication.Implication;

public class IBRObservationPanel extends JPanel {

	private BNGraph graph;
	private JTextArea textField;
	

	public IBRObservationPanel(){
		this(null);
	}

	public IBRObservationPanel(BNGraph g){
		super();
		graph = g;
		init();
	}

	public void setGraph(BNGraph graph){
		this.graph = graph;
	}
	
	private void init(){
		setLayout(new BorderLayout());
		JLabel textLabel = new JLabel("Observations");
		add(textLabel, BorderLayout.NORTH);
		
		textField = new JTextArea();
		textField.setEditable(false);
		add(textField, BorderLayout.CENTER);
		
	}

	public void updateObservations(){
		textField.setText("");
		ArrayList<String> observations = graph.getObservations();
		for (String o : observations){
			textField.append(o + "\n");
		}
	}
}