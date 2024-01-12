package lk.ijse.bo.Custom;

import lk.ijse.bo.SuperBO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface LoginPageBO extends SuperBO {

    ResultSet SignOnAction(String username, String password) throws SQLException;
}
