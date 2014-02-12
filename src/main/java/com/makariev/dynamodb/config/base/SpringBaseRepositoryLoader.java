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

import javax.persistence.Entity;
import org.deltaset.meta.extension.BaseRepositoryLoader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author dmakariev
 */
@Component
public class SpringBaseRepositoryLoader implements BaseRepositoryLoader, BeanFactoryAware {

    private BeanFactory beanFactory;

    private BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object baseRepository(Class entityClass) {

        final BaseRepository baseRepository = getBeanFactory().getBean(BaseRepository.class);
        return new BaseModelRepository(baseRepository, entityClass);
    }
    
    @Override
    public boolean isSupported(Class checkClass) {
        return null != checkClass.getAnnotation(Entity.class);
    }

    @Override
    public Class getDefaultClass() {
        return ModelRepository.class;
    }
}
