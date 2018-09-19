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
