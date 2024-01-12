package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public  String generateNextId() throws SQLException {

        ResultSet resultSet = SQLUtil.main("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1") ;
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    @Override
    public String splitAppId(String currentId) {
        return null;
    }

    public  String splitOrderId(String currentorderId) {
        if(currentorderId != null) {
            String[] split = currentorderId.split("O0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "O00" + id;
        } else {
            return "O001";
        }
    }



    @Override
    public List<Order> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(Order order) throws SQLException {
        return SQLUtil .main("INSERT INTO orders VALUES(?, ?, ?, ?)", order.getOrder_id(),order.getDate(),order.getPres_id(),order.getTotal_price());

    }

    @Override
    public Order search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Order dto) throws SQLException {
        return false;
    }
}
