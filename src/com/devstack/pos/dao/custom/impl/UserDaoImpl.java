package com.devstack.pos.dao.custom.impl;

import com.devstack.pos.dao.custom.UserDao;
import com.devstack.pos.db.DbConnection;
import com.devstack.pos.dto.UserDto;
import com.devstack.pos.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean saveUser(User user) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO user VALUES (?,?)";

        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPassword());
        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean updateUser(User user) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE user SET password = ? WHERE email = ?";

        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(2, user.getEmail());
        statement.setString(1, user.getPassword());
        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean deleteUser(String email) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM user WHERE email =?";

        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1, email);
        return statement.executeUpdate() > 0;
    }

    @Override
    public User findUser(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM user WHERE email = ?";

        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        statement.setString(1, email);

        ResultSet set = statement.executeQuery();
        if (set.next()) {
            return new User(set.getString(1), set.getString(2));
        }
        return null;
    }

    @Override
    public List<User> findAllUsers() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM user";

        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet set = statement.executeQuery();
        List<User> users = new ArrayList<>();
        while (set.next()) {
            users.add(new User(set.getString(1), set.getString(2)));
        }
        return users;
    }
}
