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
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTableResult;
import java.util.ArrayList;
import java.util.List;
import org.deltaset.meta.annotation.DsLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dmakariev
 */
@Service
public class ModifyTablesService {

    @Autowired
    private AmazonDynamoDB client;

    public List<TableDescription> showTables() {

        final List<TableDescription> result = new ArrayList<>();

        String lastEvaluatedTableName = null;
        do {

            final ListTablesRequest listTablesRequest = new ListTablesRequest()
                    .withLimit(10)
                    .withExclusiveStartTableName(lastEvaluatedTableName);

            final ListTablesResult requestResult = client.listTables(listTablesRequest);
            lastEvaluatedTableName = requestResult.getLastEvaluatedTableName();

            for (String tableName : requestResult.getTableNames()) {
                final DescribeTableResult describeResult = client.describeTable(tableName);

                final TableDescription tableDescription = describeResult.getTable();
                result.add(tableDescription);
            }

        } while (lastEvaluatedTableName != null);

        return result;
    }

    public void warnDelete(TableDescription tableDescription) {
        final String tableName = tableDescription.getTableName();
        final DeleteTableRequest deleteTableRequest = new DeleteTableRequest()
                .withTableName(tableName);
        final DeleteTableResult requestResult = client.deleteTable(deleteTableRequest);
    }

    public TableDescription updateThroughput(TableDescription tableDescription, ProvisionedThroughput provisionedThroughput) {
        final String tableName = tableDescription.getTableName();
        final UpdateTableRequest updateTableRequest = new UpdateTableRequest()
                .withTableName(tableName)
                .withProvisionedThroughput(provisionedThroughput);

        final UpdateTableResult requestResult = client.updateTable(updateTableRequest);
        return requestResult.getTableDescription();
    }

    public Object tableByName(@DsLabel("table name") String tableName) {
        final TableDescription tableDescription = loadTableDescription(tableName);
        if (null != tableDescription) {
            return tableDescription;
        } else {
            return "Table '" + tableName + "' doesn't exist";
        }
    }

    TableDescription loadTableDescription(String tableName) {
        try {
            final DescribeTableResult describeResult = client.describeTable(tableName);
            final TableDescription tableDescription = describeResult.getTable();
            return tableDescription;
        } catch (AmazonClientException ex) {
            //do nothing
        }
        return null;
    }
}
