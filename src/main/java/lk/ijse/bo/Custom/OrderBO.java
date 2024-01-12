package lk.ijse.bo.Custom;

import lk.ijse.Dto.PlaceOrderDto;
import lk.ijse.bo.SuperBO;

import java.sql.SQLException;

public interface OrderBO extends SuperBO {

    String generateNextOrderId() throws SQLException;
    boolean placeOrder(PlaceOrderDto dto) throws SQLException;
}
