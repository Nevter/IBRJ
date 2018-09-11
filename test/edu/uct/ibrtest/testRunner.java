package edu.uct.ibrtest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class testRunner {
    public static void main(String[] args) {
        System.out.println("~~~ Running Tests ~~~");

        System.out.println("testBNInference:");
        Result result = JUnitCore.runClasses(edu.uct.ibrtest.testBNInference.class);
        printResult(result);

    }


    public static void printResult(Result result) {
        System.out.printf("Test ran: %s, Failed: %s%n", 
                          result.getRunCount(), result.getFailureCount());    
    }

}  	 