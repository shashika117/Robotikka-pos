package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.CrudUtil;
import com.devstack.pos.dao.custom.ProductDao;
import com.devstack.pos.db.DbConnection;
import com.devstack.pos.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public boolean save(Product product) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO product VALUES (?,?)",product.getCode(),product.getDescription());
    }

    @Override
    public boolean update(Product product) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE product SET description = ? WHERE code = ?",
                product.getCode(),product.getDescription());
    }

    @Override
    public boolean delete(Integer code) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute( "DELETE FROM product WHERE code =?",code);
    }

    @Override
    public Product find(Integer code) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM product WHERE code = ?",code);
        if (set.next()) {
            return new Product(set.getInt(1), set.getString(2));
        }
        return null;
    }

    @Override
    public List<Product> findAll() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT * FROM product");
        List<Product> products = new ArrayList<>();
        while (set.next()) {
            products.add(new Product(set.getInt(1), set.getString(2)));
        }
        return products;
    }

    //---------------------------------
    @Override
    public int getLastProductCode() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT code FROM product ORDER BY code DESC LIMIT 1");
        if (set.next()){
            return set.getInt(1);
        }
        return 0;
    }
}
