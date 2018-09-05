package edu.uct.ibn.gui;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

public class IBNGUI extends JFrame{

	public IBNGUI(){
		super();
		init();
	}

	private void init(){
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		setTitle("IBN");
		setSize(1000, 600);
		setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

		IBNMainPanel mainPanel = new IBNMainPanel();
		contentPane.add(mainPanel, "Center");
	}

	public static void main(String[] args) {
		IBNGUI gui = new IBNGUI();
		gui.setVisible(true);
	}

}