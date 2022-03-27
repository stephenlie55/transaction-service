package com.myproject.transactionservice.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityRequest implements Serializable {
    private static final long serialVersionUID = 5395377920242225537L;

    private String phoneNumber;
    private String activityName;
    private String requestId;
    private int status;
    private String jsonRequest;
    private String jsonResponse;
    private LocalDateTime createdDate;
}
