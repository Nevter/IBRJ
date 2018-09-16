package edu.uct.ibr.util;

import java.util.Set;
import edu.uct.ibr.bayesnet.*;
import edu.uct.ibr.bayesnet.BNNode;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import java.util.ArrayList;
import edu.uct.ibr.implication.*;

public class GraphUtil {

	
	public static boolean isStronglyConnected(BNNode srcNode, BNNode destNode){
		//if they are the same node, they must be connected
		if (srcNode.getName().equals(destNode.getName())){
			return true;
		}
		//get all the children of srcNode and check if they are connected to destNode
		boolean isConnected = false;
		Set<BNNode> srcChildren = (Set<BNNode>) srcNode.getChildren();
		for(BNNode node : srcChildren){
			isConnected |= isStronglyConnected(node, destNode);
		}

		return isConnected;
	}


	public static boolean hasCycles(BNGraph graph){
		Set<BNNode> nodes = (Set<BNNode>) graph.getGraphNodes();
		for (BNNode destNode : nodes){
			for (BNNode node : (Set<BNNode>) destNode.getChildren())
			if (isStronglyConnected(node, destNode)) return true;
		}
		return false;
	}


	//creates a JGraphT graph from a knowledgebase
	public static DefaultDirectedGraph createGraph(ArrayList<Implication> kb){
		DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);

		for (Implication impl : kb){
			String antecedent = impl.getAntecedentNode().getName();
			String consequent = impl.getConsequentNode().getName();
			graph.addVertex(antecedent);
			graph.addVertex(consequent);
			graph.addEdge(antecedent,consequent);
		}

		return graph;
	}



}