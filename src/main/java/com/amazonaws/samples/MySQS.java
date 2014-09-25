package com.amazonaws.samples;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

import java.io.IOException;
import java.util.List;

public class MySQS {

    public static void main(String[] args) throws IOException {

        AmazonSQSClient sqsClient = new AmazonSQSClient();

        // **** Create Queue *****
 //       CreateQueueResult result = sqsClient.createQueue("Bonz");
 //       System.out.println( result.getQueueUrl() );


        // ***** Get Queue *****
        String queueUrl = null;
        ListQueuesResult listQueuesResult = sqsClient.listQueues();
        for (String queue : listQueuesResult.getQueueUrls()) {
            if (queue.endsWith("/Bonz")) {
                queueUrl = queue;
                break;
            }
        }
        if (queueUrl==null) {
            System.out.println("queue not found");
            return;
        }


        // ***** Send Message *****
        //sqsClient.sendMessage(queueUrl, "Just Fooling Around Again");

        ReceiveMessageResult messageResult = sqsClient.receiveMessage(queueUrl);
        List<Message> messages = messageResult.getMessages();
        for (Message message : messages) {
            System.out.println("Got: " + message.getBody());


            sqsClient.deleteMessage(queueUrl,message.getReceiptHandle() );

        }



    }



}
