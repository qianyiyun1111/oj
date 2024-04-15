package org.example.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
public class Question {
    private int id;

    private int type;

    private String question;

    private int points;

    private String[] options;

    @JsonDeserialize(using = IntArrayDeserializer.class)
    private int[] answer;

    private String scoreMode;

    private int fixScore;

    private int[] partialScore;

    private Sample[] samples;

    private String timeLimit;
}
