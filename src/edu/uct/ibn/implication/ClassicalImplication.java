package edu.uct.ibn.implication;

import java.util.*;

import edu.uct.ibn.util.*;
import edu.uct.ibn.bayesnet.BNNode;
import edu.uct.ibn.bayesnet.BNNode.Relationship;
import edu.uct.ibn.bayesnet.BNGraph;

import edu.ksu.cis.bnj.bbn.BBNCPF;
import edu.ksu.cis.bnj.bbn.BBNConstant;

public class ClassicalImplication extends Implication {


  public ClassicalImplication(BNNode antecedentNode, BNNode consequentNode, BNGraph graph){
    super(antecedentNode, consequentNode, graph);
  }


  public void supplementNetwork(){

    switch(relationship){
      case PARENT:
        supplementDirect();
        break;
      case CHILD:
        supplementReverse();
        break;
      case NONE:
      case ANCESTOR:
        supplementNone();
        break;  
      case DESCENDANT:
      case SELF:
      default:
        System.out.println("Cannot supplement network for this implication statement: " + this.toString());
    }
    
  }

  /**
   * the parent case
   */
  private void supplementDirect(){
    String consequentNodeName = consequentNode.getName();
    String antecedentNodeName = antecedentNode.getName();
    String consequentTruthValueName = consequentNode.getTruthValueName();
    String antecedentTruthValueName = antecedentNode.getTruthValueName();


    BBNCPF consequentCPF = graph.getNode(consequentNodeName).getCPF();

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
  
  /** 
   * the child case
   */
  private void supplementReverse(){

    
  }

  /**
   * the none, ancestor and descendent case
   */
  private void supplementNone(){

    
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

    buffer.append(relationship + ": ");
    buffer.append(antecedentNode.getName());
    buffer.append(" -> ");
    buffer.append(consequentNode.getName());

    return buffer.toString();
  }

}
