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

  public static final String GUI_HELP = "Click \"Load Network\" to load a network\n click draw inference to draw inference";
  
  /**
   * Get the menu instruction text
   */
  public static String menuText(String networkName){
    String menuText = ln+ln+"~IBN Menu~"+
                      ln+"Network loaded: "+networkName+
                      ln+"(l)oad a BN, draw (i)nference, (a)dd implication, (o)bserve, "+
                      ln+"view (im)plication, view (C)PT, view (g)raph, (q)uit";
    return menuText;
  }



}
