package com.myproject.transactionservice.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.transactionservice.domain.PerformTransactionRequest;
import com.myproject.transactionservice.domain.PerformTransactionResponse;
import com.myproject.transactionservice.domain.UserActivityRequest;
import com.myproject.transactionservice.domain.UserDataDTO;
import com.myproject.transactionservice.service.ActivityTrackerService;
import com.myproject.transactionservice.service.UserManagementService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping(value = "/v1")
@Slf4j
public class TransactionServiceController {
    @Autowired
    private ActivityTrackerService activityTrackerService;
    @Autowired
    private UserManagementService userManagementService;
    @Autowired
    private ObjectMapper objectMapper;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Perform transaction")
    @PostMapping(value = "/performtransaction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PerformTransactionResponse generateOneTimePassword(@RequestBody(required = true) PerformTransactionRequest performTransactionRequest, HttpServletRequest request) throws JsonProcessingException {
        PerformTransactionResponse performTransactionResponse = new PerformTransactionResponse();
        BigDecimal currentBalance = performTransactionRequest.getTransactionAmount();
        UserDataDTO userDataDTO = new UserDataDTO();
        try {
            BeanUtils.copyProperties(performTransactionRequest, performTransactionResponse);
            userDataDTO = userManagementService.getUserData(performTransactionRequest.getPhoneNumber()).getUserData();
            if (userDataDTO.getAccountBalance().compareTo(performTransactionRequest.getTransactionAmount()) == -1) {
                performTransactionResponse.setStatus("Failed");
                performTransactionResponse.setStatusDescription("Insufficient Balance");
                throw new Exception();
            }

            currentBalance = userDataDTO.getAccountBalance().subtract(performTransactionRequest.getTransactionAmount());

            userDataDTO.setAccountBalance(currentBalance);
            performTransactionResponse.setBalanceLeft(currentBalance);
            performTransactionResponse.setStatusDescription("Success Perform Transaction");
            performTransactionResponse.setStatus("Success");
        } catch (Exception ex) {
            log.error("Exception happened when perform transaction, detail: {}", ex);
        } finally {
            userManagementService.updateUserData(performTransactionRequest.getPhoneNumber(), userDataDTO);
            activityTrackerService.send(UserActivityRequest.builder()
                .activityName("Perform Transaction")
                .createdDate(LocalDateTime.now())
                .jsonRequest(objectMapper.writeValueAsString(performTransactionRequest))
                .jsonResponse(objectMapper.writeValueAsString(performTransactionResponse))
                .phoneNumber(performTransactionRequest.getPhoneNumber())
                .build()
            );
        }
        return new PerformTransactionResponse();
    }
}
