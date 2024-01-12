package lk.ijse.dao.custom;

import lk.ijse.Dto.tm.CartTm;
import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Medicine;

import java.sql.SQLException;
import java.util.List;

public interface MedicineDAO extends CrudDAO<Medicine> {


     boolean updateQty(String medId, String medQty) throws SQLException;

     int getMedicineCount() throws SQLException;

      boolean update(List<CartTm> cartTmList) throws SQLException;
}
