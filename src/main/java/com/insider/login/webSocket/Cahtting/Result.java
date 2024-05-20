package com.insider.login.webSocket.Cahtting;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Result<T> {

    /** 타입 매개변수 생성 뭐든 가질 수 잇는 제네릭 T를 주어, 사용할 수 있게 해주었음. */

    private T data;

    public Result(T data) {

        this.data = data;
    }

}
