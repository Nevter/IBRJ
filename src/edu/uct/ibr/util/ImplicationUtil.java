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

import edu.uct.ibr.implication.*;
import edu.uct.ibr.util.GraphUtil;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;

public class ImplicationUtil {

	//does adding impl create a classical cycle?
	public static boolean hasClassicalCycle(ArrayList<Implication> kb, Implication impl){
		ArrayList<Implication> kbDash = (ArrayList<Implication>) kb.clone();
		kbDash.add(impl);
		return hasClassicalCycle(kbDash);
	}

	//does kb contain a classical cycle
  public static boolean hasClassicalCycle(ArrayList<Implication> kb){
		ArrayList<Implication> classicalImpl = kb;
		
		DefaultDirectedGraph<String, DefaultEdge> graph = GraphUtil.createGraph(classicalImpl);
		List<Set<String>> connectedSets = getStronglyConnectedSets(graph);
		return getStronglyConnectedSet(connectedSets) == null ? false : true;
	}

	//get implications that are connected via transitivity
	public static Set<String> getConnectedClassicalImplications(ArrayList<Implication> kb){
		ArrayList<Implication> classicalImpl = kb;

		DefaultDirectedGraph<String, DefaultEdge> graph = GraphUtil.createGraph(classicalImpl);
		List<Set<String>> connectedSets = getStronglyConnectedSets(graph);

		return getStronglyConnectedSet(connectedSets);

	}

	//Get the first set of strongly connected items
	public static Set<String> getStronglyConnectedSet(List<Set<String>> connectedSets){
		for (Set<String> conSet : connectedSets){
			if (conSet.size() > 1){
				return conSet;
			}
		}
		return null;
	}

	//Get all the strongly connected sets
	public static List<Set<String>> getStronglyConnectedSets(DefaultDirectedGraph graph){
		KosarajuStrongConnectivityInspector conInsp = new KosarajuStrongConnectivityInspector(graph);

		return conInsp.stronglyConnectedSets();
	}

	
}