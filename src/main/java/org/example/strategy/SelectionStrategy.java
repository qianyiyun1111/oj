package org.example.strategy;

import org.example.data.Answer;
import org.example.data.Question;

import java.util.ArrayList;
import java.util.List;

public class SelectionStrategy implements Strategy{
    int[] actualAnswer;
    int[] expectAnswer;
    @Override
    public int point(Question question, Answer answer) {
        return 0;
    }

    public boolean get(Question question, Answer answer){
        if(answer == null){
            return false;
        }
        String ans = answer.getAnswer();
        List<Integer> answers = new ArrayList<>();
        for(int i = 0; i < ans.length(); i++){
            answers.add((int) ans.charAt(i)-65);
        }
        actualAnswer = answers.stream().mapToInt(Integer::intValue).toArray();
        expectAnswer = question.getAnswer();
        return true;
    }
}
