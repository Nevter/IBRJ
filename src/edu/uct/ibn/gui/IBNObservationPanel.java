package edu.uct.ibn.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.swing.*;


public class IBNObservationPanel extends JPanel {

	public IBNObservationPanel(){
		super();
		init();
	}

	private void init(){
		JLabel textLabel = new JLabel("The Observations Panel");
		add(textLabel, BorderLayout.CENTER);

		setOpaque(true);
		setBackground(Color.GREEN);
	}

	public void updateObservations(){
		System.out.println("put observations in here");
	}
}