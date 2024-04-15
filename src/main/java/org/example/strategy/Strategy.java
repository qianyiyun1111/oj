package org.example.strategy;

import org.example.data.Answer;
import org.example.data.Question;

public interface Strategy {
    int point(Question question, Answer answer);
}
