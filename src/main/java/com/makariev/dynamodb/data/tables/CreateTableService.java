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
package com.makariev.dynamodb.data.tables;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.makariev.dynamodb.data.tables.creators.ForumCreator;
import com.makariev.dynamodb.data.tables.creators.ProductCatalogCreator;
import com.makariev.dynamodb.data.tables.creators.ReplyCreator;
import com.makariev.dynamodb.data.tables.creators.ThreadCreator;
import com.makariev.dynamodb.data.tables.creators.UserPreferenceCreator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dmakariev
 */
@Service
public class CreateTableService {

    @Autowired
    private AmazonDynamoDB client;

    private final Set<Class<? extends TableRequestCreator>> tableCreators;

    public CreateTableService() {
        this.tableCreators = new HashSet<>(Arrays.asList(
                ForumCreator.class,
                ProductCatalogCreator.class,
                ReplyCreator.class,
                ThreadCreator.class,
                UserPreferenceCreator.class
        ));
    }

    public String createPredefinedTables() {
        final StringBuilder sb = new StringBuilder();
        for (Class<? extends TableRequestCreator> tableCreatorClass : tableCreators) {
            try {
                final TableRequestCreator tableCreator = tableCreatorClass.newInstance();
                final CreateTableRequest createTableRequest = tableCreator.getCreateTableRequest();
                final CreateTableResult result = client.createTable(createTableRequest);
                final TableDescription tableDescription = result.getTableDescription();
                sb.append(" table '").append(tableDescription.getTableName()).append("' SUCCESSFULLY CREATED;");
            } catch (AmazonClientException | InstantiationException | IllegalAccessException | UnsupportedOperationException ex) {
                sb.append("  FAILED ex:").append(ex.toString()).append(';');
            }
        }
        final String message = sb.toString();
        return message.isEmpty() ? "nothing is executed" : message;
    }

}
