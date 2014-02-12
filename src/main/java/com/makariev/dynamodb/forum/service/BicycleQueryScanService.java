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
package com.makariev.dynamodb.forum.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.makariev.dynamodb.forum.Bicycle;
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
public class BicycleQueryScanService {

    @Autowired
    private DynamoDBMapper mapper;

    public List<Bicycle> findBicyclesOfSpecificTypeWithMultipleThreads(
            @DsLabel("number of parallel threads") int numberOfThreads,
            @DsLabel("bicycleType") String bicycleType) throws Exception {

        final List<Bicycle> result = new ArrayList<>();

        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        scanExpression.addFilterCondition("ProductCategory",
                new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS("Bicycle")));
        scanExpression.addFilterCondition("BicycleType",
                new Condition()
                .withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(bicycleType)));
        final List<Bicycle> scanResult = mapper.parallelScan(Bicycle.class, scanExpression, numberOfThreads);

        for (Bicycle bicycle : scanResult) {
            result.add(bicycle);
        }
        return result;
    }

}
