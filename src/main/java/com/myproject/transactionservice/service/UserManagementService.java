package com.myproject.transactionservice.service;

import com.myproject.transactionservice.domain.UserDataDTO;
import com.myproject.transactionservice.domain.UserDataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserManagementService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.management.service.url}")
    private String userManagementServiceUrl;

    public UserDataResponse getUserData(String phoneNumber) {
        log.info("Get User Data by phoneNumber: {}", phoneNumber);
        ResponseEntity<UserDataResponse> getUserResponse =
                restTemplate.getForEntity(userManagementServiceUrl + "/api/user/" + phoneNumber, UserDataResponse.class);
        log.info("Response: {}", getUserResponse);

        return getUserResponse.getBody();
    }

    public UserDataResponse updateUserData(String phoneNumber, UserDataDTO userDataDTO) {
        log.info("Get User Data by phoneNumber: {}", phoneNumber);
        ResponseEntity<UserDataResponse> updateUserResponse = restTemplate.postForEntity(userManagementServiceUrl + "/api/user/" + phoneNumber,
                userDataDTO, UserDataResponse.class);
        log.info("Response: {}", updateUserResponse);

        return updateUserResponse.getBody();
    }
}
