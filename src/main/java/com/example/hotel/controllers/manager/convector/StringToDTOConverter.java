package com.example.hotel.controllers.manager.convector;

import com.example.hotel.DTO.RoomDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDTOConverter implements Converter<String, RoomDTO> {

    private final ObjectMapper objectMapper;

    public StringToDTOConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public RoomDTO convert(String source) {
        try {
            return objectMapper.readValue(source, RoomDTO.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid RoomDTO string: " + source, e);        }
    }
}
