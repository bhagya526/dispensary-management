package lk.ijse.bo.Custom.impl;

import lk.ijse.bo.Custom.LoginPageBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginPageBOImpl implements LoginPageBO {
    UserDAO userdao = (UserDAO)DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public ResultSet SignOnAction(String username, String password) throws SQLException {
        return userdao.checkCredentials(username, password);

    }


}
