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
package com.makariev.dynamodb.config.base;

import java.util.List;
import org.deltaset.meta.annotation.DsAcceptEntity;
import org.deltaset.meta.annotation.DsDefaultMethod;
import org.deltaset.meta.annotation.DsFirstResult;
import org.deltaset.meta.annotation.DsMaxResult;
import org.deltaset.meta.annotation.DsReturnEntity;
import org.deltaset.meta.extension.ExtractEntityIdHelper;

/**
 *
 * @author dmakariev
 */
public class BaseModelRepository implements ModelRepository {

    private final BaseRepository baseRepository;
    private final Class currentClass;

    BaseModelRepository(BaseRepository baseRepository, Class entityClass) {
        this.baseRepository = baseRepository;
        this.currentClass = entityClass;
    }

    @DsReturnEntity
    @Override
    public Object newEntity() {
        return baseRepository.newEntity(currentClass);
    }

    @Override
    public Long countAll() {
        return baseRepository.countAll(currentClass);
    }

    @DsAcceptEntity
    @Override
    public void warnDelete(Object entity) {
        final Object id = new ExtractEntityIdHelper().extractEntityId(entity);
        baseRepository.delete(currentClass, id);
    }

    @DsDefaultMethod
    @Override
    public List findAll(@DsFirstResult Integer firstResult, @DsMaxResult Integer maxResult) {
        return baseRepository.findAll(currentClass, firstResult, maxResult);
    }

    @DsAcceptEntity
    @Override
    public Object save(Object entity) {
        return baseRepository.save(entity);
    }

    @Override
    public boolean disableWarnDelete(Object entity) {
        final Object id = new ExtractEntityIdHelper().extractEntityId(entity);
        final boolean disabledDelete = (null == id);
        return disabledDelete;
    }
}
