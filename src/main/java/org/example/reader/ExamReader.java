package org.example.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.data.Exam;
import org.example.data.Question;
import org.example.data.Sample;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExamReader {
    public static Exam[] readDir(String filepath){
        File input = new File(filepath);
        File[] fileList = input.listFiles();
        List<Exam> list = new ArrayList<>();
        for (File file : fileList) {
            if(file.getPath().endsWith(".json")){
                try{
                    list.add(readJson(file));
                }
                catch (Exception e){
                    e.getStackTrace();
                }
            }
            else if(file.getPath().endsWith(".xml")){
                try{
                    list.add(readXml(file));
                }
                catch (Exception e){
                    e.getStackTrace();
                }
            }
        }
        return list.toArray(new Exam[0]);
    }

    private static Exam readJson(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file,Exam.class);

    }

    private static Exam readXml(File file) throws IOException, JDOMException {
        // 创建一个sax解析器
        SAXBuilder builder = new SAXBuilder();
        // 根据xml结构转换成一个Document对象
        Document doc = builder.build(file);
        Exam exam = new Exam();
        Element ExamRoot = doc.getRootElement();

        exam.setId(Integer.parseInt(ExamRoot.getChildText("id")));
        exam.setTitle(ExamRoot.getChildText("title"));
        exam.setStartTime(Long.parseLong(ExamRoot.getChildText("startTime")));
        exam.setEndTime(Long.parseLong(ExamRoot.getChildText("endTime")));

        List<Element> QuestionsNode = ExamRoot.getChild("questions").getChildren();
        List<Question> questions = new ArrayList<>();
        for(Element q: QuestionsNode){
            Question question = new Question();
            question.setId(Integer.parseInt(q.getChildText("id")));
            question.setType(Integer.parseInt(q.getChildText("type")));
            question.setQuestion(q.getChildText("question"));
            question.setPoints(Integer.parseInt(q.getChildText("points")));
            if(question.getType() <=2){
                List<Element> optionsNode = q.getChild("options").getChildren();
                List<String> options = new ArrayList<>();
                for(Element optionNode:optionsNode){
                    options.add(optionNode.getText());
                }
                question.setOptions(options.toArray(new String[0]));
                if(question.getType() == 1){
                    int answer[] = {Integer.parseInt(q.getChildText("answer"))};
                    question.setAnswer(answer);
                }
                else{
                    List<Integer> answers = new ArrayList<>();
                    List<Element> answersNode = q.getChild("answers").getChildren();
                    for(Element answerNode:answersNode){
                        answers.add(Integer.valueOf(answerNode.getText()));
                    }
                    question.setAnswer(answers.stream().mapToInt(Integer::intValue).toArray());
                    question.setScoreMode(q.getChildText("scoreMode"));
                    if(question.getScoreMode().equals("fix")){
                        question.setFixScore(Integer.parseInt(q.getChildText("fixScore")));
                    }
                    else if (question.getScoreMode().equals("partial")) {
                        List<Integer> partialScore = new ArrayList<>();
                        List<Element> partialNode = q.getChild("partialScores").getChildren();
                        for(Element node:partialNode){
                            partialScore.add(Integer.valueOf(node.getText()));
                        }
                        question.setPartialScore(partialScore.stream().mapToInt(Integer::intValue).toArray());
                    }
                }
            }
            else if(question.getType() == 3){
                question.setTimeLimit(q.getChildText("timeLimit"));
                List<Sample> samples = new ArrayList<>();
                List<Element> samplesNode = q.getChild("samples").getChildren();
                for(Element sampleNode:samplesNode){
                    Sample sample = new Sample(sampleNode.getChildText("input"),sampleNode.getChildText("output"));
                    samples.add(sample);
                }
                question.setSamples(samples.toArray(new Sample[0]));
            }
            questions.add(question);
        }
        exam.setQuestions(questions.toArray(new Question[0]));
        return exam;
    }
}
