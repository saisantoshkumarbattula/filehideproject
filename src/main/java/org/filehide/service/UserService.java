package org.filehide.service;

import org.filehide.dao.UserDAO;
import org.filehide.model.User;

import java.sql.SQLException;

public class UserService {
    public static int saveUser(User user) {
        try {
            if (UserDAO.isExists(user.getEmail())) {
                return 0;
            } else {
                return UserDAO.saveUser(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException();
        }
//        return -1;
    }
}
