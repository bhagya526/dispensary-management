package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public  ResultSet checkCredentials(String username, String password) throws SQLException {
        ResultSet resultSet = SQLUtil.main( "SELECT * FROM user WHERE user_name=? AND password=?", username, password);
        return resultSet;
    }
    @Override
    public  User searchByEmail(String email) throws SQLException {

        ResultSet resultSet =  SQLUtil.main( "SELECT * FROM user WHERE user_name = ?", email);
        if(resultSet.next()) {
            return new User(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
        }
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException {
        return null;
    }
    @Override
    public boolean save(User dto2) throws SQLException {
        return SQLUtil.main( "INSERT INTO user VALUES(?,?)",dto2.getUsername(),dto2.getPassword());


    }

    @Override
    public User search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(User dto) throws SQLException {
        return false;
    }

    @Override
    public String generateNextId() throws SQLException {
        return null;
    }

    @Override
    public String splitAppId(String currentId) {
        return null;
    }
}
