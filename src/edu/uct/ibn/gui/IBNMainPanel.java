package edu.uct.ibn.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.swing.*;

import edu.uct.ibn.implication.*;
import edu.uct.ibn.util.*;
import edu.uct.ibn.bayesnet.BNNode.Relationship;
import edu.uct.ibn.bayesnet.*;


public class IBNMainPanel extends JPanel {
	
	protected IBNGraphPanel graphPanel = null;
	protected BNGraph graph = null;

	public IBNMainPanel(){
		super();
		init();
	}

	private void init(){
		setLayout(new BorderLayout());

		JPanel buttonPanel = createButtonPanel();
		add(buttonPanel, BorderLayout.NORTH);

		graphPanel = new IBNGraphPanel();

		add(graphPanel, BorderLayout.CENTER);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.add(new IBNObservationPanel(), BorderLayout.NORTH);
		infoPanel.add(new IBNImplicationPanel(), BorderLayout.CENTER);
		infoPanel.add(new IBNCPTPanel(), BorderLayout.SOUTH);

		add(infoPanel, BorderLayout.EAST);

	}

	private JPanel createButtonPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,5));
	
		Button loadNetworkBtn = new Button("Load a network");
		loadNetworkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
					System.out.println("Clicked the Load a network button");
					graph = new BNGraph("./examples/asia/asia.bif");
					graphPanel.setGraph(graph);
		}
		});
		Button drawInferenceBtn = new Button("Draw Inference");
		drawInferenceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
					System.out.println("Clicked the draw inference button");
		}
		});
		Button addImplicationBtn = new Button("Add Implication");
		addImplicationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
					System.out.println("Clicked the Add Implication button");
		}
		});
		Button addObservationBtn = new Button("Add Observation");
		addObservationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
					System.out.println("Clicked the Add Observation button");
		}
		});
		Button helpBtn = new Button("Help");
		helpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
					System.out.println("Clicked the help button");
		}
		});
		buttonPanel.add(loadNetworkBtn);
		buttonPanel.add(drawInferenceBtn);
		buttonPanel.add(addImplicationBtn);
		buttonPanel.add(addObservationBtn);
		buttonPanel.add(helpBtn);

		return buttonPanel;
	}

}