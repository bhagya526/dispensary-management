package lk.ijse.bo.Custom;

import lk.ijse.Dto.CustomerDto;
import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {

     String generateNextId() throws SQLException;
     List<CustomerDto> getAllCustomers() throws SQLException;
     Customer searchByID(String id) throws SQLException;
     boolean updateOnAction(CustomerDto dto) throws SQLException;
     boolean deleteOnAction(String id) throws SQLException;
     Customer searchByTel(String tel) throws SQLException;
     boolean saveCustomer(CustomerDto dto) throws SQLException;
     int getPatientsCount() throws SQLException;
}
