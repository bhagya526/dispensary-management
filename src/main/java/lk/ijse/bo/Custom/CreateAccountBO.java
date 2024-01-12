package lk.ijse.bo.Custom;

import lk.ijse.Dto.UserDto;
import lk.ijse.bo.SuperBO;
import lk.ijse.entity.User;

import java.sql.SQLException;

public interface CreateAccountBO extends SuperBO {

    User searchByEmail(String email) throws SQLException;
     boolean saveCredentials(UserDto dto2) throws SQLException;
}
