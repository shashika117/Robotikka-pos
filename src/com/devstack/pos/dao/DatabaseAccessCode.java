package com.devstack.pos.dao;

import com.devstack.pos.db.DbConnection;
import com.devstack.pos.dto.CustomerDto;
import com.devstack.pos.dto.UserDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessCode {
    //User management
    public static boolean createUser(String email, String password) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO user VALUES (?,?)";

        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, password);
        return statement.executeUpdate() > 0;
    }

    public static UserDto findUser(String email) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM user WHERE email = ?";

        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
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

        String sql = "INSERT INTO customer VALUES (?,?,?,?)";
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, name);
        statement.setString(3, contact);
        statement.setDouble(4, salary);

        return statement.executeUpdate() > 0;
    }

    public static boolean updateCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {

        String sql =
                "UPDATE customer SET name = ?,contact = ?,salary = ? WHERE email = ?";
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, contact);
        statement.setString(3, String.valueOf(salary));
        statement.setString(4, email);

        return statement.executeUpdate() > 0;
    }

    public static CustomerDto findCustomer(String email) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM customer WHERE email = ?";
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
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
        String sql = "SELECT * FROM customer";
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet set = statement.executeQuery();

        List<CustomerDto> customers = new ArrayList<>();
        while (set.next()) {
            customers.add(new CustomerDto(
                    set.getString("email"),
                    set.getString("name"),
                    set.getString("contact"),
                    set.getDouble("salary")
            ));
        }
        return customers;
    }

    public static boolean deleteCustomer(String email) throws ClassNotFoundException, SQLException {
        String sql =
                "DELETE FROM customer WHERE email = ?";
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1, email);

        return statement.executeUpdate() > 0;
    }

    public static List<CustomerDto> searchCustomer(String searchText) throws ClassNotFoundException, SQLException {
        searchText = "%" + searchText + "%";
         String sql = "SELECT * FROM customer WHERE email LIKE ? || name LIKE ? || contact LIKE ? || salary LIKE ?";
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1, searchText);
        statement.setString(2, searchText);
        statement.setString(3, searchText);
        statement.setString(4, searchText);
        ResultSet set = statement.executeQuery();

        List<CustomerDto> customers = new ArrayList<>();
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

    //Product management
    public static int getLastProductCode() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT code FROM product ORDER BY code DESC LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        if (set.next()){
            return set.getInt(1);
        }
        return 0;
    }
    public static boolean saveProduct(int code,String description) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO product VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,code);
        statement.setString(2,description);
        return statement.executeUpdate()>0;
    }

    //Product management
}
