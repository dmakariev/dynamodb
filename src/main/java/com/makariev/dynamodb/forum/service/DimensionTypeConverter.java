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

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshaller;
import com.makariev.dynamodb.forum.DimensionType;

/**
 *
 * @author dmakariev
 */
// Converts the complex type DimensionType to a string and vice-versa.
public class DimensionTypeConverter implements DynamoDBMarshaller<DimensionType> {

    @Override
    public String marshall(DimensionType value) {
        DimensionType itemDimensions = (DimensionType) value;
        String dimension = null;
        try {
            if (itemDimensions != null) {
                dimension = String.format("%s x %s x %s",
                        itemDimensions.getLength(),
                        itemDimensions.getHeight(),
                        itemDimensions.getThickness());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dimension;
    }

    @Override
    public DimensionType unmarshall(Class<DimensionType> dimensionType, String value) {

        DimensionType itemDimension = new DimensionType();
        try {
            if (value != null && value.length() != 0) {
                String[] data = value.split("x");
                itemDimension.setLength(data[0].trim());
                itemDimension.setHeight(data[1].trim());
                itemDimension.setThickness(data[2].trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemDimension;
    }
}
