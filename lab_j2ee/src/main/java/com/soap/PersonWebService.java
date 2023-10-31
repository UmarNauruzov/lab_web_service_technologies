package com.soap;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.sql.DataSource;


@WebService(serviceName = "PersonService")
public class PersonWebService {
    //    private void PersonWebService() {};
    private List<Person> persons;
    @Resource(lookup = "jdbc/service_lab")
    private DataSource dataSource;

    @WebMethod(operationName = "getPersons")
    public List<Person> getPersons() {
        PostgreSQLDAO dao = getConnection();
        persons = dao.getPersons();
        return persons;
    }
    private PostgreSQLDAO getConnection(){
        PostgreSQLDAO dao = null;
        try{
            dao = new PostgreSQLDAO(dataSource.getConnection());
        } catch (SQLException ex){
            Logger.getLogger(PersonWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dao;
    }
}
