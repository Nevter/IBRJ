package edu.uct.ibr.util;

import java.util.Scanner;
import java.util.ArrayList;;

public class io{

  private static final String ln = System.getProperty("line.separator");
  private static Scanner inputScanner = new Scanner(System.in);


  /**
   * Get lower case String input
   */
  public static String input(){
    return rawInput().toLowerCase();
  }
  
  /**
   * get raw input from the user as a String
   */
  public static String rawInput(){
    return inputScanner.next();
  }

  /**
   * Get lower case String input from a user after printing out a user prompt
   */
  public static String input(String inputPrompt){
    output(inputPrompt);
    outputNoLine("> ");
    return input();
  }

  /**
   * Print an object toString to the console on a new line
   */
  public static void output(Object output){
    System.out.println(output);
  }

  /**
   * Print an object toString to the console without printing a new line
   */
  public static void outputNoLine(Object output){
    System.out.print(output);
  }

  /**
   * Print the contents of an array to the console with each element on a new line
   */
  public static void output(ArrayList<String> output){
    for (String o : output){
      output(o);
    }
  }

}
