package org.example.data;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.List;


public class IntArrayDeserializer extends JsonDeserializer<int[]> {
    @Override
    public int[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Object value = jsonParser.readValueAs(Object.class);
        if (value instanceof Integer) {
            // 如果是单个整数值，将其转换为包含该值的整数数组
            return new int[]{(int) value};
        } else if (value instanceof int[]) {
            // 如果是整数数组，直接返回
            return (int[]) value;
        } else if (value instanceof List<?>) {
            // 如果是ArrayList，转换为int[]数组
            List<Integer> list = (List<Integer>) value;
            return list.stream().mapToInt(Integer::intValue).toArray();
        } else {
            throw new IllegalArgumentException("Invalid type for int array: " + value.getClass());
        }
    }
}