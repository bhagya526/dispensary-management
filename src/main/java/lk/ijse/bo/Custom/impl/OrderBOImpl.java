package lk.ijse.bo.Custom.impl;

import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.PlaceOrderDto;
import lk.ijse.Dto.tm.CartTm;
import lk.ijse.bo.Custom.OrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.MedicineDAO;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.entity.Order;
import lk.ijse.entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderBOImpl implements OrderBO {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    MedicineDAO medicineDAO =(MedicineDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MEDICINE);
    OrderDetailDAO orderdetailDAO =(OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);
    @Override
    public String generateNextOrderId() throws SQLException {
        return orderDAO.generateNextId();
    }
    @Override
    public boolean placeOrder(PlaceOrderDto dto) throws SQLException {

     Connection connection = null;
     connection = DbConnection.getInstance().getConnection();

            connection.setAutoCommit(false);



     boolean isOrderSaved = orderDAO.save(new Order(dto.getOrderId(),dto.getDate(),dto.getPres_Id(),dto.getTotal_price()));

            if(isOrderSaved) {
                System.out.println("1 "+isOrderSaved);

                boolean isUpdated = medicineDAO.update(dto.getCartTmList());
                System.out.println("2 "+isUpdated);

                if(isUpdated) {
                    boolean isOrderDetailSaved = false;

                for (CartTm tm: dto.getCartTmList()) {
                        isOrderDetailSaved = orderdetailDAO.save(new OrderDetails(dto.getOrderId(),
                                dto.getMedID(),tm.getMedQty(),tm.getMedPrice(),tm.getTotal()),dto.getCartTmList());
                    }
                System.out.println("3 "+isOrderDetailSaved);

                if(isOrderDetailSaved) {
                        connection.commit();
                        return true;
                } else {
                        connection.rollback();
                    connection.setAutoCommit(true);
                    }
                } else {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
                } else {
                connection.rollback();
                connection.setAutoCommit(true);
            }
        return false;
    }
}
