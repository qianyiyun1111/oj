package org.example.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.data.Answers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerReader {
    public static Answers[] readDir(String filepath){
        File input = new File(filepath);
        File[] fileList = input.listFiles();
        List<Answers> list = new ArrayList<>();
        for (File file : fileList) {
            try{
                list.add(readJson(file));
            }
            catch (Exception e){
                e.getStackTrace();
            }
        }
        return list.toArray(new Answers[0]);
    }

    private static Answers readJson(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, Answers.class);
    }
}
