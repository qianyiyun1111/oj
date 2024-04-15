package org.example.strategy;

import org.example.data.Answer;
import org.example.data.Question;

public class SingleSelectionStrategy extends SelectionStrategy {

    @Override
    public int point(Question question, Answer answer) {
        if(get(question, answer)){
            if(actualAnswer[0] == expectAnswer[0]) {
                return question.getPoints();
            }
        }
        return 0;
    }
}
