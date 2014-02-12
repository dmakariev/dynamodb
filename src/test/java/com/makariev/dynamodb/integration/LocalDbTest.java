/*
 * Copyright (C) 2013 Dimitar Makariev (http://makariev.com). All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.makariev.dynamodb.integration;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.makariev.dynamodb.config.aws.DynamoDbContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author dmakariev
 */
public class LocalDbTest {

    private static final String ENDPOINT = System.getProperty("dynamodb.endpoint");

    @Test
    public void worksWithAwsDynamoDb() throws IOException {
        final AWSCredentials credentials = new PropertiesCredentials(
                DynamoDbContext.class.getResourceAsStream("/AwsCredentials.properties"));
        //final AWSCredentials credentials = new BasicAWSCredentials("", "");
        final AmazonDynamoDB aws = new AmazonDynamoDBClient(credentials);
        System.out.println("=ENDPOINT="+ENDPOINT);
        assertThat("AmazonDynamoDB aws is not null", aws, is(notNullValue()));
        //aws.setEndpoint(String.format("http://localhost:%s", LocalDbTest.ENDPOINT));
        aws.setEndpoint(LocalDbTest.ENDPOINT);
        assertThat("aws is not null", is(notNullValue()));
        // and now your code
        final String tableName = "booo";
        boolean tableExists = false;
        try {
            final DescribeTableResult describeResult = aws.describeTable(tableName);
            assertThat("describeResult", describeResult, is(notNullValue()));
            final TableDescription tableDescription = describeResult.getTable();
            assertThat("tableDescription", tableDescription, is(notNullValue()));
            assertThat("tableDescription.getTableName()", tableDescription.getTableName(), is(tableName));
            tableExists = true;
        } catch (AmazonClientException ex) {
           
            //throw ex;
            //tableExists = true;
            //fail("unspecified error; re-run the tests after clean");
        }

        if(tableExists){
            return;
        }
        final CreateTableRequest request = new CreateTableRequest();
        request.setTableName(tableName);
        final ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(1000L, 1000L);
        request.setProvisionedThroughput(provisionedThroughput);
        final Collection<KeySchemaElement> keySchemaCollection = new ArrayList<KeySchemaElement>();
        KeySchemaElement element = new KeySchemaElement("identifier", KeyType.HASH);//new KeySchemaElement("name", "string");
        keySchemaCollection.add(element);

        request.setKeySchema(keySchemaCollection);

        final Collection<AttributeDefinition> attributeList = new ArrayList<AttributeDefinition>();
//        final AttributeDefinition defS = new AttributeDefinition("nameS", ScalarAttributeType.S);
//        
//        final AttributeDefinition defB = new AttributeDefinition("nameB", ScalarAttributeType.B);
//        final AttributeDefinition defN = new AttributeDefinition("nameN", ScalarAttributeType.N);
        final AttributeDefinition defIdentifier = new AttributeDefinition("identifier", ScalarAttributeType.S);
//        attributeList.add(defS);
//        attributeList.add(defB);
//        attributeList.add(defN);
        attributeList.add(defIdentifier);

        request.setAttributeDefinitions(attributeList);

        final CreateTableResult result = aws.createTable(request);
        final TableDescription tableDescription2 = result.getTableDescription();
        tableDescription2.getTableName();
        assertThat("tableDescription.getTableName()", tableDescription2.getTableName(), is(tableName));
    }

}
