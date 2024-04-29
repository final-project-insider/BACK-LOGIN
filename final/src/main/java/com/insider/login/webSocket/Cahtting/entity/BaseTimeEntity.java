package com.insider.login.webSocket.Cahtting.entity;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 사용하면, 상속받는 엔티티에 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) // Auditing : 자동으로 값 매핑
public class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime  createdDate;

    @LastModifiedDate
    private LocalDateTime  lastModifiedDate;

}
