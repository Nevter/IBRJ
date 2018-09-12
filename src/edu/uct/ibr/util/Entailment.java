package edu.uct.ibr.util;

import aima.core.logic.propositional.inference.*;
import aima.core.logic.propositional.kb.*;
import aima.core.logic.propositional.parsing.*;
import aima.core.logic.propositional.parsing.ast.*;

import edu.uct.ibr.implication.*;
import java.util.ArrayList;

public class Entailment {

	public static boolean entails(ArrayList<Implication> kb, ArrayList<Implication> observations ){
		
		KnowledgeBase knowledgeBase = new KnowledgeBase();
		for (Implication impl : kb){
			knowledgeBase.tell(implToSentence(impl));
		}
		
		TTEntails entailmentChecker = new TTEntails();
		
		boolean entails = false;
		for (Implication impl : observations){
			entails |= entailmentChecker.ttEntails(knowledgeBase,implToSentence(impl));
		}
		
		return entails;
	}

	public static ComplexSentence implToSentence(Implication impl){
		PropositionSymbol antecedent = new PropositionSymbol(impl.getAntecedentNode().getName());
		PropositionSymbol consequent = new PropositionSymbol(impl.getConsequentNode().getName());
		
		return new ComplexSentence(antecedent, Connective.IMPLICATION, consequent);
	}

}