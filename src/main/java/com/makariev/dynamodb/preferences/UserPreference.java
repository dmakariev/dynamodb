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

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.util.Objects;

/**
 *
 * @author dmakariev
 */
@DynamoDBTable(tableName = UserPreference.TABLE_NAME)
public class UserPreference {

    public static final String TABLE_NAME = "TestUserPereference";
    public static final String NAME_INDEX = "NameIndex";
    private int userNo;
    private String firstName;
    private String lastName;
    private Boolean autoLogin;
    private Boolean vibrate;
    private Boolean silent;
    private String colorTheme;

    @DynamoDBHashKey(attributeName = "userNo")
    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    @DynamoDBAttribute(attributeName = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @DynamoDBIndexRangeKey(attributeName = "lastName",globalSecondaryIndexName = UserPreference.NAME_INDEX)
    //@DynamoDBAttribute(attributeName = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @DynamoDBAttribute(attributeName = "autoLogin")
    public Boolean getAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(Boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    @DynamoDBAttribute(attributeName = "vibrate")
    public Boolean getVibrate() {
        return vibrate;
    }

    public void setVibrate(Boolean vibrate) {
        this.vibrate = vibrate;
    }

    @DynamoDBAttribute(attributeName = "silent")
    public Boolean getSilent() {
        return silent;
    }

    public void setSilent(Boolean silent) {
        this.silent = silent;
    }

    @DynamoDBAttribute(attributeName = "colorTheme")
    public String getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(String colorTheme) {
        this.colorTheme = colorTheme;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.userNo;
        hash = 37 * hash + Objects.hashCode(this.firstName);
        hash = 37 * hash + Objects.hashCode(this.lastName);
        hash = 37 * hash + Objects.hashCode(this.autoLogin);
        hash = 37 * hash + Objects.hashCode(this.vibrate);
        hash = 37 * hash + Objects.hashCode(this.silent);
        hash = 37 * hash + Objects.hashCode(this.colorTheme);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserPreference other = (UserPreference) obj;
        if (this.userNo != other.userNo) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.autoLogin, other.autoLogin)) {
            return false;
        }
        if (!Objects.equals(this.vibrate, other.vibrate)) {
            return false;
        }
        if (!Objects.equals(this.silent, other.silent)) {
            return false;
        }
        if (!Objects.equals(this.colorTheme, other.colorTheme)) {
            return false;
        }
        return true;
    }

}
