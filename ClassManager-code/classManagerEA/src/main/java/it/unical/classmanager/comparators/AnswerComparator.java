package it.unical.classmanager.comparators;

import java.util.Comparator;

import it.unical.classmanager.model.data.Answer;

public class AnswerComparator implements Comparator<Answer>{

	@Override
	public int compare(Answer a1, Answer a2) {

		int id1 = a1.getId();
		int id2 = a2.getId();

		if(id1 == id2)
			return 0;
		if(id1 < id2)
			return -1;

		return 1;
	}
	
}
