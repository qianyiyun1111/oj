package org.example.data;

import lombok.Data;

@Data
public class Sample {
    private String input;

    private String output;

    public Sample(String input, String output) {
        this.input = input;
        this.output = output;
    }
    public Sample() {
    }
}
