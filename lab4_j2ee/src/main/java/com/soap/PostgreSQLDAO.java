package com.soap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSQLDAO {
    Statement stmt = null;
    ResultSet rs = null;

    public PostgreSQLDAO(){};

    private Connection connection;
    public PostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }

    public LinkedHashSet<Person> getPersonsFields(List<String> searchArgs) {
        LinkedHashSet<Person> persons = new LinkedHashSet<>();
        try (Statement stmt = connection.createStatement();) {
            for (String searchArg : searchArgs) {
                ResultSet rs = stmt.executeQuery("SELECT t.* FROM persons t WHERE (t.*)::text LIKE '%"
                        + searchArg + "%'");
                while (rs.next()) {
                    int person_id = rs.getInt("id");
                    String name = rs.getString("name");
                    String patronymic = rs.getString("patronymic");
                    String surname = rs.getString("surname");
                    int age = rs.getInt("age");
                    String gender = rs.getString("gender");
                    Person person = new Person(person_id, name, patronymic, surname, age, gender);
                    persons.add(person);
                }
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
