package edu.uct.ibr.util;

import java.util.Set;
import edu.uct.ibr.bayesnet.*;
import edu.uct.ibr.bayesnet.BNNode;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import java.util.ArrayList;
import edu.uct.ibr.implication.*;

public class GraphUtil {

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