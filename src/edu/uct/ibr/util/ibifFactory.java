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

import edu.uct.ibr.bayesnet.*;
import edu.uct.ibr.implication.Implication;
import edu.ksu.cis.bnj.bbn.converter.ConverterFactory;
import java.io.ByteArrayOutputStream;
import java.io.*;
import java.util.ArrayList;

public class ibifFactory{

    /**
     * Converts a graph into a .bif string
     * @param g
     * @return
     */
    public static String bifConverter(BNGraph g){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ConverterFactory.save(g.getBBNGraph(), outputStream, "xml");
        return outputStream.toString();
    }

    /**
     * creates the KB section for a .ibif file
     */
    public static String kbToIBIF(ArrayList<Implication> knowledgeBase){
        StringBuffer buffer = new StringBuffer();
        buffer.append("      <!-- Knowledge base -->\n");
        for (Implication impl : knowledgeBase){
            buffer.append("      <sentence>\n");
            
            buffer.append("        <connective>");
            buffer.append("classical");
            buffer.append("</connective>\n");

            buffer.append("        <antecedent>");
            buffer.append(impl.getAntecedentNode().getName());
            buffer.append("</antecedent>\n");

            buffer.append("        <consequent>");
            buffer.append(impl.getConsequentNode().getName());
            buffer.append("</consequent>\n");

            buffer.append("      </sentence>\n");
        }

        return buffer.toString();
    }

    /**
     * Converts a graph with a KB into an .ibif string 
     * @param g
     * @return
     */
    public static String ibifConverter(BNGraph g){
        String bif = bifConverter(g);

        String splitBIF[] = bif.split("</NETWORK>\n");

        StringBuffer buf = new StringBuffer();
        buf.append(splitBIF[0]);
        buf.append(kbToIBIF(g.getKnowledgebase()));
        buf.append("  </NETWORK>\n");
        buf.append(splitBIF[1]);

        return buf.toString();
    }

    /**
     * Save a BNGraph to the output file
     * @param g
     * @param path
     */
    public static void save(BNGraph g, String path){
        String content = ibifConverter(g);
        fileWriter(content, path);
    }
    /**
     * writes a string to a file
     * @param content
     * @param path
     */
    public static void fileWriter(String content, String path){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(content);
             
            writer.close();
        }catch (Exception e){
            System.out.println("Could not save file to this location");
        }
    }

    /**
     * reads a string from a file
     * @param path
     * @return
     */
    public static String fileReader(String path){
        StringBuilder sb = new StringBuilder(); 
        try{
            InputStream is = new FileInputStream(path); 
            
            BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
            String line = buf.readLine(); 
            
        
            while(line != null){ 
                sb.append(line).append("\n"); 
                line = buf.readLine(); 
            } 
        }catch(Exception e){
            System.out.println("Could not read input file");
        }
        return sb.toString();
    }

    /**
     * Get the .bif section of an ibif file
     * @param content
     * @return
     */
    public static String getBIFfromIBIF(String content){
        int kbStart = content.indexOf("      <!-- Knowledge base -->");
        int kbEnd = content.lastIndexOf("</NETWORK>");
        String bif = content.substring(0, kbStart) + content.substring(kbEnd, content.length());
        return bif;
    }

    /**
     * Get a the knowledge base section of an .ibif
     * @param content
     * @return
     */
    public static String getKBfromIBIF(String content){
        int kbStart = content.indexOf("<sentence>");
        int kbEnd = content.lastIndexOf("</NETWORK>");
        String kb = content.substring(kbStart, kbEnd);
        return kb;
    }

    /**
     * Create a knowledgebase of implications from a string. 
     * Used when a IBN is being loaded from an .ibif
     * @param content
     * @return
     */
    public static ArrayList<String[]> createKBfromStr(String content){
        ArrayList<String[]> kb = new ArrayList<String[]>();
        System.out.println("Create kb from Str");
        content = content.replaceAll(" ", "");    
        String lines[] = content.split("\n");
        
        for (int i = 0; i < lines.length; i++){
            if (lines[i].equals("<sentence>")){
                String antecedentNodeName = lines[i+2].replace("<antecedent>", "").replace("</antecedent>", "");
                String consequentNodeName = lines[i+3].replace("<consequent>", "").replace("</consequent>", "");
                kb.add(new String[]{antecedentNodeName,consequentNodeName});
            }
        }
        return kb;
    }

    /**
     * load an IBN from the file path
     * @param path
     * @return
     */
    public static ArrayList<String[]> load(String path){
        String ibifContent = fileReader(path);
        String bif = getBIFfromIBIF(ibifContent);
        fileWriter(bif,"temp.bif");
        String kb = getKBfromIBIF(ibifContent);
        return createKBfromStr(kb);
    }


}