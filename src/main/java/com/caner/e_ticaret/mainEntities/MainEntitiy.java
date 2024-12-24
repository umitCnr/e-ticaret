package com.caner.e_ticaret.mainEntities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class MainEntitiy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Value("create_time")
    private LocalDateTime time;

    @PrePersist
    protected void creatTime() {
        this.time = LocalDateTime.now();
    }

}
