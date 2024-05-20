package com.insider.login.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConvertUtil {
    // Object -> JSONObject로 변환을 시켜줄 method을 작성할 것이다
    public static Object convertObjectToJsonObject(Object obj) {
        System.out.println("ConvertUtil 도착 (순서 확인용)");
        ObjectMapper mapper = new ObjectMapper();   // used for converting Java objects to JSON and vice versa
        mapper.registerModule(new JavaTimeModule());// JavaTimeModule를 저장을 함으로써 LocalDate을 JSON형식으로 저장을 하기 위한 code
        JSONParser parser = new JSONParser();       // typically used in JSON processing libraries to parse JSON text into Java objects
        String convertJsonString;
        Object convertObj;

        try {
            convertJsonString = mapper.writeValueAsString(obj); // converts Java object into a JSON String (해석: object -> JSON String으로 형변환 시켜줌)
            convertObj = parser.parse(convertJsonString);       //  converts JSON String into structured JSON object (해석: 받은 JSON String -> JSON Object로 형변환을 시켜줌)
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return convertObj;
    }
}