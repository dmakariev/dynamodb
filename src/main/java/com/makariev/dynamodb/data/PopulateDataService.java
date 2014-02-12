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
package com.makariev.dynamodb.data;

import com.makariev.dynamodb.config.base.BaseRepository;
import com.makariev.dynamodb.jpa.Person;
import com.makariev.dynamodb.preferences.UserPreference;
import com.makariev.dynamodb.preferences.UserPreferenceService;
import org.deltaset.meta.annotation.DsLabel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dmakariev
 */
@Service
public class PopulateDataService {

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private UserPreferenceService userPreferenceService;

    @Autowired
    private PopulateForumService populateForumService;

    public String populatePersons(@DsLabel("number of persons") int numberOfPersons, @DsLabel("name prefix") String namePrefix) {
        for (int i = 0; i < numberOfPersons; i++) {
            final Person person = new Person(namePrefix + "-first-" + i, namePrefix + "-family-" + i);
            baseRepository.save(person);
        }

        final String endpoint = System.getProperty("dynamodb.endpoint");
        return "Created " + numberOfPersons + " persons, with prefix=" + namePrefix + " dynamodb.endpoint=" + endpoint;
    }

    public String populateUserPreferences(@DsLabel("number of users") int numberOfItems, @DsLabel("name prefix") String namePrefix) {
        for (int i = 0; i < numberOfItems; i++) {
            final UserPreference userPreference = new UserPreference();
            userPreference.setUserNo((int) (Math.random() * 10000));
            userPreference.setFirstName(namePrefix + "-first-" + i);
            userPreference.setLastName(namePrefix + "-last-" + i);
            userPreferenceService.save(userPreference);
        }
        return "Created " + numberOfItems + " UserPreference, with prefix=" + namePrefix;
    }

    public String populateForum() {
        return populateForumService.populateForum();
    }

}
