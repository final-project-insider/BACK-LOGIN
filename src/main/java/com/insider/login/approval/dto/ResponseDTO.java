package com.insider.login.approval.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class ResponseDTO {

    private int status;         //상태코드값(200,404,415, 400, 500)
    private String message;     //응답메시지
    private Object data;        //응답데이터

    public  ResponseDTO(){

    }

    public ResponseDTO(HttpStatus status, String message, Object data){
        this.status = status.value();    // HttpStatus enum 타입에서 value라는 int형 상태 코드 값만 추출
        this.message = message;
        this.data = data;
    }
}
