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