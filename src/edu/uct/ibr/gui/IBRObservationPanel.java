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