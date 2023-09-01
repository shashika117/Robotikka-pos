package com.devstack.pos.dao.custom;

import com.devstack.pos.dao.CrudDao;
import com.devstack.pos.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao extends CrudDao<Product,Integer> {
    /*public boolean saveProduct(Product product) throws SQLException, ClassNotFoundException;
    public boolean updateProduct(Product product) throws SQLException, ClassNotFoundException;
    public boolean deleteProduct(int code) throws SQLException, ClassNotFoundException;
    public Product findProduct(int code) throws SQLException, ClassNotFoundException;
    public List<Product> findAllProducts() throws SQLException, ClassNotFoundException;*/


    //--------------------------------
    public int getLastProductCode() throws SQLException, ClassNotFoundException;
}
