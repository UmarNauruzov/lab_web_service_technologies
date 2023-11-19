package com.soap;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/persons")
@Produces({MediaType.APPLICATION_JSON})

public class PersonResource {
    @GET
    public List<Person> getPerson(@QueryParam("id") String id, @QueryParam("name") String name, @QueryParam("patronymic") String patronymic,
                                  @QueryParam("surname") String surname, @QueryParam("age") String age,
                                  @QueryParam("gender") String gender) {
        List<Person> person = new PostgreSQLDAO().findPerson(id, name, patronymic, surname, age, gender);
        return person;
    }
}