package edu.uct.ibrtest;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class testRunner {
    public static void main(String[] args) {
        System.out.println("~~~ Running Tests ~~~");

        Result result = null;

        System.out.println("testIBNUtil:");
        result = JUnitCore.runClasses(edu.uct.ibrtest.testIBRUtil.class);
        printResult(result);


        System.out.println("testBNNode:");
        result = JUnitCore.runClasses(edu.uct.ibrtest.testBNNode.class);
        printResult(result);

        System.out.println("testBNGraph:");
        result = JUnitCore.runClasses(edu.uct.ibrtest.testBNGraph.class);
        printResult(result);

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("IBRJ Integration Tests");
        result = JUnitCore.runClasses(edu.uct.ibrtest.testIBRJIntegration.class);
        printResult(result);

    }

    public static void printResult(Result result) {
        System.out.printf("Test ran: %s, Failed: %s%n", 
                          result.getRunCount(), result.getFailureCount());    
    }

}  	 