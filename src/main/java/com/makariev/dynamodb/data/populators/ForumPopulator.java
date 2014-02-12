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
import com.makariev.dynamodb.forum.Forum;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dmakariev
 */
public class ForumPopulator implements DataPopulator {

    @Override
    public String getTableName() {
        return Forum.TABLE_NAME;
    }

    @Override
    public List<PutItemRequest> getPutItemRequests() {
        final List<PutItemRequest> requests = new ArrayList<>();
        requests.add(request("Amazon DynamoDB", "Amazon Web Services", 2, 4, 1000));
        requests.add(request("Amazon S3", "Amazon Web Services", 0, null, null));
        return requests;
    }

    private PutItemRequest request(
            String name,
            String category,
            Integer threads,
            Integer messages,
            Integer views) {
        final Map<String, AttributeValue> item = new HashMap<>();
        if (null != name) {
            item.put("Name", new AttributeValue().withS(name));
        }
        if (null != category) {
            item.put("Category", new AttributeValue().withS(category));
        }
        if (null != threads) {
            item.put("Threads", new AttributeValue().withN(threads.toString()));
        }
        if (null != messages) {
            item.put("Messages", new AttributeValue().withN(messages.toString()));
        }
        if (null != views) {
            item.put("Views", new AttributeValue().withN(views.toString()));
        }

        final PutItemRequest request = new PutItemRequest().withTableName(getTableName()).withItem(item);
        return request;
    }

}
