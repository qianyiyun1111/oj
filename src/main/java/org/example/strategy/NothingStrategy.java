package org.example.strategy;

import org.example.data.Answer;
import org.example.data.Question;

public class NothingStrategy extends SelectionStrategy {
    @Override
    public int point(Question question, Answer answer) {
        if(get(question, answer)){
            if(exactly()) {
                return question.getPoints();
            }
        }
        return 0;
    }
    public boolean exactly(){
        if (actualAnswer.length == expectAnswer.length) {
            for (int i = 0; i < actualAnswer.length; i++) {
                if (actualAnswer[i] != expectAnswer[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
