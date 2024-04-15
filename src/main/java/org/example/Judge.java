package org.example;

import org.example.data.Answers;
import org.example.data.Exam;
import org.example.reader.AnswerReader;
import org.example.reader.ExamReader;
import org.example.utils.CustomThreadPool;

public class Judge {
    public final CustomThreadPool threadPool = new CustomThreadPool(5);
    private final String examsPath;
    // 答案文件夹路径
    private final String answersPath;
    // 输出文件路径
    private final String output;
    Judge(String examsPath, String answersPath, String output, CustomThreadPool threadPool){
        this.examsPath = examsPath;
        this.answersPath = answersPath;
        this.output = output;
    }
    public void run(){
        Exam[] exams;
        Answers[] answers;
        exams = ExamReader.readDir(examsPath);
        answers = AnswerReader.readDir(answersPath);
        int[][] result = new int[answers.length][3];
        for (int i = 0; i < answers.length; i++) {
            result[i][0] = answers[i].getExamId();
            result[i][1] = answers[i].getStuId();
            ExamJudge examJudge = new ExamJudge();
            result[i][2] = examJudge.judge(exams,answers[i],threadPool);
        }
        CsvWriter writer = new CsvWriter();
        writer.write(result, output);
    }
}
