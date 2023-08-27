package com.devstack.pos.dao;

import com.devstack.pos.dto.CustomerDto;
import com.devstack.pos.dto.UserDto;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class DatabaseAccessCode {
    //User management
    public static boolean createUser(String email, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/robotikka", "root", "1234");
        String sql = "INSERT INTO user VALUES (?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, password);
        return statement.executeUpdate() > 0;
    }

    public static UserDto findUser(String email) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/robotikka", "root", "1234");
        String sql = "SELECT * FROM user WHERE email = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);

        ResultSet set = statement.executeQuery();
        if (set.next()) {
            return new UserDto(set.getString(1), set.getString(2));
        }
        return null;
    }
    //User management

    //Customer management
    public static boolean creatCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/robotikka", "root", "1234");
        String sql = "INSERT INTO customer VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, name);
        statement.setString(3, contact);
        statement.setDouble(4, salary);

        return statement.executeUpdate() > 0;
    }

    public static boolean updateCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/robotikka", "root", "1234");
        String sql =
                "UPDATE customer SET name = ?,contact = ?,salary = ? WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, contact);
        statement.setString(3, String.valueOf(salary));
        statement.setString(4, email);

        return statement.executeUpdate() > 0;
    }

    public static CustomerDto findCustomer(String email) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka", "root", "1234");
        String sql = "SELECT * FROM customer WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        ResultSet set = statement.executeQuery();
        if (set.next()) {
            return new CustomerDto(
                    set.getString("email"),
                    set.getString("name"),
                    set.getString("contact"),
                    set.findColumn("salary"));
        }
        return null;
    }

    public static List<CustomerDto> findAllCustomer() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka", "root", "1234");
        String sql = "SELECT * FROM customer";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();

        List<CustomerDto> customers = null;
        while (set.next()) {
            customers.add(new CustomerDto(
                    set.getString("email"),
                    set.getString("name"),
                    set.getString("contact"),
                    Double.valueOf(set.getString("salary"))
            ));
        }
        return customers;
    }

    public static boolean deleteCustomer(String email) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/robotikka", "root", "1234");
        String sql =
                "DELETE FROM customer WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);

        return statement.executeUpdate() > 0;
    }

    public static List<CustomerDto> searchCustomer(String searchText) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/robotikka", "root", "1234");
        String sql = "SELECT * FROM customer WHERE email LIKE %?% || name LIKE %?% || contact LIKE %?% || salary LIKE %?%";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,searchText);
        statement.setString(2,searchText);
        statement.setString(3,searchText);
        statement.setString(4,searchText);
        ResultSet set = statement.executeQuery();

        List<CustomerDto> customers = null;
        while (set.next()) {
            customers.add(new CustomerDto(
                    set.getString("email"),
                    set.getString("name"),
                    set.getString("contact"),
                    Double.valueOf(set.getString("salary"))
            ));
        }
        return customers;
    }
    //Customer management
}
