package lk.ijse.dao.custom.impl;

import lk.ijse.Dto.OrderDetailsDto;
import lk.ijse.Dto.tm.CartTm;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public List<OrderDetails> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(OrderDetails dto) throws SQLException {
        return false;
    }
    public  boolean save(OrderDetails orderDetails,List<CartTm> cartTmList) throws SQLException {
        for (CartTm cartTm : cartTmList) {
            return SQLUtil.main("INSERT INTO order_details VALUES (?,?,?,?,?)", orderDetails.getOrder_id(), orderDetails.getMedicine_id(), orderDetails.getQty(), orderDetails.getUnit_price(), cartTm.getTotal());
        }
        return true;
    }

    @Override
    public OrderDetails search(String id) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean update(OrderDetails dto) throws SQLException {
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
