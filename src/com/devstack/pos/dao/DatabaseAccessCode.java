package com.devstack.pos.dao;

import com.devstack.pos.dao.custom.CustomerDao;
import com.devstack.pos.dao.custom.ProductDao;
import com.devstack.pos.dao.custom.UserDao;
import com.devstack.pos.dao.custom.impl.CustomerDaoImpl;
import com.devstack.pos.dao.custom.impl.ProductDaoImpl;
import com.devstack.pos.dao.custom.impl.UserDaoImpl;
import com.devstack.pos.db.DbConnection;
import com.devstack.pos.dto.CustomerDto;
import com.devstack.pos.dto.UserDto;
import com.devstack.pos.entity.Customer;
import com.devstack.pos.entity.Product;
import com.devstack.pos.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessCode {
    UserDao userDao = new UserDaoImpl();
    CustomerDao customerDao = new CustomerDaoImpl();
    ProductDao productDao = new ProductDaoImpl();

    //User management
    public boolean createUser(String email, String password) throws ClassNotFoundException, SQLException {
        return userDao.save(new User(email, password));
    }

    public UserDto findUser(String email) throws ClassNotFoundException, SQLException {
        User user = userDao.find(email);
        if (user != null) {
            return new UserDto(user.getEmail(), user.getPassword());
        }
        return null;
    }
    //User management

    //Customer management
    public boolean creatCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {

        return customerDao.save(new Customer(email, name, contact, salary));
    }

    public boolean updateCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {

        return customerDao.update(new Customer(email, name, contact, salary));
    }

    public CustomerDto findCustomer(String email) throws ClassNotFoundException, SQLException {
        Customer customer = customerDao.find(email);
        if (customer != null) {
            return new CustomerDto(
                    customer.getEmail(),
                    customer.getName(),
                    customer.getContact(),
                    customer.getSalary());
        }
        return null;
    }

    public List<CustomerDto> findAllCustomer() throws ClassNotFoundException, SQLException {
        List<CustomerDto> customers = new ArrayList<>();

        for (Customer c : customerDao.findAll()) {
            customers.add(new CustomerDto(
                    c.getEmail(),
                    c.getName(),
                    c.getContact(),
                    c.getSalary()
            ));
        }
        return customers;
    }

    public boolean deleteCustomer(String email) throws ClassNotFoundException, SQLException {
        return customerDao.delete(email);
    }

    public List<CustomerDto> searchCustomer(String searchText) throws ClassNotFoundException, SQLException {

        List<CustomerDto> customers = new ArrayList<>();

        for (Customer c : customerDao.searchCustomer(searchText)) {
            customers.add(new CustomerDto(
                    c.getEmail(),
                    c.getName(),
                    c.getContact(),
                    c.getSalary()
            ));
        }
        return customers;
    }
    //Customer management

    //Product management
    public int getLastProductCode() throws SQLException, ClassNotFoundException {
        return productDao.getLastProductCode();
    }

    public boolean saveProduct(int code, String description) throws SQLException, ClassNotFoundException {
        return productDao.save(new Product(code,description));
    }
    //Product management
}
