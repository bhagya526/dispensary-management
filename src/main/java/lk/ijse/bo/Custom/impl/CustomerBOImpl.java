package lk.ijse.bo.Custom.impl;

import lk.ijse.Dto.CustomerDto;
import lk.ijse.bo.Custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public String generateNextId() throws SQLException {
       return customerDAO.generateNextId();
    }
    @Override
    public List <CustomerDto> getAllCustomers() throws SQLException {
        List<Customer> entity = customerDAO.getAll();
        ArrayList<CustomerDto> dto = new ArrayList<>();

        for(Customer set:entity){
            dto.add(new CustomerDto(set.getCus_id(),set.getName(),set.getEmail(),set.getAddress(),set.getTel()));
        }
        return dto;

    }
    @Override
    public Customer searchByID(String id) throws SQLException {
        return customerDAO.search(id);
    }
    @Override
    public boolean updateOnAction(CustomerDto dto) throws SQLException {
        return customerDAO.update(new Customer(dto.getCus_id(),dto.getName(),dto.getEmail(),dto.getAddress(),dto.getTel()));
    }
    @Override
    public boolean deleteOnAction(String id) throws SQLException {
        return customerDAO.delete(id);
    }
    @Override
    public Customer searchByTel(String tel) throws SQLException {
        return customerDAO.searchPatientByTel(tel);
    }
    @Override
    public boolean saveCustomer(CustomerDto customer) throws SQLException {
        return customerDAO.save(new Customer(customer.getCus_id(),customer.getName(),customer.getEmail(),customer.getAddress(),customer.getTel()));
    }
    @Override
    public int getPatientsCount() throws SQLException {
        return customerDAO.getCustomerCount();
    }



}
