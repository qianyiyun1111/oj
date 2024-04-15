package org.example.strategy;

import org.example.utils.CustomThreadPool;
import org.example.data.Answer;
import org.example.data.Question;
import org.example.data.Sample;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Paths;

public class JavaCodeStrategy extends CodeStrategy {

    @Override
    public int point(Question question, Answer answer) {
        if (compileJava(answer.getAnswer())) {
            if (executeJava(question.getSamples(), answer.getAnswer())) {
                return question.getPoints();
            }
        }
        return 0;
    }

    private boolean compileJava(String answer) {
        try {
            // 构建编译任务
            String resourcePath = Paths.get(getClass().getClassLoader().getResource("cases").toURI()).toString();
            String path = resourcePath + "\\answers\\" + answer;
            Charset charset = Charset.forName("GBK");
            String command = "javac " + path;
                ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
                Process process = processBuilder.start();
                int exitCode = process.waitFor();
                return exitCode == 0;
        }
        catch (InterruptedException | URISyntaxException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean executeJava(Sample[] samples, String answer){
        // 构建执行任务并提交到线程池
        for (Sample sample : samples) {
            String resourcePath = null;
            try {
                resourcePath = Paths.get(getClass().getClassLoader().getResource("cases").toURI()).toString();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            String path = resourcePath + "\\answers\\code-answers ";
            String file = answer.replace(".java", "").replace("code-answers/", "");
            Charset charset = Charset.forName("GBK");
            String command = "java -classpath " + path + file + " " + sample.getInput();
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            Process process = null;
            try {
                process = processBuilder.start();
                String output = readStream(process.getInputStream(), charset);
                int exitCode = process.waitFor();
                if (exitCode != 0) {
                    return false;
                } else {
                    if (output.endsWith("\n")) {
                        output = output.substring(0, output.length() - 2);
                    }
                    return output.equals(sample.getOutput());
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

}