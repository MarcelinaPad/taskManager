package com.example.taskmanager.workerEmail;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.*;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmailWorker {

    @Autowired
    TaskService taskService;

    @Scheduled(initialDelay = 0, fixedRate = 100000) // 300000 ms = 5 minut
    void sendEmails () {

        List<Task> tasksInProgress = taskService.findTasksInProgress();
        if(tasksInProgress.isEmpty()){
            System.out.println(" nie ma taskow");

            return;
        }

        String taskTitles = tasksInProgress.stream().map(Task::getTitle).collect(Collectors.joining(", "));
        System.out.println("started");
        ClientOptions options = ClientOptions.builder()
                .apiKey("5e1b7f6b15872c79ba89fa2b18d2e946")
                .apiSecretKey("b4cd7c67c0199f0247cf94e5dcee009e")
                .build();

        MailjetClient client = new MailjetClient(options);

        TransactionalEmail message1 = TransactionalEmail
                .builder()
                .to(new SendContact("matipro11@gmail.com", "stanislav"))
                .from(new SendContact("matipro11@gmail.com", "Mailjet integration test"))
                .textPart("DOKONCZ ZADANIA LENIU! \n"+ taskTitles)
                .subject("This is the subject")
                .trackOpens(TrackOpens.ENABLED)
                .header("test-header-key", "test-value")
                .customID("custom-id-value")
                .build();

        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(message1) // you can add up to 50 messages per request
                .build();

        // act
        try {
            SendEmailsResponse response = request.sendWith(client);
            System.out.println(response);
        } catch (MailjetException e) {
            System.out.println("bagno error");
            throw new RuntimeException(e);
        }

    }
}
