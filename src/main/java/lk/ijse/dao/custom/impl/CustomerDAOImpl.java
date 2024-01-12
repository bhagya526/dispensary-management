package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
   @Override
    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.main("SELECT cus_id FROM customer ORDER BY cus_id DESC LIMIT 1");
        if (resultSet.next()) {
            return splitAppId(resultSet.getString(1));
        }
        return splitAppId(null);
    }

    @Override
    public String splitAppId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("S0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if (id < 10) {
                return "S00" + id;
            } else {
                return "S0" + id;
            }
        } else {
            return "S001";
        }
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        ResultSet rst = SQLUtil.main("SELECT * FROM customer");

        ArrayList<Customer> list = new ArrayList<>();

        while (rst.next()) {
            list.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)));
        }
        return list;
    }

/*    @Override
    public boolean save(Customer customer) throws SQLException {
        return false;
    }*/


    @Override
    public boolean save(Customer customer) throws SQLException {
        return SQLUtil.main("INSERT INTO customer VALUES (?,?,?,?,?)", customer.getCus_id(), customer.getName(), customer.getEmail(), customer.getAddress(), customer.getTel());
    }
    @Override
    public Customer search(String id) throws SQLException {
        Customer dto = null;

        ResultSet rst = SQLUtil.main("SELECT * FROM customer WHERE cus_id = ?", id);
        if (rst.next()) {
            dto = new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5));
        }

        return dto;


    }
    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.main("DELETE FROM customer WHERE cus_id = ?", id);
    }
    @Override
    public boolean update(Customer dto) throws SQLException {
        return SQLUtil.main("UPDATE customer SET name = ?, email = ?, address = ?, tel = ? WHERE cus_id = ?", dto.getName(), dto.getEmail(), dto.getAddress(), dto.getTel(), dto.getCus_id());
   }

    @Override
    public Customer searchPatientByTel(String tel) throws SQLException {

        ResultSet rst= SQLUtil.main("SELECT * FROM customer WHERE tel = ?",tel);
        Customer dto=null;
        if(rst.next()){
            dto=new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5));
        }
        return dto;
    }
    @Override
    public int getCustomerCount() throws SQLException {
        ResultSet rst=SQLUtil.main("SELECT COUNT(cus_id) AS cus_count FROM customer");
        int count = 0;
        while(rst.next()){
            count=rst.getInt("cus_count");
        }
        return count;
    }
}
