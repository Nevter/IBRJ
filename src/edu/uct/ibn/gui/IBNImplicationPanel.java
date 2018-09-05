package edu.uct.ibn.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.swing.*;


public class IBNImplicationPanel extends JPanel {

	public IBNImplicationPanel(){
		super();
		init();
	}

	private void init(){
		JLabel textLabel = new JLabel("The Implication Panel");
		add(textLabel, BorderLayout.CENTER);

		setOpaque(true);
		setBackground(Color.CYAN);
	}
}