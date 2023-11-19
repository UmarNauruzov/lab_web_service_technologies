package com.soap;

import com.soap.Person;
import com.soap.PersonWebService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSQLDAO {
    Statement stmt = null;
    ResultSet rs = null;
    private Connection connection;
     public PostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Person> getPersons() {
        List<Person> persons = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from persons");
            while (rs.next()) {
                String name = rs.getString("name");
                String patronymic = rs.getString("patronymic");
                String surname = rs.getString("surname");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                Person person = new Person(name, patronymic, surname, age, gender);
                persons.add(person);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return persons;
    }
}