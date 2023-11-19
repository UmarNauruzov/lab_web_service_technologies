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
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    private String query = "select * from persons";

    public List<Person> getPersons(String query) {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
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
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return persons;
    }
    public List<Person> findPerson(String person_id, String name, String patronymic, String surname, String age, String gender) {
        ArrayList<String> query_where = new ArrayList<String>();
        if (person_id != null && !person_id.isEmpty() ) query_where.add("id='"+person_id+"'");
        if (name != null && !name.isEmpty() ) query_where.add("name='"+name+"'");
        if (patronymic != null && !patronymic.isEmpty()) query_where.add("patronymic='"+patronymic+"'");
        if (surname!= null&&!surname.isEmpty()) query_where.add("surname='"+surname+"'");
        if (age!=null&&!age.isEmpty()) query_where.add("age='"+age+"'");
        if (gender!= null &&!gender.isEmpty()) query_where.add("gender='"+gender+"'");
        String query = query_where.size() >0 ? this.query +
                " where " + String.join(" and ",query_where)
                : this.query;

        List<Person> persons = getPersons(query);
        return persons;
    }
}
