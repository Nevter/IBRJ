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