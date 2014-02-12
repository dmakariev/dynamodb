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
package com.makariev.dynamodb.data.populators;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.makariev.dynamodb.data.DataPopulator;
import com.makariev.dynamodb.forum.ProductCatalog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dmakariev
 */
public class ProductCatalogPopulator implements DataPopulator {

    @Override
    public String getTableName() {
        return ProductCatalog.TABLE_NAME;
    }

    @Override
    public List<PutItemRequest> getPutItemRequests() {
        final List<PutItemRequest> requests = new ArrayList<>();
        final String tableName = getTableName();

        // Add books.
        {
            final Map<String, AttributeValue> item = new HashMap<>();
            item.put("Id", new AttributeValue().withN("101"));
            item.put("Title", new AttributeValue().withS("Book 101 Title"));
            item.put("ISBN", new AttributeValue().withS("111-1111111111"));
            item.put("Authors", new AttributeValue().withSS(Arrays.asList("Author1")));
            item.put("Price", new AttributeValue().withN("2"));
            item.put("Dimensions", new AttributeValue().withS("8.5 x 11.0 x 0.5"));
            item.put("PageCount", new AttributeValue().withN("500"));
            item.put("InPublication", new AttributeValue().withN("1"));
            item.put("ProductCategory", new AttributeValue().withS("Book"));

            final PutItemRequest itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            requests.add(itemRequest);
        }

        {
            final Map<String, AttributeValue> item = new HashMap<>();
            item.put("Id", new AttributeValue().withN("102"));
            item.put("Title", new AttributeValue().withS("Book 102 Title"));
            item.put("ISBN", new AttributeValue().withS("222-2222222222"));
            item.put("Authors", new AttributeValue().withSS(Arrays.asList("Author1", "Author2")));
            item.put("Price", new AttributeValue().withN("20"));
            item.put("Dimensions", new AttributeValue().withS("8.5 x 11.0 x 0.8"));
            item.put("PageCount", new AttributeValue().withN("600"));
            item.put("InPublication", new AttributeValue().withN("1"));
            item.put("ProductCategory", new AttributeValue().withS("Book"));

            final PutItemRequest itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            requests.add(itemRequest);
        }

        {
            final Map<String, AttributeValue> item = new HashMap<>();

            item.put("Id", new AttributeValue().withN("103"));
            item.put("Title", new AttributeValue().withS("Book 103 Title"));
            item.put("ISBN", new AttributeValue().withS("333-3333333333"));
            item.put("Authors", new AttributeValue().withSS(Arrays.asList("Author1", "Author2")));
            // Intentional. Later we run scan to find price error. Find items > 1000 in price.            
            item.put("Price", new AttributeValue().withN("2000"));
            item.put("Dimensions", new AttributeValue().withS("8.5 x 11.0 x 1.5"));
            item.put("PageCount", new AttributeValue().withN("600"));
            item.put("InPublication", new AttributeValue().withN("0"));
            item.put("ProductCategory", new AttributeValue().withS("Book"));

            final PutItemRequest itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            requests.add(itemRequest);
        }

        {
            final Map<String, AttributeValue> item = new HashMap<>();

            // Add bikes.
            item.put("Id", new AttributeValue().withN("201"));
            item.put("Title", new AttributeValue().withS("18-Bike-201")); // Size, followed by some title.
            item.put("Description", new AttributeValue().withS("201 Description"));
            item.put("BicycleType", new AttributeValue().withS("Road"));
            item.put("Brand", new AttributeValue().withS("Mountain A")); // Trek, Specialized.
            item.put("Price", new AttributeValue().withN("100"));
            item.put("Gender", new AttributeValue().withS("M")); // Men's
            item.put("Color", new AttributeValue().withSS(Arrays.asList("Red", "Black")));
            item.put("ProductCategory", new AttributeValue().withS("Bicycle"));

            final PutItemRequest itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            requests.add(itemRequest);
        }

        {
            final Map<String, AttributeValue> item = new HashMap<>();

            item.put("Id", new AttributeValue().withN("202"));
            item.put("Title", new AttributeValue().withS("21-Bike-202"));
            item.put("Description", new AttributeValue().withS("202 Description"));
            item.put("BicycleType", new AttributeValue().withS("Road"));
            item.put("Brand", new AttributeValue().withS("Brand-Company A"));
            item.put("Price", new AttributeValue().withN("200"));
            item.put("Gender", new AttributeValue().withS("M"));
            item.put("Color", new AttributeValue().withSS(Arrays.asList("Green", "Black")));
            item.put("ProductCategory", new AttributeValue().withS("Bicycle"));

            final PutItemRequest itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            requests.add(itemRequest);
        }

        {
            final Map<String, AttributeValue> item = new HashMap<>();

            item.put("Id", new AttributeValue().withN("203"));
            item.put("Title", new AttributeValue().withS("19-Bike-203"));
            item.put("Description", new AttributeValue().withS("203 Description"));
            item.put("BicycleType", new AttributeValue().withS("Road"));
            item.put("Brand", new AttributeValue().withS("Brand-Company B"));
            item.put("Price", new AttributeValue().withN("300"));
            item.put("Gender", new AttributeValue().withS("W")); // Women's
            item.put("Color", new AttributeValue().withSS(Arrays.asList("Red", "Green", "Black")));
            item.put("ProductCategory", new AttributeValue().withS("Bicycle"));

            final PutItemRequest itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            requests.add(itemRequest);
        }

        {
            final Map<String, AttributeValue> item = new HashMap<>();

            item.put("Id", new AttributeValue().withN("204"));
            item.put("Title", new AttributeValue().withS("18-Bike-204"));
            item.put("Description", new AttributeValue().withS("204 Description"));
            item.put("BicycleType", new AttributeValue().withS("Mountain"));
            item.put("Brand", new AttributeValue().withS("Brand-Company B"));
            item.put("Price", new AttributeValue().withN("400"));
            item.put("Gender", new AttributeValue().withS("W"));
            item.put("Color", new AttributeValue().withSS(Arrays.asList("Red")));
            item.put("ProductCategory", new AttributeValue().withS("Bicycle"));

            final PutItemRequest itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            requests.add(itemRequest);
        }

        {
            final Map<String, AttributeValue> item = new HashMap<>();

            item.put("Id", new AttributeValue().withN("205"));
            item.put("Title", new AttributeValue().withS("20-Bike-205"));
            item.put("Description", new AttributeValue().withS("205 Description"));
            item.put("BicycleType", new AttributeValue().withS("Hybrid"));
            item.put("Brand", new AttributeValue().withS("Brand-Company C"));
            item.put("Price", new AttributeValue().withN("500"));
            item.put("Gender", new AttributeValue().withS("B")); // Boy's
            item.put("Color", new AttributeValue().withSS(Arrays.asList("Red", "Black")));
            item.put("ProductCategory", new AttributeValue().withS("Bicycle"));

            final PutItemRequest itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
            requests.add(itemRequest);
        }

        return requests;
    }

}
