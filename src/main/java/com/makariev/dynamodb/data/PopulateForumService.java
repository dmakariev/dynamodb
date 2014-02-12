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
package com.makariev.dynamodb.data;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.makariev.dynamodb.data.populators.ForumPopulator;
import com.makariev.dynamodb.data.populators.ProductCatalogPopulator;
import com.makariev.dynamodb.data.populators.ReplyPopulator;
import com.makariev.dynamodb.data.populators.ThreadPopulator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dmakariev
 */
@Service
public class PopulateForumService {

    @Autowired
    private AmazonDynamoDB client;

    private final Set<Class<? extends DataPopulator>> dataPopulators;

    public PopulateForumService() {
        this.dataPopulators = new HashSet<>(Arrays.asList(
                ForumPopulator.class,
                ProductCatalogPopulator.class,
                ReplyPopulator.class,
                ThreadPopulator.class
        ));
    }

    public String populateForum() {
        final StringBuilder sb = new StringBuilder();
        for (Class<? extends DataPopulator> dataPopulatorClass : dataPopulators) {
            try {
                final DataPopulator dataPopulator = dataPopulatorClass.newInstance();
                final List<PutItemRequest> putItemRequests = dataPopulator.getPutItemRequests();
                for (PutItemRequest putItemRequest : putItemRequests) {
                    final PutItemResult itemResult = client.putItem(putItemRequest);
                }
                sb.append(" table '").append(dataPopulator.getTableName()).append("' SUCCESSFULLY POPULATED;");
            } catch (AmazonClientException | InstantiationException | IllegalAccessException | UnsupportedOperationException ex) {
                sb.append("  FAILED ex:").append(ex.toString()).append(';');
            }
        }
        final String message = sb.toString();
        return message.isEmpty() ? "nothing is executed" : message;
    }

}
