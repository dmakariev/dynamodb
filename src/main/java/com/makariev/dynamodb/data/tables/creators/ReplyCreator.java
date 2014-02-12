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
package com.makariev.dynamodb.data.tables.creators;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.makariev.dynamodb.data.tables.TableRequestCreator;
import com.makariev.dynamodb.forum.Reply;
import java.util.ArrayList;
import java.util.List;

import static com.makariev.dynamodb.data.tables.TableRequestCreatorHelper.basicTable;
/**
 *
 * @author dmakariev
 */
public class ReplyCreator implements TableRequestCreator {

    @Override
    public CreateTableRequest getCreateTableRequest() {
        final String tableName = Reply.TABLE_NAME;
        final String hashKeyName = "Id";
        final ScalarAttributeType hashKeyType = ScalarAttributeType.S;
        final Long readCapacityUnits = 10L;
        final Long writeCapacityUnits = 10L;

        final String rangeKeyName = "ReplyDateTime";
        final ScalarAttributeType rangeKeyType = ScalarAttributeType.S;

        ////////////////
        final CreateTableRequest request = basicTable(
                tableName,
                readCapacityUnits, writeCapacityUnits,
                hashKeyName, hashKeyType
        );
        ///////////////////////
        //defining range key 
        request.getKeySchema().add(new KeySchemaElement()
                .withAttributeName(rangeKeyName)
                .withKeyType(KeyType.RANGE)
        );
        //defining range key 
        request.getAttributeDefinitions().add(new AttributeDefinition()
                .withAttributeName(rangeKeyName)
                .withAttributeType(rangeKeyType)
        );
        //////////////////////

        //follows defininig of a local secondary index
        request.getAttributeDefinitions().add(new AttributeDefinition()
                .withAttributeName("PostedBy")
                .withAttributeType(ScalarAttributeType.S)
        );

        final List<KeySchemaElement> iks = new ArrayList<>();
        iks.add(new KeySchemaElement()
                .withAttributeName(hashKeyName)
                .withKeyType(KeyType.HASH)
        );
        iks.add(new KeySchemaElement()
                .withAttributeName("PostedBy")
                .withKeyType(KeyType.RANGE)
        );

        final LocalSecondaryIndex localSecondaryIndex = new LocalSecondaryIndex()
                .withIndexName("PostedByIndex")
                .withKeySchema(iks)
                .withProjection(new Projection().withProjectionType(ProjectionType.KEYS_ONLY));

        final List<LocalSecondaryIndex> localSecondaryIndexes = new ArrayList<>();
        localSecondaryIndexes.add(localSecondaryIndex);

        request.setLocalSecondaryIndexes(localSecondaryIndexes);

        return request;
    }

}
