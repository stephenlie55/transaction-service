package com.myproject.transactionservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDTO implements Serializable {
    private static final long serialVersionUID = 5405670317075919570L;

    private Long id;
    private String userFullName;
    private String email;
    private String phoneNumber;
    private BigDecimal accountBalance;
    private Date createDate;
    private Date modifiedDate;
}
