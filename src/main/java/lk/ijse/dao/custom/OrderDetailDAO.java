package lk.ijse.dao.custom;

import lk.ijse.Dto.tm.CartTm;
import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetails>{
   boolean save(OrderDetails orderDetails,List<CartTm> cartTmList) throws SQLException;
}