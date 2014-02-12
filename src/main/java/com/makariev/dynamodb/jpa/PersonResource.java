package com.makariev.dynamodb.jpa;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.makariev.dynamodb.config.base.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Path("person")
@Component
public class PersonResource {

    @Autowired
    private BaseRepository baseRepository;

    @GET
    @Produces(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Person> getPersons() {

        final Long count = baseRepository.countAll(Person.class);
        final List<Person> allPersons = baseRepository.findAll(Person.class, 0, count.intValue());
        return allPersons;
    }

}
