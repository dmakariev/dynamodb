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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.deltaset.meta.annotation.DsLabel;

@Entity
@XmlRootElement
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    @Column(name = "version", nullable = false, updatable = false, unique = false)
    private Long version;
    @NotNull
    private String firstName;
    @NotNull
    @Size(min = 3, max = 60)
    private String familyName;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    public Person() {
        this.firstName = "default name";
        this.familyName = "family";
    }

    public Person(String firstName, String familyName) {
        this.firstName = firstName;
        this.familyName = familyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long id) {
        this.version = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public List<String> autocompleteFirstName() {
        return Arrays.asList("Alex", "Boris", "Larry", "Sergei");
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public List<String> autocompleteFamilyName() {
        return Arrays.asList("Ivanov", "Petkov", "Page", "Brin");
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String title() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getFamilyName().toUpperCase());
        sb.append(", ");
        sb.append(getFirstName());
        return sb.toString();
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String oneParameter(final String name) {
        return "simple hi form " + name;
    }

    public String twoParameters(final @DsLabel("name") String name, final @DsLabel("saying") String saying) {
        return name + " is saying " + saying;
    }

    public String default1TwoParameters() {
        return "something nice";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.version != other.version && (this.version == null || !this.version.equals(other.version))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 89 * hash + (this.version != null ? this.version.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", version=" + version + ", firstName=" + firstName + ", familyName=" + familyName + ", dateOfBirth=" + dateOfBirth + '}';
    }
}
