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

import java.util.List;
import org.deltaset.meta.annotation.DsLabel;

/**
 *
 * @author dmakariev
 */
public interface UserPreferenceService {

    void delete(UserPreference userPreference);

    List<UserPreference> findAll();

    UserPreference findByUserNo(@DsLabel(value = "userNo") int userNo);

    void save(UserPreference userPreference);

    List<UserPreference> scanByFirstName(@DsLabel(value = "first name") String name);

    List<UserPreference> scanByLastName(@DsLabel(value = "last name") String searchForName);

    List<UserPreference> scanByFirstOrLastName(@DsLabel(value = "first or last partial name") String name);

    List<UserPreference> queryByFirstName(
            @DsLabel(value = "first name") String firstName,
            @DsLabel(value = "last name") String lastName);

}
