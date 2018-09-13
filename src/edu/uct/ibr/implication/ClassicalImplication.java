package edu.uct.ibr.implication;

import java.util.*;

import edu.uct.ibr.util.*;
import edu.uct.ibr.bayesnet.BNNode;
import edu.uct.ibr.bayesnet.BNNode.Relationship;
import edu.uct.ibr.bayesnet.BNGraph;

import edu.ksu.cis.bnj.bbn.BBNCPF;
import edu.ksu.cis.bnj.bbn.BBNConstant;

public class ClassicalImplication extends Implication {


  public ClassicalImplication(BNNode antecedentNode, BNNode consequentNode, BNGraph graph){
    super(antecedentNode, consequentNode, graph);
  }


  public boolean supplementNetwork(){
    boolean success = false;
    switch(relationship){
      case PARENT:
        success = supplement();
        break;
      case CHILD:
        success = supplementReverse();
        break;
      case NONE:
      case ANCESTOR:
      case DESCENDANT:
        success = supplementNone();
        break;  
      case SELF:
      default:
        System.out.println("Cannot supplement network for this implication statement: " + this.toString());
    }
    return success;
  }

  /**
   * the parent case
   */
  private boolean supplement(){
    String consequent = consequentNode.getName();
    String antecedent = antecedentNode.getName();
    String consequentTruthValue = consequentNode.getTruthValueName();
    String consequentFalseValue = consequentNode.getFalseValueName();
    String antecedentTruthValue = antecedentNode.getTruthValueName();

    BBNCPF consequentCPF = consequentNode.getCPF();

    Set<Hashtable> tableEntries = consequentCPF.getTable().keySet();

    HashSet<Hashtable> setToOne = findEntries(tableEntries, antecedent, antecedentTruthValue, consequent, consequentTruthValue);
    HashSet<Hashtable> setToZero = findEntries(tableEntries, antecedent, antecedentTruthValue, consequent, consequentFalseValue);

    for (Hashtable h : setToOne){
      consequentCPF.remove(h);
      consequentCPF.put(h, new BBNConstant(1.0));
    }
    for (Hashtable h : setToZero){
      consequentCPF.remove(h);
      consequentCPF.put(h, new BBNConstant(0.0));
    }
    return true;
  } 
  
  /**
   * the none, ancestor and descendent case
   */
  private boolean supplementNone(){
    //add the new edge
    if (graph.addEdge(antecedentNode, consequentNode)){
      //with this new edge, antecedentNode is now a parent of consequent Node and thus
      //it is treated the same as the Parent case. 
      return supplement();
    }
    return false;
  }

  /** 
   * the child case
   */
  private boolean supplementReverse(){
    System.out.println("Supplement reverse");
    String consequent = consequentNode.getName();
    String antecedent = antecedentNode.getName();
    
    String consequentTruthValue = consequentNode.getTruthValueName();
    String consequentFalseValue = consequentNode.getFalseValueName();
    
    String antecedentTruthValue = antecedentNode.getTruthValueName();
    String antecedentFalseValue = antecedentNode.getFalseValueName();

    BBNCPF antecedentCPF = antecedentNode.getCPF();
    Set<Hashtable> tableEntries = antecedentCPF.getTable().keySet();

    HashSet<Hashtable> setToZero = findEntries(tableEntries, consequent, consequentFalseValue, antecedent, antecedentTruthValue);
    HashSet<Hashtable> setToOne = findEntries(tableEntries, consequent, consequentFalseValue, antecedent, antecedentFalseValue);

    for (Hashtable h : setToOne){
      antecedentCPF.remove(h);
      antecedentCPF.put(h, new BBNConstant(1.0));
    }
    for (Hashtable h : setToZero){
      antecedentCPF.remove(h);
      antecedentCPF.put(h, new BBNConstant(0.0));
    }
    return true;
  }
    

    /**
   * 
   * Find all the entries in table entries that have the nodeName set to valueName
   * 
   * @param table - The table entries to search in
   * @param nodeA - The first node to search for
   * @param valueA - The value of the the first node to search for
   * @param nodeB - The second node to search for
   * @param valueB - The value of the second node to search for
   * @return A Set of hashtables, containing the entries that match the given input
   */
  private HashSet<Hashtable> findEntries(Set<Hashtable> table, String nodeA, String valueA, String nodeB, String valueB){
    HashSet<Hashtable> entries = new HashSet<Hashtable>();

    for (Hashtable h : table){   
      if(h.get(nodeA).equals(valueA) && h.get(nodeB).equals(valueB)){
        entries.add(h);
      }
    }

    return entries;
  }

  public String toString(){
    StringBuffer buffer = new StringBuffer();

    //buffer.append(relationship + ": ");
    buffer.append(antecedentNode.getName());
    buffer.append(" -> ");
    buffer.append(consequentNode.getName());

    return buffer.toString();
  }

}
