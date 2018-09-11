package edu.uct.ibr.gui;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

public class IBRGUI extends JFrame{

	public IBRGUI(){
		super();
		init();
	}

	private void init(){
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		setTitle("IBR");
		setSize(1000, 600);
		setLocationRelativeTo(null);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		IBRMainPanel mainPanel = new IBRMainPanel();
		contentPane.add(mainPanel, "Center");
	}

	public static void main(String[] args) {
		IBRGUI gui = new IBRGUI();
		gui.setVisible(true);
	}

}