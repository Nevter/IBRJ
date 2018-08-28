package edu.uct.ibn.implication;

import java.util.*;

import edu.uct.ibn.util.*;
import edu.uct.ibn.bayesnet.BNNode;
import edu.uct.ibn.bayesnet.BNNode.Relationship;
import edu.uct.ibn.bayesnet.BNGraph;

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
    

    if (this.relationship == Relationship.NONE){
      System.out.println("This doesn't make any sense");
    }
    else if (this.relationship == Relationship.PARENT){
      
      Set<Hashtable> tableEntries = consequentCPF.getTable().keySet();

      HashSet<Hashtable> entriesToChange = findEntries(tableEntries, antecedentNodeName, antecedentTruthValueName);
      HashSet<Hashtable> entriesToSetToOne = findEntries(entriesToChange, consequentNodeName, consequentTruthValueName);
      HashSet<Hashtable> entriesToSetToZero = (HashSet<Hashtable>) entriesToChange.clone();
      entriesToSetToZero.removeAll(entriesToSetToOne);

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


  private HashSet<Hashtable> findEntries(Set<Hashtable> tableEntries, String nodeName, String valueName){
    HashSet<Hashtable> entries = new HashSet<Hashtable>();

    for (Hashtable h : tableEntries){
      Set<String> tableKeySet = h.keySet();
      for (String kVal : tableKeySet){
        if (kVal.equals(nodeName) && h.get(kVal).equals(valueName)){
          entries.add(h);
        }
      }
    }

    return entries;
  }

  public String toString(){
    StringBuffer buffer = new StringBuffer();

    buffer.append(antecedentNode.getName());
    buffer.append(" -> ");
    buffer.append(consequentNode.getName());

    return buffer.toString();
  }

}
