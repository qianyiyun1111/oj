package org.example.strategy;

import org.example.data.Answer;
import org.example.data.Question;

public class FixStrategy extends NothingStrategy {
    @Override
    public int point(Question question, Answer answer) {
        if(get(question, answer)){
            if(exactly()) {
                return question.getPoints();
            }
            else if(fix()){
                return question.getFixScore();
            }
        }
        return 0;
    }
    public boolean fix(){
        boolean fix = false;
        for (int i = 0; i < actualAnswer.length; i++) {
            fix = false;
            for (int j = 0; j < expectAnswer.length; j++) {
                if(actualAnswer[i]==expectAnswer[j]){
                    fix = true;
                    break;
                }
            }
            if(!fix){
                break;
            }
        }
        return fix;
    }
}
