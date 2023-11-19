package com.soap;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.LinkedHashSet;

@RequestScoped
@Path("/persons")
@Produces({MediaType.APPLICATION_JSON})
public class PersonResource {
    @Resource(lookup = "jdbc/service_lab")
    private DataSource dataSource;

    @GET
    public LinkedHashSet<Person> getPersons(@QueryParam("searchParam") final List<String> searchArgs) {
        System.out.println(searchArgs);
        return new PostgreSQLDAO(getConnection()).getPersonsFields(searchArgs);
    }
    private Connection getConnection() {
        Connection result = null;
        try {
            result = dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(PersonResource.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        return result;
    }
}