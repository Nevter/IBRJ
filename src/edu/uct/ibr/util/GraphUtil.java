package edu.uct.ibr.util;

import java.util.Set;
import edu.uct.ibr.bayesnet.*;
import edu.uct.ibr.bayesnet.BNNode;

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
}