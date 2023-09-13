package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.CrudUtil;
import com.devstack.pos.dao.custom.CustomerDao;
import com.devstack.pos.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO customer VALUES (?,?,?,?)",
                customer.getEmail(),
                customer.getName(),
                customer.getContact(),
                customer.getSalary()
        );
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE customer SET name = ?,contact = ?,salary = ? WHERE email = ?",
                customer.getName(),
                customer.getContact(),
                customer.getSalary(),
                customer.getEmail()
        );
    }

    @Override
    public boolean delete(String email) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM customer WHERE email = ?", email);
    }

    @Override
    public Customer find(String email) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM customer WHERE email = ?", email);
        if (set.next()) {
            return new Customer(
                    set.getString("email"),
                    set.getString("name"),
                    set.getString("contact"),
                    set.findColumn("salary"));
        }
        return null;
    }

    @Override
    public List<Customer> findAll() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM customer");
        List<Customer> customers = new ArrayList<>();
        while (set.next()) {
            customers.add(new Customer(
                    set.getString("email"),
                    set.getString("name"),
                    set.getString("contact"),
                    set.getDouble("salary")
            ));
        }
        return customers;
    }

    //--------------------------------------------------
    @Override
    public List<Customer> searchCustomer(String searchText) throws SQLException, ClassNotFoundException {
        searchText = "%" + searchText + "%";
        ResultSet set = CrudUtil.execute("SELECT * FROM customer WHERE email LIKE ? || name LIKE ? || contact LIKE ? || salary LIKE ?",
                searchText, searchText, searchText, searchText
        );

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
