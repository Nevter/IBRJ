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

package edu.uct.ibr;

import edu.uct.ibr.cli.IBRCLI;
import edu.uct.ibr.gui.IBRGUI;

/**
 * TODO:
 *  * Add method descriptors  
 */

public class IBR {

  public static void main(String[] args) {
    if (args.length > 0){
      if (args[0].equals("-cli")) IBRCLI.run();
      else if (args[0].equals("-gui")) IBRGUI.main(new String[0]);
      else help();
    }
    String type = io.input("(g)ui, (c)li, (h)elp");
    switch(type){
      case "g": IBRGUI.main(new String[0]); break;
      case "c": IBRCLI.run(); break;
      case "h": 
      default: help(); break;
    }
  }  

  public static void help(){
    System.out.println("There will be some help info here at some point");
  }
}
