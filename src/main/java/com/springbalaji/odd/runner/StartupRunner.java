package com.springbalaji.odd.runner;

import com.springbalaji.odd.service.WebhookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class StartupRunner implements CommandLineRunner {

    private final WebhookService webhookService;

    public StartupRunner(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @Override
    public void run(String... args) throws Exception {
        String regNo = "22BCE3373";
        webhookService.executeWorkflow(regNo);
    }
}
