package com.example.taskmanager.workerEmail;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.UserService;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.*;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmailWorker {

    @Autowired
    private TaskService taskService;

    @Autowired
    @Lazy
    public void setTaskService (TaskService taskService) {
        this.taskService = taskService;
    }



    public void sendTaskListEmail(String email) {
        List<Task> tasks = taskService.findTasksByUserEmail(email);
        if (tasks.isEmpty()) {
            System.out.println("Nie ma zadań dla uzytkownika" + email);

            return;
        }

        String taskTitles = tasks.stream()
                .map(Task::getTitle)
                .collect(Collectors.joining(", "));

        ClientOptions options = ClientOptions.builder()
                .apiKey("1dc8ae0ba784e089f6c729848eaea77f")
                .apiSecretKey("ebcede21737fc4f348870215d5674ef3")
                .build();


        MailjetClient client = new MailjetClient(options);

        TransactionalEmail message1 = TransactionalEmail
                .builder()
                .to(new SendContact(email))
                .from(new SendContact("marcelinap98@tlen.pl", "Task Manager"))
                .textPart("Twoje zadania: \n" + taskTitles)
                .subject("Lista zadań")
                .trackOpens(TrackOpens.ENABLED)
                .header("test-header-key", "test-value")
                .build();

        SendEmailsRequest request = SendEmailsRequest
                .builder()
                .message(message1)
                .build();

        try {
            SendEmailsResponse response = request.sendWith(client);
            System.out.println(response);
        } catch (MailjetException e) {
            System.out.println("Błąd podczas wysyłania emaila do " + email);
            throw new RuntimeException(e);
        }
    }
}

//        String taskTitles = tasksInProgress.stream().map(Task::getTitle).collect(Collectors.joining(", "));
//        System.out.println("started");
//        ClientOptions options = ClientOptions.builder()
//                .apiKey("1dc8ae0ba784e089f6c729848eaea77f")
//                .apiSecretKey("ebcede21737fc4f348870215d5674ef3")
//                .build();
//
//        MailjetClient client = new MailjetClient(options);
//
//        TransactionalEmail message1 = TransactionalEmail
//                .builder()
//                .to(new SendContact())
//                .from(new SendContact("marcelinap98@tlen.pl", "Mailjet integration test"))
//                .textPart("DOKONCZ ZADANIA! \n"+ taskTitles)
//                .subject("This is the subject")
//                .trackOpens(TrackOpens.ENABLED)
//                .header("test-header-key", "test-value")
//                .build();
//
//        SendEmailsRequest request = SendEmailsRequest
//                .builder()
//                .message(message1) // you can add up to 50 messages per request
//                .build();
//
//        // act
//        try {
//            SendEmailsResponse response = request.sendWith(client);
//            System.out.println(response);
//        } catch (MailjetException e) {
//            System.out.println("Error while sending email");
//            throw new RuntimeException(e);
//        }
//
//    }
//}
