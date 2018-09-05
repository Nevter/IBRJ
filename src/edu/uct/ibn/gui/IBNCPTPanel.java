package edu.uct.ibn.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.swing.*;

public class IBNCPTPanel extends JPanel{

  public IBNCPTPanel(){
    super();
    init();
  }

  private void init(){
    setLayout(new BorderLayout());
    JLabel textLabel = new JLabel("The CPT Panel");
    add(textLabel, BorderLayout.CENTER);

		setOpaque(true);
		setBackground(Color.BLUE);
  }
}
