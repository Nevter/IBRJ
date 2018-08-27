package edu.uct.ibn.util;

import edu.ksu.cis.bnj.bbn.inference.InferenceResult;

import java.util.Scanner;
import java.io.*;
import java.util.*;

public class io{

  private static final String ln = System.getProperty("line.separator");
  private static Scanner inputScanner = new Scanner(System.in);


  public static String input(){
    return inputScanner.next().toLowerCase();
  }
  public static String rawInput(){
    return inputScanner.next();
  }

  public static void output(Object output){
    System.out.print(output);
  }

  public static void output(ArrayList<InferenceResult> results){
    if (results.isEmpty()){
      return;
    }
    else if (results.size() == 1){
      output(results.get(0));
    }
    else {
      output("Marginals in order of typicality:");
      for (InferenceResult r : results){
        output(r);
      }
    }
  }

}
