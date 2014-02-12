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
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.makariev.dynamodb.data.tables.TableRequestCreator;
import com.makariev.dynamodb.data.tables.creators.UserPreferenceCreator;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author dmakariev
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:test-applicationContext.xml"})
public class UserPreferenceServiceTest {

    public UserPreferenceServiceTest() {
    }

    @Autowired
    private UserPreferenceService service;
    @Autowired
    private AmazonDynamoDB client;

    /**
     * Test of save method, of class UserPreferenceObjectMapperService.
     */
    @Test
    public void testUserPreferenceCRUD() {

        System.out.println("create table");
        createUserPreferenceTable();

        System.out.println("save");

        final int USER_NO = 5;

        final UserPreference userPreference = new UserPreference();
        userPreference.setUserNo(USER_NO);
        userPreference.setFirstName("FirstName");
        userPreference.setLastName("LastName");

        service.save(userPreference);

        final List<UserPreference> userPreferences = service.findAll();
        assertThat("userPreferences is not Null", userPreferences, is(notNullValue()));
        assertThat("userPreferences.size()=1", userPreferences.size(), is(1));

        userPreference.setLastName("UpdateLastName");

        service.save(userPreference);

        final UserPreference loadedUserPreference = service.findByUserNo(USER_NO);

        assertThat("last name is updated", loadedUserPreference.getLastName(), is("UpdateLastName"));

        service.delete(userPreference);

        final List<UserPreference> reloadedUserPreferences = service.findAll();
        assertThat("reloadedUserPreferences is not Null", reloadedUserPreferences, is(notNullValue()));
        assertThat("reloadedUserPreferences.size()=0", reloadedUserPreferences.size(), is(0));
    }

    private void createUserPreferenceTable() {
        final TableRequestCreator tableRequestCreator = new UserPreferenceCreator();
        final CreateTableRequest createTableRequest = tableRequestCreator.getCreateTableRequest();
        final CreateTableResult result = client.createTable(createTableRequest);
    }

}
