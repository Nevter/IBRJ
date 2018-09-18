package edu.uct.ibr.util;

import edu.uct.ibr.bayesnet.*;
import edu.uct.ibr.implication.Implication;
import edu.ksu.cis.bnj.bbn.converter.ConverterFactory;
import java.io.ByteArrayOutputStream;
import java.io.*;
import java.util.ArrayList;

public class ibifFactory{

    public static String bifConverter(BNGraph g){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ConverterFactory.save(g.getBBNGraph(), outputStream, "xml");
        return outputStream.toString();
    }

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

            buffer.append("        <consquent>");
            buffer.append(impl.getConsequentNode().getName());
            buffer.append("</consquent>\n");

            buffer.append("      </sentence>\n");
        }

        return buffer.toString();
    }

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

    public static void save(BNGraph g, String output){
        try{
            String out = ibifConverter(g);
            BufferedWriter writer = new BufferedWriter(new FileWriter(output));
            writer.write(out);
             
            writer.close();
        }catch (Exception e){
            System.out.println("Could not save file to this location");
        }
    }


}