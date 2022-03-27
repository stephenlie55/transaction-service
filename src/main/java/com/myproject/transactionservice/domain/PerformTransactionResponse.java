package com.myproject.transactionservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformTransactionResponse implements Serializable {
    private static final long serialVersionUID = -8357356150587164029L;

    private String status;
    private String statusDescription;
    private BigDecimal transactionAmount;
    private String destinationAccountNumber;
    private BigDecimal balanceLeft;
    private String phoneNumber;
}
