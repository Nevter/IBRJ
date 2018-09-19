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

package edu.uct.ibr.util;

public class message{

  private static final String ln = System.getProperty("line.separator");

  public static final String CLI_HEADER =  "~~~~~~~~ Welcome to IBN CLI ~~~~~~~~~~\n"+
                                            "~~~ A bayesian reasoner with logic ~~~";
  public static final String LINE = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
  public static final String ERROR_NO_GRAPH = "No network loaded";
  public static final String ERROR_BAD_INPUT = "User input not recognised";
  public static final String ERROR_BAD_FILE_PATH = "Something went wrong with opening that file. Is the path correct?";
  public static final String ERROR_BAD_NODE_NAME = "Cannot find node with name ";
  public static final String ERROR_RELATIONSHIP_SELF = "Cannot add implication statement to same node";
  public static final String INPUT_FILE_PATH = "Enter the path to the file to load";
  public static final String INPUT_NODE_NAME = "Enter node name";
  public static final String INPUT_ANTECEDENT_NODE_NAME = "Enter antecedent node name";
  public static final String INPUT_CONSEQUENT_NODE_NAME = "Enter consequent node name";
  public static final String INPUT_IMPLICATION_TYPE = "(c)lassical or (d)efeasible implication?";
  public static final String INPUT_OBSERVED_VALUE = "Enter observed value";
  public static final String CLI_OBSERVATION_TYPE ="(l)ogical or (p)robabilistic observation?";
  public static final String OUTPUT_FILE_PATH = "Enter the output file name";
  public static final String GUI_HELP = ""+
        "Use the top panel buttons to perform actions on the network.\n"+
        "The right side panels show information about the network such\n"+
        "as implications added and observations made.\n"+
        "Network nodes can be moved by clicking and dragging them.";
  /**
   * Get the menu instruction text
   */
  public static String menuText(String networkName){
    String menuText = ln+ln+"~IBN Menu~"+
                      ln+"Network loaded: "+networkName+
                      ln+"(l)oad a BN, (s)ave, draw (i)nference, (a)dd implication, (o)bserve, "+
                      ln+"view (im)plication, view (C)PT, view (g)raph, (q)uit";
    return menuText;
  }



}
