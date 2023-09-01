package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.custom.CustomerDao;
import com.devstack.pos.db.DbConnection;
import com.devstack.pos.entity.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer VALUES (?,?,?,?)";
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1, customer.getEmail());
        statement.setString(2, customer.getName());
        statement.setString(3, customer.getContact());
        statement.setDouble(4, customer.getSalary());

        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {

        String sql =
                "UPDATE customer SET name = ?,contact = ?,salary = ? WHERE email = ?";
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getContact());
        statement.setString(3, String.valueOf(customer.getSalary()));
        statement.setString(4, customer.getEmail());

        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean delete(String email) throws SQLException, ClassNotFoundException {
        String sql =
                "DELETE FROM customer WHERE email = ?";
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1, email);

        return statement.executeUpdate() > 0;
    }

    @Override
    public Customer find(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer WHERE email = ?";
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1, email);
        ResultSet set = statement.executeQuery();
        if (set.next()) {
            return new Customer(
                    set.getString("email"),
                    set.getString("name"),
                    set.getString("contact"),
                    set.findColumn("salary"));
        }
        return null;    }

    @Override
    public List<Customer> findAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer";
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet set = statement.executeQuery();

        List<Customer> customers = new ArrayList<>();
        while (set.next()) {
            customers.add(new Customer(
                    set.getString("email"),
                    set.getString("name"),
                    set.getString("contact"),
                    set.getDouble("salary")
            ));
        }
        return customers;    }

    //--------------------------------------------------
    @Override
    public List<Customer> searchCustomer(String searchText) throws SQLException, ClassNotFoundException {
        searchText = "%" + searchText + "%";
        String sql = "SELECT * FROM customer WHERE email LIKE ? || name LIKE ? || contact LIKE ? || salary LIKE ?";
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1, searchText);
        statement.setString(2, searchText);
        statement.setString(3, searchText);
        statement.setString(4, searchText);
        ResultSet set = statement.executeQuery();

        List<Customer> customers = new ArrayList<>();
        while (set.next()) {
            customers.add(new Customer(
                    set.getString("email"),
                    set.getString("name"),
                    set.getString("contact"),
                    Double.valueOf(set.getString("salary"))
            ));
        }
        return customers;
    }
}
