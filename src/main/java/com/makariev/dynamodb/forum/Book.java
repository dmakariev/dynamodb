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
package com.makariev.dynamodb.forum;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.makariev.dynamodb.forum.service.DimensionTypeConverter;
import java.util.Set;

/**
 *
 * @author dmakariev
 */
@DynamoDBTable(tableName = "ProductCatalog")
public class Book {

    private int id;
    private String title;
    private String isbn;
    private Set<String> bookAuthors;
    private DimensionType dimensionType;

    @DynamoDBHashKey(attributeName = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DynamoDBAttribute(attributeName = "ISBN")
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String ISBN) {
        this.isbn = ISBN;
    }

    @DynamoDBAttribute(attributeName = "Authors")
    public Set<String> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(Set<String> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    @DynamoDBMarshalling(marshallerClass = DimensionTypeConverter.class)
    public DimensionType getDimensions() {
        return dimensionType;
    }

    public void setDimensions(DimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    @Override
    public String toString() {
        return "Book [ISBN=" + isbn + ", bookAuthors=" + bookAuthors
                + ", dimensionType=" + dimensionType + ", Id=" + id
                + ", Title=" + title + "]";
    }
}
