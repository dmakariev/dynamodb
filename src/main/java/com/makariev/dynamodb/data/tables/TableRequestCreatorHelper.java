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

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dmakariev
 */
public class TableRequestCreatorHelper {

    private TableRequestCreatorHelper() {
    }

    public static CreateTableRequest basicTable(
            String tableName,
            Long readCapacityUnits, Long writeCapacityUnits,
            String hashKeyName, ScalarAttributeType hashKeyType) {

        final List<KeySchemaElement> ks = new ArrayList<>();
        ks.add(new KeySchemaElement()
                .withAttributeName(hashKeyName)
                .withKeyType(KeyType.HASH)
        );

        final List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName(hashKeyName)
                .withAttributeType(hashKeyType)
        );

        // Provide initial provisioned throughput values as Java long data types
        final ProvisionedThroughput provisionedthroughput = new ProvisionedThroughput()
                .withReadCapacityUnits(readCapacityUnits)
                .withWriteCapacityUnits(writeCapacityUnits);

        final CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(ks)
                .withProvisionedThroughput(provisionedthroughput)
                .withAttributeDefinitions(attributeDefinitions);

        return request;
    }
}
