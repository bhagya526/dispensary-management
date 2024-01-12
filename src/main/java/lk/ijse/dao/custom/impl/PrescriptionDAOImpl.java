package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.PrescriptionDAO;
import lk.ijse.entity.Prescription;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PrescriptionDAOImpl implements PrescriptionDAO {
@Override
public boolean save(Prescription dto) throws SQLException {
    return SQLUtil .main("INSERT INTO prescription VALUES (?,?,?,?)", dto.getPres_id(), dto.getDate(),dto.getDescription(), dto.getCust_id());

    }
@Override
    public  List<Prescription> getAll() throws SQLException {

        ResultSet rst = SQLUtil.main("SELECT * FROM prescription");

        ArrayList<Prescription> list = new ArrayList<>();

        while(rst.next()){
            list.add(new Prescription(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4)));
        }

        return list;
    }
@Override
    public  String generateNextId() throws SQLException {

        ResultSet resultSet = SQLUtil.main("SELECT pres_id FROM prescription ORDER BY pres_id DESC LIMIT 1");
        if(resultSet.next()) {
            return splitAppId(resultSet.getString(1));
        }
        return splitAppId(null);
    }
@Override
    public  String splitAppId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("P0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id<10) {
                return "P00" + id;
            } else {
                return "P0" + id;
            }
        } else {
            return "P001";
        }
    }
@Override
    public Prescription search(String id) throws SQLException {

        Prescription dto = null;

        ResultSet rst = SQLUtil.main( "SELECT * FROM prescription WHERE pres_id = ?",id);

        if(rst.next()){
            dto = new Prescription(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4));
        }

        return dto;
    }
@Override
    public boolean update(Prescription dto) throws SQLException {

        return SQLUtil.main("UPDATE prescription SET date = ?, description = ?, cust_id = ? WHERE pres_id = ?", dto.getDate(), dto.getDescription(), dto.getCust_id(), dto.getPres_id());
    }
@Override
    public boolean delete(String id) throws SQLException {


        return SQLUtil .main( "DELETE FROM prescription WHERE pres_id = ?",id);

    }
    @Override
    public  int getPrescriptionCount() throws SQLException {

        ResultSet rst= SQLUtil.main("SELECT COUNT(pres_id) AS pres_count FROM prescription");
        int count = 0;
        while(rst.next()){
            count=rst.getInt("pres_count");
        }
        return count;
    }
}