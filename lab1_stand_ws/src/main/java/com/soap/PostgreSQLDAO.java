package com.soap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class PostgreSQLDAO {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    public List<Person> getPersons() {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from persons");
            while (rs.next()) {
                String name = rs.getString("name");
                String patronymic = rs.getString("patronymic");
                String surname = rs.getString("surname");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                Person picture = new Person(name, patronymic, surname, age, gender);
                persons.add(picture);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE,
                    null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) { /* Ignored */}
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /* Ignored */}
            }
        }
        return persons;
    }
}