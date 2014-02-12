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
import com.makariev.dynamodb.config.base.SpringBaseRepositoryLoader;
import com.makariev.dynamodb.data.tables.CreateTableService;
import com.makariev.dynamodb.data.tables.ModifyTablesService;
import com.makariev.dynamodb.forum.BatchAggregate;
import com.makariev.dynamodb.forum.Bicycle;
import com.makariev.dynamodb.forum.Book;
import com.makariev.dynamodb.forum.BookSimple;
import com.makariev.dynamodb.forum.CatalogItem;
import com.makariev.dynamodb.forum.Forum;
import com.makariev.dynamodb.forum.Reply;
import com.makariev.dynamodb.preferences.UserPreference;
import com.makariev.dynamodb.preferences.UserPreferenceFactory;
import com.makariev.dynamodb.preferences.UserPreferenceService;
import org.deltaset.meta.config.DeltasetMetaConfig;
import org.deltaset.meta.config.provider.ClassProvider;
import org.deltaset.meta.config.provider.ViewProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.makariev.dynamodb.forum.Thread;
import com.makariev.dynamodb.forum.service.BatchAggregateService;
import com.makariev.dynamodb.forum.service.BicycleQueryScanService;
import com.makariev.dynamodb.forum.service.BicycleRepository;
import com.makariev.dynamodb.forum.service.BookRepository;
import com.makariev.dynamodb.forum.service.BookSimpleRepository;
import com.makariev.dynamodb.forum.service.CatalogItemRepository;
import com.makariev.dynamodb.forum.service.ForumRepository;
import com.makariev.dynamodb.forum.service.ReplyRepository;
import com.makariev.dynamodb.forum.service.ThreadRepository;

/**
 *
 * @author dmakariev
 */
@Component
public class ApplicationMetaData implements DeltasetMetaConfig {

    private final SpringBaseRepositoryLoader repositoryLoader;

    @Autowired
    public ApplicationMetaData(SpringBaseRepositoryLoader repositoryLoader) {
        this.repositoryLoader = repositoryLoader;
    }

    @Override
    public void configureViews(ViewProvider provider) {
        //do nothing
        //provider.setExcludedFields("id", "version");
        provider.addHideActionNameByRE("^with(.*)$");//methods starting with 'with'
    }

    @Override
    public void configureClasses(ClassProvider provider) {
        provider.setBaseRepositoryLoader(repositoryLoader);
        provider.addRepositories(TableDescription.class, ModifyTablesService.class, CreateTableService.class);
        provider.addRepositories(UserPreference.class, UserPreferenceFactory.class, UserPreferenceService.class);

        provider.addRepositories(Thread.class, ThreadRepository.class);
        provider.addRepositories(BatchAggregate.class, BatchAggregateService.class);
        provider.addRepositories(Bicycle.class, BicycleRepository.class, BicycleQueryScanService.class);
        provider.addRepositories(Book.class, BookRepository.class);
        provider.addRepositories(BookSimple.class, BookSimpleRepository.class);
        provider.addRepositories(CatalogItem.class, CatalogItemRepository.class);
        provider.addRepositories(Forum.class, ForumRepository.class);
        provider.addRepositories(Reply.class, ReplyRepository.class);
    }
}
