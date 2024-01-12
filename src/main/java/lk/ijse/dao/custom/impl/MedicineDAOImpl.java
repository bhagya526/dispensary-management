package lk.ijse.dao.custom.impl;

import lk.ijse.Dto.MedicineDto;
import lk.ijse.Dto.tm.CartTm;
import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.MedicineDAO;
import lk.ijse.entity.Medicine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAOImpl implements MedicineDAO {
    private MedicineDto dto;
    private Object MedicineDto;
    @Override
    public  List<Medicine> getAll() throws SQLException {

        ResultSet rst = SQLUtil.main("SELECT * FROM medicine");

        ArrayList<Medicine> list = new ArrayList<>();

        while (rst.next()) {
            list.add(new Medicine(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)));
        }
        return list;
    }


    @Override
    public boolean update(List<CartTm> cartTmList) throws SQLException {
        for (CartTm tm : cartTmList) {

            if (!updateQty(tm.getMedId(), tm.getMedQty())) {
                return false;
            }

        }
        return true;

    }
    @Override
    public boolean updateQty(String medId, String medQty) throws SQLException {
        return SQLUtil .main("UPDATE medicine SET quantity = quantity - ? WHERE medicine_id = ?", medQty, medId);

    }

    @Override
    public String generateNextId() throws SQLException {

        ResultSet resultSet = SQLUtil.main("SELECT medicine_id FROM medicine ORDER BY medicine_id DESC LIMIT 1");
        if (resultSet.next()) {
            return splitAppId(resultSet.getString(1));
        }
        return splitAppId(null);
    }
    @Override
    public String splitAppId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("M0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if (id < 10) {
                return "M00" + id;
            } else {
                return "M0" + id;
            }
        } else {
            return "M001";
        }
    }

   @Override
    public boolean save(Medicine dto) throws SQLException {
        return SQLUtil.main("INSERT INTO medicine VALUES (?,?,?,?,?)",dto.getMedicine_id(),dto.getName(),dto.getType(),dto.getQty(),dto.getPrice());
    }
   @Override
    public  Medicine search(String id) throws SQLException {

        Medicine dto = null;
        ResultSet rst = SQLUtil.main("SELECT * FROM medicine WHERE medicine_id = ?",id);
        if (rst.next()) {
            dto = new Medicine(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5));
        }
        return dto;
    }
   @Override
    public  boolean delete(String id) throws SQLException {
        try {
            boolean isDeleted = SQLUtil.main("DELETE FROM medicine WHERE medicine_id = ?",id);
            return isDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Medicine dto) throws SQLException {
        return false;
    }

    @Override
    public int getMedicineCount() throws SQLException {

        ResultSet rst =  SQLUtil.main("SELECT COUNT(medicine_id) AS medicine_count FROM medicine");
        int count = 0;
        while (rst.next()) {
            count = rst.getInt("medicine_count");

        }
        return count;

    }
}


