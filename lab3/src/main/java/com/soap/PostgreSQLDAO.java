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
        try (Connection connection = ConnectionUtil.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from persons");
            while (rs.next()) {
                int person_id = rs.getInt("id");
                String name = rs.getString("name");
                String patronymic = rs.getString("patronymic");
                String surname = rs.getString("surname");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                Person picture = new Person(person_id, name, patronymic, surname, age, gender);
                persons.add(picture);
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
    public String createPerson(String name, String patronymic, String surname, int age, String gender) {
        String status = "0";

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            System.out.println("INSERT INTO persons(name, patronymic, surname, age, gender) values ('" +
                    name + "', '" + patronymic + "', '" + surname + "', " + age + ", '" + gender + "');");
            int rs = stmt.executeUpdate("INSERT INTO persons(name, patronymic, surname, age, gender) values ('" +
                    name + "', '" + patronymic + "', '" + surname + "', " + age + ", '" + gender + "');");
            status = Integer.toString(rs);

        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
        return status;
    }

    public String deletePerson(int person_id) {
        String status = "0";

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            int rs = stmt.executeUpdate("DELETE FROM persons WHERE id='" + person_id + "';");
            status = Integer.toString(rs);
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
        return status;
    }

    public String updatePerson(int person_id, List<String> updateArgs) {
        String status = "0";
        String updateFields = String.join(", ", updateArgs);

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            int rs = stmt.executeUpdate("UPDATE persons SET " + updateFields + " WHERE id=" + person_id + ";");
            status = Integer.toString(rs);
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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
        return status;
    }
}