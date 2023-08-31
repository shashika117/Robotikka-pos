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

    }
    public static UserDto findUser(String email) throws ClassNotFoundException, SQLException {

    }
    //User management

    //Customer management
    public static boolean creatCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {


    }

    public static boolean updateCustomer(String email, String name, String contact, double salary) throws ClassNotFoundException, SQLException {

    }

    public static CustomerDto findCustomer(String email) throws ClassNotFoundException, SQLException {

    }

    public static List<CustomerDto> findAllCustomer() throws ClassNotFoundException, SQLException {

    }

    public static boolean deleteCustomer(String email) throws ClassNotFoundException, SQLException {

    }

    throws ClassNotFoundException, SQLException {

    }
    //Customer management

    //Product management
     throws SQLException, ClassNotFoundException {

    }
    public static boolean saveProduct(int code,String description) throws SQLException, ClassNotFoundException {

    }

    //Product management
}
