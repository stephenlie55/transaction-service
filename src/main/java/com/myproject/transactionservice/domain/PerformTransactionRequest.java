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
public class PerformTransactionRequest implements Serializable {
    private static final long serialVersionUID = -2644047769998295947L;

    private String phoneNumber;
    private BigDecimal transactionAmount;
    private String destinationAccountNumber;
}
