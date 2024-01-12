package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Customer;

import java.sql.SQLException;

public interface CustomerDAO extends CrudDAO<Customer> {
     Customer searchPatientByTel(String tel) throws SQLException;
     int getCustomerCount() throws SQLException;
     public boolean save(Customer customer) throws SQLException;



}
