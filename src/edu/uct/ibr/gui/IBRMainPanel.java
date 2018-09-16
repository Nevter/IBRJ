package edu.uct.ibr.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.swing.*;

import edu.uct.ibr.implication.*;
import edu.uct.ibr.util.*;
import edu.uct.ibr.bayesnet.BNNode.Relationship;
import edu.uct.ibr.bayesnet.*;


public class IBRMainPanel extends JPanel {
	
	protected IBRGraphPanel graphPanel = null;
	protected BNGraph graph = null;
	protected IBRImplicationPanel implPanel = null;
	protected IBRObservationPanel obsPanel = null;

	Button loadNetworkBtn = null;
	Button drawInferenceBtn = null;
	Button addImplicationBtn = null;
	Button addObservationBtn = null;
	Button helpBtn = null;
	Button cptBtn = null;

	public IBRMainPanel(){
		super();
		init();
	}

	private void init(){
		setLayout(new BorderLayout());

		JPanel buttonPanel = createButtonPanel();
		add(buttonPanel, BorderLayout.NORTH);

		graphPanel = new IBRGraphPanel();

		add(graphPanel, BorderLayout.CENTER);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(2,1));
		
		
		obsPanel = new IBRObservationPanel();
		infoPanel.add(obsPanel, BorderLayout.NORTH);
		
		implPanel = new IBRImplicationPanel(graph);
		infoPanel.add(implPanel, BorderLayout.CENTER);


		add(infoPanel, BorderLayout.EAST);

	}

	private void setGraph(){
		graphPanel.setGraph(graph);
		
		implPanel.setGraph(graph);
		obsPanel.setGraph(graph);
		drawInferenceBtn.setEnabled(true);
		addImplicationBtn.setEnabled(true);
		addObservationBtn.setEnabled(true);
		cptBtn.setEnabled(true);
	}

	private JPanel createButtonPanel(){
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,6));
	
		loadNetworkBtn = new Button("Load a network");
		loadNetworkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String filePath = (String) JOptionPane.showInputDialog(null,"Enter path to file to load:","Load Network", JOptionPane.DEFAULT_OPTION, null, null, "./examples/asia/asia.bif");
				if (filePath == null){return;}
				try{
					graph = new BNGraph(filePath);
				} catch(Exception e){
					JOptionPane.showMessageDialog(null, "File not found");
				}
				setGraph();
			}
		});
		cptBtn = new Button("View a CPT");
		cptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				ArrayList<String> nodes = graph.getNodeNames();
				String nodeNames[] = nodes.toArray(new String[0]);
				String nodeName = (String) JOptionPane.showInputDialog(null, "Select node", "View CPT", JOptionPane.QUESTION_MESSAGE, null, nodeNames, nodeNames[0]);
				if (nodeName == null){return;}
				BNNode node = graph.getNode(nodeName);
				String nodeCPT = node.getCPF().toString();
				JOptionPane.showMessageDialog(null, nodeCPT);

			}
		});
		drawInferenceBtn = new Button("Draw Inference");
		drawInferenceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
					String result = BNInference.getMarginalsOutput(graph);
					JOptionPane.showMessageDialog(null, result);
			}
		});
		addImplicationBtn = new Button("Add Implication");
		addImplicationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {				
				ArrayList<String> nodes = graph.getNodeNames();

				String nodeNames[] = nodes.toArray(new String[0]);
				String antecedent = (String) JOptionPane.showInputDialog(null, "Select antecedent node", "Add implication", JOptionPane.QUESTION_MESSAGE, null, nodeNames, nodeNames[0]);
				if (antecedent == null){return;}
				String consequent = (String) JOptionPane.showInputDialog(null, "Select consequent node", "Add implication", JOptionPane.QUESTION_MESSAGE, null, nodeNames, nodeNames[0]);
				if (consequent == null){return;}
				BNNode antecedentNode = graph.getNode(antecedent);
				BNNode consequentNode = graph.getNode(consequent);
			
				Implication impl = new ClassicalImplication(antecedentNode, consequentNode, graph);

				if (!graph.addImplicationStatement(impl)){
					JOptionPane.showMessageDialog(null, "Implication could not be added");
				}

				implPanel.reloadImplications();

			}
		});
		addObservationBtn = new Button("Add Observation");
		addObservationBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				String observationTypes[] = {"Probabilistic","Logical"};
				String obsType = (String) JOptionPane.showInputDialog(null, "Select observation type", "Observe Node", JOptionPane.QUESTION_MESSAGE, null, observationTypes, observationTypes[0]);
				if (obsType == null){return;}

				if (obsType == observationTypes[0]){
					ArrayList<String> nodes = graph.getNodeNames();
					String nodeNames[] = nodes.toArray(new String[0]);
					String nodeToObserve = (String) JOptionPane.showInputDialog(null, "Select node to observe", "Observe Node", JOptionPane.QUESTION_MESSAGE, null, nodeNames, nodeNames[0]);
					if (nodeToObserve == null){return;}
					BNNode node = graph.getNode(nodeToObserve);
					String values[] = {node.getTruthValueName(), node.getFalseValueName()};
					String observationValue = (String) JOptionPane.showInputDialog(null, "Select observation value", "Observe Node", JOptionPane.QUESTION_MESSAGE, null, values, values[0]);
					if (observationValue == null){return;}
					node.observe(observationValue);
				}
				if (obsType == observationTypes[1]){
					ArrayList<String> nodes = graph.getNodeNames();

					String nodeNames[] = nodes.toArray(new String[0]);
					String antecedent = (String) JOptionPane.showInputDialog(null, "Select antecedent node", "Add implication", JOptionPane.QUESTION_MESSAGE, null, nodeNames, nodeNames[0]);
					if (antecedent == null){return;}
					String consequent = (String) JOptionPane.showInputDialog(null, "Select consequent node", "Add implication", JOptionPane.QUESTION_MESSAGE, null, nodeNames, nodeNames[0]);
					if (consequent == null){return;}
					BNNode antecedentNode = graph.getNode(antecedent);
					BNNode consequentNode = graph.getNode(consequent);
				
					Implication impl = new ClassicalImplication(antecedentNode, consequentNode, graph);

					graph.observe(impl);

				}

				obsPanel.updateObservations();
			}
		});
		helpBtn = new Button("Help");
		helpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(null, message.GUI_HELP);
			}
		});
		
		addObservationBtn.setEnabled(false);
		addImplicationBtn.setEnabled(false);
		drawInferenceBtn.setEnabled(false);
		cptBtn.setEnabled(false);
		buttonPanel.add(loadNetworkBtn);
		buttonPanel.add(cptBtn);
		buttonPanel.add(drawInferenceBtn);
		buttonPanel.add(addImplicationBtn);
		buttonPanel.add(addObservationBtn);
		buttonPanel.add(helpBtn);

		return buttonPanel;
	}

}