package com.myproject.transactionservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDataResponse implements Serializable {
    private static final long serialVersionUID = 5722813675723011384L;

    private String activityName;
    private String status;
    private LocalDateTime createdDate;
    private UserDataDTO userData;
    private String description;
}
