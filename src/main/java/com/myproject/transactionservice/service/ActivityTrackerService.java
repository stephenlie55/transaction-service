package com.myproject.transactionservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.transactionservice.domain.UserActivityRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class ActivityTrackerService {

    @Value("${user.activity.topic}")
    private String userActivityTopic;

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(UserActivityRequest userActivityRequest){
        try {
            userActivityRequest.setRequestId(userActivityRequest.getActivityName().concat("-").concat(generateRequestId(6)));
            String jsonObject = new ObjectMapper().writeValueAsString(userActivityRequest);
            log.info("Send user activity request {} : ", jsonObject);
            jmsTemplate.convertAndSend(userActivityTopic, jsonObject);
        } catch (Exception e) {
            log.error("Error send user activity request {}",e.getMessage());
        }
    }

    public static String generateRequestId(int length) {
        String randomNumber = "123456789";
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() < length) {
            int index = new Random().nextInt(randomNumber.length());
            stringBuilder.append(randomNumber.charAt(index));
        }
        return stringBuilder.toString();
    }
}
