package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Order;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order> {
     String generateNextId() throws SQLException;

     String splitOrderId(String currentorderId);
}

