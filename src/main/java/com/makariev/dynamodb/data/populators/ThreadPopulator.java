/*
 * Copyright 2014 dmakariev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.makariev.dynamodb.data.populators;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.makariev.dynamodb.data.DataPopulator;
import com.makariev.dynamodb.forum.Thread;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dmakariev
 */
public class ThreadPopulator implements DataPopulator {

    @Override
    public String getTableName() {
        return Thread.TABLE_NAME;
    }

    @Override
    public List<PutItemRequest> getPutItemRequests() {
        final List<PutItemRequest> requests = new ArrayList<>();
        final String tableName = getTableName();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        long time1 = (new Date()).getTime() - (7 * 24 * 60 * 60 * 1000); // 7 days ago
        long time2 = (new Date()).getTime() - (14 * 24 * 60 * 60 * 1000); // 14 days ago
        long time3 = (new Date()).getTime() - (21 * 24 * 60 * 60 * 1000); // 21 days ago

        Date date1 = new Date();
        date1.setTime(time1);

        Date date2 = new Date();
        date2.setTime(time2);

        Date date3 = new Date();
        date3.setTime(time3);

        // Add threads.
        {
            final Map<String, AttributeValue> forum = new HashMap<>();
            forum.put("ForumName", new AttributeValue().withS("Amazon DynamoDB"));
            forum.put("Subject", new AttributeValue().withS("DynamoDB Thread 1"));
            forum.put("Message", new AttributeValue().withS("DynamoDB thread 1 message"));
            forum.put("LastPostedBy", new AttributeValue().withS("User A"));
            forum.put("LastPostedDateTime", new AttributeValue().withS(dateFormatter.format(date2)));
            forum.put("Views", new AttributeValue().withN("0"));
            forum.put("Replies", new AttributeValue().withN("0"));
            forum.put("Answered", new AttributeValue().withN("0"));
            forum.put("Tags", new AttributeValue().withSS(Arrays.asList("index", "primarykey", "table")));

            final PutItemRequest forumRequest = new PutItemRequest().withTableName(tableName).withItem(forum);
            requests.add(forumRequest);
        }

        {
            final Map<String, AttributeValue> forum = new HashMap<>();

            forum.put("ForumName", new AttributeValue().withS("Amazon DynamoDB"));
            forum.put("Subject", new AttributeValue().withS("DynamoDB Thread 2"));
            forum.put("Message", new AttributeValue().withS("DynamoDB thread 2 message"));
            forum.put("LastPostedBy", new AttributeValue().withS("User A"));
            forum.put("LastPostedDateTime", new AttributeValue().withS(dateFormatter.format(date3)));
            forum.put("Views", new AttributeValue().withN("0"));
            forum.put("Replies", new AttributeValue().withN("0"));
            forum.put("Answered", new AttributeValue().withN("0"));
            forum.put("Tags", new AttributeValue().withSS(Arrays.asList("index", "primarykey", "rangekey")));

            final PutItemRequest forumRequest = new PutItemRequest().withTableName(tableName).withItem(forum);
            requests.add(forumRequest);
        }

        {
            final Map<String, AttributeValue> forum = new HashMap<>();

            forum.put("ForumName", new AttributeValue().withS("Amazon S3"));
            forum.put("Subject", new AttributeValue().withS("S3 Thread 1"));
            forum.put("Message", new AttributeValue().withS("S3 Thread 3 message"));
            forum.put("LastPostedBy", new AttributeValue().withS("User A"));
            forum.put("LastPostedDateTime", new AttributeValue().withS(dateFormatter.format(date1)));
            forum.put("Views", new AttributeValue().withN("0"));
            forum.put("Replies", new AttributeValue().withN("0"));
            forum.put("Answered", new AttributeValue().withN("0"));
            forum.put("Tags", new AttributeValue().withSS(Arrays.asList("largeobjects", "multipart upload")));

            final PutItemRequest forumRequest = new PutItemRequest().withTableName(tableName).withItem(forum);
            requests.add(forumRequest);
        }

        return requests;
    }

}
