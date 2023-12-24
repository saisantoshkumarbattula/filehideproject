package org.filehide.dao;

import org.filehide.db.MyConnection;
import org.filehide.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean isExists(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("select email from users");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            if(resultSet.getString(1).equals(email)) return true;
        }
        return false;
    }
    public static int saveUser(User user) throws SQLException{
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("insert into users values(default, ?, ?");
        statement.setString(1, user.getName());
        statement.setString(2,user.getEmail());
        return statement.executeUpdate();
    }
}
