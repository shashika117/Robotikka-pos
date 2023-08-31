package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.custom.ProductDao;
import com.devstack.pos.db.DbConnection;
import com.devstack.pos.entity.Product;
import com.devstack.pos.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public boolean saveProduct(Product product) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO product VALUES (?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, product.getCode());
        statement.setString(2, product.getDescription());
        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException, ClassNotFoundException {
        String sql =
                "UPDATE product SET description = ? WHERE code = ?";
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1, product.getDescription());
        statement.setInt(2, product.getCode());

        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean deleteProduct(int code) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM product WHERE code =?";

        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setInt(1, code);
        return statement.executeUpdate() > 0;
    }

    @Override
    public Product findProduct(int code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product WHERE code = ?";

        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setInt(1, code);

        ResultSet set = statement.executeQuery();
        if (set.next()) {
            return new Product(set.getInt(1), set.getString(2));
        }
        return null;
    }

    @Override
    public List<Product> findAllProducts() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM product";

        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet set = statement.executeQuery();
        List<Product> products = new ArrayList<>();
        while (set.next()) {
            products.add(new Product(set.getInt(1), set.getString(2)));
        }
        return products;
    }

    //---------------------------------
    @Override
    public int getLastProductCode() throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql="SELECT code FROM product ORDER BY code DESC LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet set = statement.executeQuery();
        if (set.next()){
            return set.getInt(1);
        }
        return 0;
    }
}
