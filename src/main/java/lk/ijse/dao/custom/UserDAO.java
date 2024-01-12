package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    ResultSet checkCredentials(String username, String password) throws SQLException;
    User searchByEmail(String email) throws SQLException;
}
