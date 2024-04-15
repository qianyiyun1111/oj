package org.example;

import org.example.data.Answer;
import org.example.data.Answers;
import org.example.data.Exam;
import org.example.data.Question;
import org.example.strategy.*;
import org.example.utils.CustomThreadPool;
import org.example.utils.ThreadPool;

public class ExamJudge {
    private Exam thisExam;
    public int judge(Exam[] exams, Answers answers, CustomThreadPool threadPool){
        int points = 0;
        for(Exam exam:exams){
            if(exam.getId() == answers.getExamId()){
                thisExam = exam;
            }
        }
        if(thisExam.TimeJudge(answers.getSubmitTime())){
            for(int i = 0; i < thisExam.getQuestions().length; i++){
                Question question = thisExam.getQuestions()[i];
                Answer answer = null;
                Strategy strategy = null;

                //寻找question对应answer
                for (int j = 0; j < answers.getAnswers().length; j++){
                    Answer ans = answers.getAnswers()[j];
                    if(question.getId()==ans.getId()){
                        answer = ans;
                        break;
                    }
                }


                switch(question.getType()){
                    case 1:
                        strategy = new SingleSelectionStrategy();
                        break;
                    case 2:
                        if(question.getScoreMode().equals("nothing")){
                            strategy = new NothingStrategy();
                        }
                        else if(question.getScoreMode().equals("fix")){
                            strategy = new FixStrategy();
                        }
                        else if(question.getScoreMode().equals("partial")){
                            strategy = new PartialStrategy();
                        }
                        break;
                    case 3:
                        strategy = new CodeStrategy();
                        if(answer.getAnswer().endsWith(".java")){
                            strategy = new JavaCodeStrategy(threadPool);
                        }
                        break;
                    default:
                        //Add new type here.
                        break;
                }
                points+=strategy.point(question,answer);
            }
        }
        return points;
    }
}
