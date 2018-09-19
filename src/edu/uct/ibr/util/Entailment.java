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

import aima.core.logic.propositional.inference.*;
import aima.core.logic.propositional.kb.*;
import aima.core.logic.propositional.parsing.*;
import aima.core.logic.propositional.parsing.ast.*;

import edu.uct.ibr.implication.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Entailment {

	public static boolean entails(ArrayList<Implication> kb, ArrayList<Implication> observations ){
		
		//if the observation symbols aren't specified in the KB, then we dont care about them
		//i.e. remove them from the observations
		HashSet<String> kbSymbols = new HashSet<String>();
		for (Implication impl : kb){
			kbSymbols.add(impl.getAntecedentNode().getName());
			kbSymbols.add(impl.getConsequentNode().getName());
		}

		for (Implication obs : (ArrayList<Implication>) observations.clone()){
			if (!kbSymbols.contains(obs.getAntecedentNode().getName()) || 
				  !kbSymbols.contains(obs.getConsequentNode().getName())) {
						observations.remove(obs);
					}

		}
		

		//if the KB is empty, then we don't care the observations
		//if there are no observations, then we just use the KB
		if (kb.size() == 0 || observations.size() == 0){
			return true;
		}
		

		//turn our KB into a AIMA kb
		KnowledgeBase knowledgeBase = new KnowledgeBase();
		for (Implication impl : kb){
			knowledgeBase.tell(implToSentence(impl));
		}
		
		TTEntails entailmentChecker = new TTEntails();
		
		boolean entails = true;
		for (Implication impl : observations){
			entails &= entailmentChecker.ttEntails(knowledgeBase,implToSentence(impl));
		}
		
		return entails;
	}

	public static ComplexSentence implToSentence(Implication impl){
		PropositionSymbol antecedent = new PropositionSymbol(impl.getAntecedentNode().getName());
		PropositionSymbol consequent = new PropositionSymbol(impl.getConsequentNode().getName());
		
		return new ComplexSentence(antecedent, Connective.IMPLICATION, consequent);
	}

}