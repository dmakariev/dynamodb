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
package com.makariev.dynamodb.config.menu;

import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.makariev.dynamodb.data.PopulateDataService;
import com.makariev.dynamodb.forum.BatchAggregate;
import com.makariev.dynamodb.forum.Bicycle;
import com.makariev.dynamodb.forum.Book;
import com.makariev.dynamodb.forum.BookSimple;
import com.makariev.dynamodb.forum.CatalogItem;
import com.makariev.dynamodb.forum.Forum;
import com.makariev.dynamodb.forum.Reply;
import com.makariev.dynamodb.jpa.Person;
import com.makariev.dynamodb.preferences.UserPreference;
import org.deltaset.menu.DeltasetMenuConfig;
import org.deltaset.menu.MenuItem;
import org.deltaset.menu.MenuProvider;
import org.springframework.stereotype.Component;
import com.makariev.dynamodb.forum.Thread;

/**
 *
 * @author dmakariev
 */
@Component
public class ApplicationMenu implements DeltasetMenuConfig {

    @Override
    public void configureMenu(MenuProvider menuProvider) {
        menuProvider.setDefaultSrc("/ds/home.xhtml");
        menuProvider.addTopMenuItem(topMenuItem());
    }

    private MenuItem topMenuItem() {
        final MenuItem topMenuItem = MenuProvider.Builder.itemTemplate("Home", "/ds/home.xhtml", "*");
        topMenuItem.add(
                MenuProvider.Builder.itemGroup("DynamoDB Admin", "*").add(
                        MenuProvider.Builder.itemEntity("Tables Service", TableDescription.class, "*"),
                        MenuProvider.Builder.itemEntity("Populate Data", PopulateDataService.class, "*")//
                ),
                MenuProvider.Builder.itemGroup("Simple classes", "*").add(
                        MenuProvider.Builder.itemEntity("Person (JPA)", Person.class, "*"),
                        MenuProvider.Builder.itemEntity("UserPreference", UserPreference.class, "*")//
                ),
                MenuProvider.Builder.itemGroup("Forum", "*").add(
                        MenuProvider.Builder.itemEntity("Thread", Thread.class, "*"),
                        MenuProvider.Builder.itemEntity("Reply", Reply.class, "*"),
                        MenuProvider.Builder.itemEntity("Forum", Forum.class, "*")//
                ),
                MenuProvider.Builder.itemGroup("Product Catalog", "*").add(
                        MenuProvider.Builder.itemEntity("CatalogItem", CatalogItem.class, "*"),
                        MenuProvider.Builder.itemEntity("BookSimple", BookSimple.class, "*"),
                        MenuProvider.Builder.itemEntity("Book", Book.class, "*"),
                        MenuProvider.Builder.itemEntity("Bicycle", Bicycle.class, "*"),
                        MenuProvider.Builder.itemEntity("BatchAggregate", BatchAggregate.class, "*")//
                )
        );

        return topMenuItem;
    }
}
