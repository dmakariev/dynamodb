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
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.makariev.dynamodb.data.tables.TableRequestCreator;
import com.makariev.dynamodb.preferences.UserPreference;
import java.util.ArrayList;
import java.util.List;

import static com.makariev.dynamodb.data.tables.TableRequestCreatorHelper.basicTable;
/**
 *
 * @author dmakariev
 */
public class UserPreferenceCreator implements TableRequestCreator {

    /*
     *
     * introducing Global Secondary Index:
     * http://aws.typepad.com/aws/2013/12/now-available-global-secondary-indexes-for-amazon-dynamodb.html
     *
     * create table with Global Secondary Index :
     * http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/GSILowLevelJava.html
     *
     * create table with Local Secondary Index:
     * http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/LSILowLevelJava.html
     *
     */
    @Override
    public CreateTableRequest getCreateTableRequest() {
        final String tableName = UserPreference.TABLE_NAME;
        final String hashKeyName = "userNo";
        final ScalarAttributeType hashKeyType = ScalarAttributeType.N;
        final Long readCapacityUnits = 10L;
        final Long writeCapacityUnits = 10L;

        ////////////////
        final CreateTableRequest request = basicTable(
                tableName,
                readCapacityUnits, writeCapacityUnits,
                hashKeyName, hashKeyType
        );

        //define GlobalSecondaryIndex
        request.getAttributeDefinitions().add(new AttributeDefinition()
                .withAttributeName("firstName")
                .withAttributeType(ScalarAttributeType.S)
        );

        request.getAttributeDefinitions().add(new AttributeDefinition()
                .withAttributeName("lastName")
                .withAttributeType(ScalarAttributeType.S)
        );

        // NameIndex
        final GlobalSecondaryIndex nameIndex = new GlobalSecondaryIndex()
                .withIndexName(UserPreference.NAME_INDEX)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits(10L)
                        .withWriteCapacityUnits(10L))
                .withProjection(new Projection().withProjectionType("ALL"));

        final List<KeySchemaElement> indexKeySchema = new ArrayList<>();

        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName("firstName")
                .withKeyType(KeyType.HASH)
        );
        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName("lastName")
                .withKeyType(KeyType.RANGE)
        );

        nameIndex.setKeySchema(indexKeySchema);

        request.withGlobalSecondaryIndexes(nameIndex);
        return request;
    }

}
