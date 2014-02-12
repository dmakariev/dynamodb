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
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
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
//@Service
public class UserPreferenceLowLevelAPIService implements UserPreferenceService {

    private final AmazonDynamoDB dynamoDb;

    @Autowired
    public UserPreferenceLowLevelAPIService(AmazonDynamoDB dynamoDb) {
        this.dynamoDb = dynamoDb;
    }

    @Override
    public void save(UserPreference up) {
        final Map<String, AttributeValue> item = new HashMap<>();

        AttributeValue userNo = new AttributeValue()
                .withN(String.valueOf(up.getUserNo()));
        item.put("userNo", userNo);

        AttributeValue firstName = new AttributeValue()
                .withS(up.getFirstName());
        item.put("firstName", firstName);

        AttributeValue lastName = new AttributeValue()
                .withS(up.getLastName());
        item.put("lastName", lastName);

        PutItemRequest request = new PutItemRequest()
                .withTableName(UserPreference.TABLE_NAME)
                .withItem(item);

        dynamoDb.putItem(request);
    }

    @Override
    public void delete(UserPreference up) {
        //Key primaryKey = new Key().withHashKeyElement(up);
        Map<String, AttributeValue> primaryKey = new HashMap<>();
        AttributeValue avKey = new AttributeValue();
        avKey.withN(String.valueOf(up.getUserNo()));
        primaryKey.put("userNo", avKey);
        DeleteItemRequest request = new DeleteItemRequest()
                .withTableName(UserPreference.TABLE_NAME)
                .withKey(primaryKey);

        dynamoDb.deleteItem(request);
    }

    @Override
    public List<UserPreference> findAll() {

        ScanRequest request = new ScanRequest();
        request.setTableName(UserPreference.TABLE_NAME);
        ScanResult result = dynamoDb.scan(request);

        final List<Map<String, AttributeValue>> items = result.getItems();

        return toUserPreferenceList(items);

    }

    @Override
    public UserPreference findByUserNo(int userNo) {
        Map<String, AttributeValue> primaryKey = new HashMap<>();
        AttributeValue avKey = new AttributeValue();
        avKey.withN(String.valueOf(userNo));
        primaryKey.put("userNo", avKey);
//Key primaryKey = new Key().withHashKeyElement(userNoAttr);
        GetItemRequest request = new GetItemRequest()
                .withTableName(UserPreference.TABLE_NAME)
                .withKey(primaryKey);

        GetItemResult result = dynamoDb.getItem(request);

        Map userPreference = result.getItem();
        return toUserPreference(userPreference);
    }

    private List<UserPreference> toUserPreferenceList(final List<Map<String, AttributeValue>> items) {
        final List<UserPreference> resultList = new ArrayList<>();
        for (Map<String, AttributeValue> map : items) {
            resultList.add(toUserPreference(map));
        }
        return resultList;
    }

    private UserPreference toUserPreference(Map<String, AttributeValue> map) {

        final UserPreference up = new UserPreference();

        if (map.containsKey("userNo")) {
            int userNo = Integer.parseInt(map.get("userNo").getN());
            up.setUserNo(userNo);
        }

        if (map.containsKey("firstName")) {
            String firstName = map.get("firstName").getS();
            up.setFirstName(firstName);
        }

        if (map.containsKey("lastName")) {
            String lastName = map.get("lastName").getS();
            up.setLastName(lastName);
        }

        return up;
    }

    @Override
    public List<UserPreference> scanByFirstName(String searchForName) {
        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        scanExpression.addFilterCondition("firstName",
                new Condition()
                .withComparisonOperator(ComparisonOperator.CONTAINS)
                .withAttributeValueList(new AttributeValue().withS(searchForName)));
        final Map<String, Condition> scanFilter = scanExpression.getScanFilter();

        final ScanResult scanResult = dynamoDb.scan(UserPreference.TABLE_NAME, scanFilter);

        final List<Map<String, AttributeValue>> items = scanResult.getItems();

        return toUserPreferenceList(items);
    }

    @Override
    public List<UserPreference> scanByLastName(String searchForName) {
        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        scanExpression.addFilterCondition("lastName",
                new Condition()
                .withComparisonOperator(ComparisonOperator.CONTAINS)
                .withAttributeValueList(new AttributeValue().withS(searchForName)));
        final Map<String, Condition> scanFilter = scanExpression.getScanFilter();

        final ScanResult scanResult = dynamoDb.scan(UserPreference.TABLE_NAME, scanFilter);

        final List<Map<String, AttributeValue>> items = scanResult.getItems();

        return toUserPreferenceList(items);
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

        QueryResult result = dynamoDb.query(queryRequest);
        List<Map<String, AttributeValue>> items = result.getItems();

        return toUserPreferenceList(items);
    }

}
