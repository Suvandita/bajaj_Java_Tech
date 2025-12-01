package com.springbalaji.odd.service;

import com.springbalaji.odd.dto.GenerateWebhookRequest;
import com.springbalaji.odd.dto.GenerateWebhookResponse;
import com.springbalaji.odd.dto.FinalQueryRequest;
import com.springbalaji.odd.util.SqlSolutionProvider;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void executeWorkflow(String regNo) {  
        GenerateWebhookRequest request = new GenerateWebhookRequest("John Doe", regNo, "john@example.com");
        ResponseEntity<GenerateWebhookResponse> response = restTemplate.postForEntity(
                "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA",
                request,
                GenerateWebhookResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String webhookUrl = response.getBody().getWebhook();
            String accessToken = response.getBody().getAccessToken();

            String finalQuery = SqlSolutionProvider.getFinalSql(regNo);

            FinalQueryRequest finalRequest = new FinalQueryRequest(finalQuery);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", accessToken);

            HttpEntity<FinalQueryRequest> entity = new HttpEntity<>(finalRequest, headers);
            ResponseEntity<String> submitResponse = restTemplate.postForEntity(webhookUrl, entity, String.class);

            System.out.println("Submission response: " + submitResponse.getStatusCode() + " " + submitResponse.getBody());
        } else {
            System.out.println("Failed to get webhook. Status: " + response.getStatusCode());
        }
    }
}
