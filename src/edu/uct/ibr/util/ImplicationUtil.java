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

	public static boolean hasClassicalCycle(ArrayList<Implication> kb, Implication impl){
		kb.add(impl);
		return hasClassicalCycle(kb);
	}


  	public static boolean hasClassicalCycle(ArrayList<Implication> kb){
		ArrayList<Implication> classicalImpl = getClassical(kb);
		
		DefaultDirectedGraph<String, DefaultEdge> graph = GraphUtil.createGraph(classicalImpl);
		List<Set<String>> connectedSets = getStronglyConnectedSets(graph);
		return getStronglyConnectedSet(connectedSets) == null ? false : true;
	}

	public static Set<String> getConnectedClassicalImplications(ArrayList<Implication> kb){
		ArrayList<Implication> classicalImpl = getClassical(kb);

		DefaultDirectedGraph<String, DefaultEdge> graph = GraphUtil.createGraph(classicalImpl);
		List<Set<String>> connectedSets = getStronglyConnectedSets(graph);

		return getStronglyConnectedSet(connectedSets);

	}



	public static Set<String> getStronglyConnectedSet(List<Set<String>> connectedSets){
		for (Set<String> conSet : connectedSets){
			if (conSet.size() > 1){
				return conSet;
			}
		}
		return null;
	}

	public static ArrayList<Implication> getClassical(ArrayList<Implication> kb){
		ArrayList<Implication> classicalImplications = new ArrayList<Implication>();
		for (Implication impl : kb){
			if (impl.getClass() == ClassicalImplication.class){
				classicalImplications.add(impl);
			}
		}
		return classicalImplications;
	}


	public static ArrayList<Implication> getDefeasible(ArrayList<Implication> kb){
		ArrayList<Implication> defeasibleImplications = new ArrayList<Implication>();
		for (Implication impl : kb){
			if (impl.getClass() == DefeasibleImplication.class){
				defeasibleImplications.add(impl);
			}
		}
		return defeasibleImplications;
	}

	public static List<Set<String>> getStronglyConnectedSets(DefaultDirectedGraph graph){
		KosarajuStrongConnectivityInspector conInsp = new KosarajuStrongConnectivityInspector(graph);

		return conInsp.stronglyConnectedSets();
	}

	
}