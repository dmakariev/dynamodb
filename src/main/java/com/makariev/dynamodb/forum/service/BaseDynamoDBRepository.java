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
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import java.util.List;

/**
 *
 * @author dmakariev
 * @param <T>
 */
public class BaseDynamoDBRepository<T> {

    private final DynamoDBMapper mapper;
    private final Class<T> clazz;

    public BaseDynamoDBRepository(DynamoDBMapper mapper, Class<T> clazz) {
        this.mapper = mapper;
        this.clazz = clazz;
    }

    public List<T> findAll() {
        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        //not quite correct; this thing is limmitted by dynamoDb ( there is no count ) 
        final PaginatedScanList<T> result = mapper.scan(clazz, scanExpression);
        return result;
    }

    public void save(T item) {
        //saves or updates
        mapper.save(item);
    }

    public T loadById(Object id) {
        final T item = mapper.load(clazz, id);
        return item;
    }

    public void delete(T item) {
        mapper.delete(item);
    }
}
