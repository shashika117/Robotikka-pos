package com.devstack.pos.dao;

import com.devstack.pos.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao <T,Id> {
    public boolean save(T t) throws SQLException, ClassNotFoundException;
    public boolean update(T t) throws SQLException, ClassNotFoundException;
    public boolean delete(Id id) throws SQLException, ClassNotFoundException;
    public T find(Id id) throws SQLException, ClassNotFoundException;
    public List<T> findAll() throws SQLException, ClassNotFoundException;
}
