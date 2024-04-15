package org.example.data;

import lombok.Data;

@Data
public class Answers {
      private int examId;

      private int stuId;

      private long submitTime;

      private Answer[] answers;
}
