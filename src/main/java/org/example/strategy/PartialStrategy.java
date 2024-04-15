package org.example.strategy;

import org.example.data.Answer;
import org.example.data.Question;

public class PartialStrategy extends NothingStrategy {

    @Override
    public int point(Question question, Answer answer) {
        if(get(question, answer)){
            if(exactly()) {
                return question.getPoints();
            }
            else {
                int point = 0;
                boolean error;
                for (int i = 0; i < actualAnswer.length; i++) {
                    error = true;
                    for (int j = 0; j < expectAnswer.length; j++) {
                        if(actualAnswer[i]==expectAnswer[j]){
                            error = false;
                            point+=question.getPartialScore()[j];
                            break;
                        }
                    }
                    if(error){
                        return 0;
                    }
                }
                return point;
            }
        }
        return 0;
    }
}
