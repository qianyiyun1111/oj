package org.example.data;

import lombok.Data;

@Data
public class Exam {

    private int id;

    private String title;

    private Long startTime;

    private Long endTime;

    private Question[] questions;

    public boolean TimeJudge(Long time){
        return time >=startTime && time <= endTime;
    }
}
