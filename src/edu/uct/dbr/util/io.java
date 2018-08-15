package edu.uct.dbr.util;

import java.util.Scanner;
import java.io.*;
import java.util.*;

public class io{

  private static final String ln = System.getProperty("line.separator");
  private static Scanner inputScanner = new Scanner(System.in);


  public static String input(){
    return inputScanner.next().toLowerCase();
  }

  public static void output(Object output){
    System.out.print(output);
  }

}
