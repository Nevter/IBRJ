package edu.uct.ibn.implication;

import java.util.*;

import edu.uct.ibn.BNNode;
import edu.uct.ibn.BNNode.Relationship;
import edu.uct.ibn.BNGraph;

import edu.ksu.cis.bnj.bbn.BBNCPF;
import edu.ksu.cis.bnj.bbn.BBNConstant;

public class ClassicalImplication extends Implication {


  public ClassicalImplication(BNNode antecedentNode, BNNode consequentNode, Relationship relationship){
    super(antecedentNode, consequentNode, relationship);
  }


  public BNGraph supplementNetwork(BNGraph graph){
    String consequentNodeName = consequentNode.getName();
    String antecedentNodeName = antecedentNode.getName();
    String consequentTruthValueName = consequentNode.getTruthValueName();
    String antecedentTruthValueName = antecedentNode.getTruthValueName();
    
    BBNCPF consequentCPF = graph.getNode(consequentNodeName).getCPF();
    
    System.out.println(antecedentTruthValueName);


    if (this.relationship == Relationship.NONE){
      System.out.println("This doesn't make any sense");
    }
    else if (this.relationship == Relationship.PARENT){
      System.out.println("Adding a classical implication to network - PARENT");
      
      Set<Hashtable> tableEntries = consequentCPF.getTable().keySet();
      List<Hashtable> entriesToChange = new ArrayList<Hashtable>();
      List<Hashtable> entriesToSetToOne = new ArrayList<Hashtable>();
      List<Hashtable> entriesToSetToZero = new ArrayList<Hashtable>();

      for (Hashtable h : tableEntries){
        Set<String> tableKeySet = h.keySet();
        for (String kVal : tableKeySet){
          if (kVal.equals(antecedentNodeName) && h.get(kVal).equals(antecedentTruthValueName)){
            entriesToChange.add(h);
          }
        }
      }

      for (Hashtable h : entriesToChange){
        Set<String> tableKeySet = h.keySet();
        for (String kVal : tableKeySet){
          if (kVal.equals(consequentNodeName)){
            if (h.get(kVal).equals(consequentTruthValueName)){
              entriesToSetToOne.add(h);
            }
            else{
              entriesToSetToZero.add(h);
            }
          }
        }
      }

      System.out.println("Set to 1: " + entriesToSetToOne.toString());
      System.out.println("Set to 0: " + entriesToSetToZero.toString());

      for (Hashtable h : entriesToSetToOne){
        consequentCPF.remove(h);
        consequentCPF.put(h, new BBNConstant(1.0));
      }
      for (Hashtable h : entriesToSetToZero){
        consequentCPF.remove(h);
        consequentCPF.put(h, new BBNConstant(0.0));
      }


    }
    else if (this.relationship == Relationship.ANCESTOR){
      
    }
    else {
      System.out.println("Cannot perform SELF, CHILD or DESCENDENT relations");
    }
    return graph;
  }

  public String toString(){
    StringBuffer buffer = new StringBuffer();

    buffer.append(antecedentNode.getName());
    buffer.append(" -> ");
    buffer.append(consequentNode.getName());

    return buffer.toString();
  }

}
