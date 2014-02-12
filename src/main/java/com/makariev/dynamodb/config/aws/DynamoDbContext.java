/*
 * Copyright (C) 2014 Dimitar Makariev (http://makariev.com). All rights reserved.
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
package com.makariev.dynamodb.config.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author dmakariev
 */
@Configuration
public class DynamoDbContext {

    private final AmazonDynamoDB dynamoDb;
    private final DynamoDBMapper mapper;

    public DynamoDbContext() throws IOException {
        final AWSCredentials credentials = new PropertiesCredentials(
                DynamoDbContext.class.getResourceAsStream("/AwsCredentials.properties"));
//        final AWSCredentials credentials = new BasicAWSCredentials("", "");
        this.dynamoDb = new AmazonDynamoDBClient(credentials);
        final String endpoint = System.getProperty("dynamodb.endpoint");
        this.dynamoDb.setEndpoint(String.format(endpoint));

        this.mapper = new DynamoDBMapper(this.dynamoDb);
    }

    @Bean
    public AmazonDynamoDB dynamoDb() {
        return dynamoDb;
    }

    @Bean
    public DynamoDBMapper mapper() {
        return mapper;
    }

}
