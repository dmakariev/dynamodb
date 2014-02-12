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
package com.makariev.dynamodb.preferences;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dmakariev
 */
@Service
public class UserPreferenceObjectMapperService implements UserPreferenceService {

    private final DynamoDBMapper mapper;
    private final AmazonDynamoDB dynamoDb;

    @Autowired
    public UserPreferenceObjectMapperService(DynamoDBMapper mapper, AmazonDynamoDB dynamoDb) {
        this.mapper = mapper;
        this.dynamoDb = dynamoDb;
    }

    @Override
    public void save(UserPreference userPreference) {
        mapper.save(userPreference);
    }

    @Override
    public void delete(UserPreference userPreference) {
        mapper.delete(userPreference);
    }

    @Override
    public List<UserPreference> findAll() {
        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        //not quite correct; this thing is limmitted by dynamoDb ( there is no count ) 
        final PaginatedScanList result = mapper.scan(UserPreference.class, scanExpression);
        return result;
    }

    @Override
    public UserPreference findByUserNo(int userNo) {
        final UserPreference userPreference = mapper.load(UserPreference.class, userNo);
        return userPreference;
    }

    @Override
    public List<UserPreference> scanByFirstName(String searchForName) {

        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        scanExpression.addFilterCondition("firstName",
                new Condition()
                .withComparisonOperator(ComparisonOperator.CONTAINS)
                .withAttributeValueList(new AttributeValue().withS(searchForName)));

        final PaginatedScanList<UserPreference> scanList = mapper.scan(UserPreference.class, scanExpression);

        return scanList;
    }

    @Override
    public List<UserPreference> scanByLastName(String searchForName) {

        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        scanExpression.addFilterCondition("lastName",
                new Condition()
                .withComparisonOperator(ComparisonOperator.CONTAINS)
                .withAttributeValueList(new AttributeValue().withS(searchForName)));

        final PaginatedScanList<UserPreference> scanList = mapper.scan(UserPreference.class, scanExpression);

        return scanList;
    }

    @Override
    public List<UserPreference> scanByFirstOrLastName(String searchForName) {

        //there is no obious way of implementing (A OR B)
        //Couldn't be done : following Boolean logic : (A OR B) is the same as NOT(NOT A AND NOT B)
        ///
        //The only thing is trying to combine the two results
        final List<UserPreference> firstNameList = scanByFirstName(searchForName);
        final List<UserPreference> lastNameList = scanByLastName(searchForName);

        final List<UserPreference> result = new ArrayList<>();
        result.addAll(firstNameList);

        //don't add the duplicates
        for (UserPreference userPreference : lastNameList) {
            if (false == result.contains(userPreference)) {
                result.addAll(lastNameList);
            }
        }

        return result;
    }

    @Override
    public List<UserPreference> queryByFirstName(String firstName, String lastName) {

        return queryByFirstAndLastNameMixedLevel(firstName, lastName);
        //return queryByNames(firstName, lastName);
    }

    //not working method - throws null pointer
    private List<UserPreference> queryByNames(String firstName, String lastName) {

        DynamoDBQueryExpression<UserPreference> dynamoDBQueryExpression = new DynamoDBQueryExpression<>();
        dynamoDBQueryExpression.withIndexName(UserPreference.NAME_INDEX);

        dynamoDBQueryExpression.withRangeKeyCondition("firstName", new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(firstName)));

        dynamoDBQueryExpression.withRangeKeyCondition("lastName", new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(lastName)));

        final List<UserPreference> list = mapper.query(UserPreference.class, dynamoDBQueryExpression);

        return list;
    }

    private List<UserPreference> queryByFirstAndLastNameMixedLevel(String firstName, String lastName) {

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(UserPreference.TABLE_NAME)
                .withIndexName(UserPreference.NAME_INDEX)
                .withSelect("ALL_ATTRIBUTES")
                .withScanIndexForward(true);

        final HashMap<String, Condition> keyConditions = new HashMap<>();

        //ComparisonOperator.CONTAINS) cannot be used with indexes ( no query, only scan ) 
        keyConditions.put("firstName", new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(firstName)));

        keyConditions.put("lastName", new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(lastName)));

        queryRequest.setKeyConditions(keyConditions);

        final QueryResult result = dynamoDb.query(queryRequest);
        final List<Map<String, AttributeValue>> items = result.getItems();

        final List<UserPreference> list = mapper.marshallIntoObjects(UserPreference.class, items);

        return list;
    }

}
