package com.number.assignment.numberatic.db.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@Document
public class Numberatic {


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+03:00")
    private final Date insertDate = new Date();

    @Indexed(unique = true)
    private Long number;

    @Id
    @JsonIgnore
    private String numberId;

    public Numberatic() {
    }

    public Numberatic(Long number) {
        this.number = number;
    }
}
