package edu.uct.ibn.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.swing.*;
import edu.uct.ibn.bayesnet.BNGraph;
import edu.uct.ibn.implication.Implication;

public class IBNObservationPanel extends JPanel {

	private BNGraph graph;
	private JTextArea textField;
	

	public IBNObservationPanel(){
		this(null);
	}

	public IBNObservationPanel(BNGraph g){
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