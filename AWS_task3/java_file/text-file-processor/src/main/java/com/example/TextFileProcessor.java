package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class TextFileProcessor implements RequestHandler<S3Event, String> {

    private final S3Client s3 = S3Client.create();
    private final DynamoDbClient dynamo = DynamoDbClient.create();

    @Override
    public String handleRequest(S3Event event, Context context) {
        String bucket = event.getRecords().get(0).getS3().getBucket().getName();
        String key = event.getRecords().get(0).getS3().getObject().getKey();
        context.getLogger().log("Processing file: " + key);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    s3.getObject(GetObjectRequest.builder().bucket(bucket).key(key).build())
            ));
            String content = reader.lines().collect(Collectors.joining("\n"));
            reader.close();

            int lineCount = content.split("\r?\n").length;
            int wordCount = content.trim().isEmpty() ? 0 : content.trim().split("\\s+").length;
            int charCount = content.length();
            String preview = content.substring(0, Math.min(100, content.length()));

            Map<String, AttributeValue> item = new HashMap<>();
            item.put("fileName", AttributeValue.builder().s(key).build());
            item.put("lineCount", AttributeValue.builder().n(String.valueOf(lineCount)).build());
            item.put("wordCount", AttributeValue.builder().n(String.valueOf(wordCount)).build());
            item.put("charCount", AttributeValue.builder().n(String.valueOf(charCount)).build());
            item.put("preview", AttributeValue.builder().s(preview).build());
            item.put("processedDate", AttributeValue.builder().s(Instant.now().toString()).build());

            dynamo.putItem(PutItemRequest.builder()
                    .tableName("FileProcessingResults")
                    .item(item)
                    .build());

            context.getLogger().log("Successfully processed: " + key);
            return "SUCCESS";
        } catch (Exception e) {
            context.getLogger().log("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
