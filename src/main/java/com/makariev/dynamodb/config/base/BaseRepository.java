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

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dmakariev
 */
@Repository
public class BaseRepository {

    private EntityManager entityManager;

    @PersistenceContext
    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Object newEntity(Class entityClass) {
        return newInstance(entityClass);
    }

    @Transactional
    public void delete(Class entityClass, Object id) {
        final Object forDelete = entityManager.getReference(entityClass, id);
        entityManager.remove(forDelete);
    }

    @Transactional
    public <T> T save(T entity) {
        try {
            final T saved = entityManager.merge(entity);
            return saved;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public <T> T findById(Class<T> entityClass, Object id) {
        final T entity = entityManager.find(entityClass, id);
        if (null == entity) {
            throw new IllegalArgumentException("person with id=" + id + " not found");
        }
        return entity;
    }

    public <T> Long countAll(Class<T> entityClass) {
        return entityManager.createQuery("SELECT count(o) from " + entityClass.getSimpleName() + " o", Long.class).
                getSingleResult();
    }

    @Transactional
    public <T> List<T> findAll(Class<T> entityClass, Integer firstResult, Integer maxResult) {
        final List<T> founded = entityManager.createQuery("SELECT o FROM " + entityClass.getSimpleName() + " o").
                setFirstResult(firstResult).
                setMaxResults(maxResult).getResultList();
        return new ArrayList<T>(founded);
    }

    private static <T> T newInstance(Class<T> currentClass) {
        try {
            final T newEntity = currentClass.newInstance();
            return newEntity;
        } catch (InstantiationException ex) {
            throw new RuntimeException("could not create " + currentClass.getName(), ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("could not create " + currentClass.getName(), ex);
        }
    }
}
