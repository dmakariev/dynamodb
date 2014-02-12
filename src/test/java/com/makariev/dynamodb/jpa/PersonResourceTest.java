/*
 * Copyright (C) 2013 Dimitar Makariev (http://makariev.com). All rights reserved.
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
package com.makariev.dynamodb.jpa;

import com.makariev.dynamodb.jpa.Person;
import com.makariev.dynamodb.jpa.PersonResource;
import com.makariev.dynamodb.config.base.BaseRepository;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dmakariev
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
    "classpath:test-applicationContext.xml"})
public class PersonResourceTest {

    public PersonResourceTest() {
    }

    @Autowired
    private PersonResource personResource;
    @Autowired
    private BaseRepository baseRepository;

    /**
     * Test of getPersons method, of class PersonResource.
     */
    @Test
    @Transactional
    public void testGetPersons() {
        assertThat("personResource is not NULL", personResource, is(notNullValue()));
        assertThat("baseRepository is not NULL", baseRepository, is(notNullValue()));

        final Person newPerson = new Person("new", "person");
        baseRepository.save(newPerson);
        
        assertThat("personResource.getPersons() is not NULL", personResource.getPersons(), is(notNullValue()));
        assertThat("personResource.getPersons() is not NULL", personResource.getPersons().size(), is(1));
    }

}
