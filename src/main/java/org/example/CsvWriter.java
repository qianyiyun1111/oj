package org.example;

import java.io.*;

public class CsvWriter {
    public void write(int[][] result, String path){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // 写入表头
            writer.write("examId, stuId, score");
            writer.newLine();

            // 写入数据
            for (int[] row : result) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    sb.append(row[i]);
                    if (i < row.length - 1) {
                        sb.append(",");
                    }
                }
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("写入 CSV 文件时出错：" + e.getMessage());
        }
    }
}
