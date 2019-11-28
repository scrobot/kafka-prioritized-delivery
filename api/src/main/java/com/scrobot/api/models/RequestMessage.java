package com.scrobot.api.models;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@Data
@Getter
public class RequestMessage {

    private final String id;
    private final int weight;
    private final DateTime createAt = DateTime.now();

    public String getCreateAt() {
        return createAt.toString();
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
