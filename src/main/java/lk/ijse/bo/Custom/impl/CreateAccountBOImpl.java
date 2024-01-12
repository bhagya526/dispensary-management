package lk.ijse.bo.Custom.impl;

import lk.ijse.Dto.UserDto;
import lk.ijse.bo.Custom.CreateAccountBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.entity.User;

import java.sql.SQLException;

public class CreateAccountBOImpl implements CreateAccountBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public User searchByEmail(String email) throws SQLException {
        return userDAO.searchByEmail(email);
    }
    @Override
    public boolean saveCredentials(UserDto dto) throws SQLException {
        return userDAO.save(new User(dto.getEmail(),dto.getPassword()));
    }


}
