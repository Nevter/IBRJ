package edu.uct.ibn.util;

import java.util.Scanner;
import java.io.*;
import java.util.*;

public class message{

  private static final String ln = System.getProperty("line.separator");


  public static String menuText(String networkName){
    String menuText = ln+"~IBN Menu~"+
                      ln+"Network loaded: "+networkName+
                      ln+"(l)oad a BN, draw (i)nference, (a)dd implication, (o)bserve, "+
                      ln+"view (im)plication, view (C)PT, view (g)raph, (q)uit";
    return menuText;
  }



}
