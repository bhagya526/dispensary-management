package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.DoctorDAO;
import lk.ijse.entity.Doctor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAOImpl implements DoctorDAO {
    @Override
    public String generateNextId() throws SQLException {

        SQLUtil.main("SELECT doc_id FROM doctor ORDER BY doc_id DESC LIMIT 1");
        ResultSet resultSet = SQLUtil.main("SELECT doc_id FROM doctor ORDER BY doc_id DESC LIMIT 1");

        if (resultSet.next()) {
            return splitAppId(resultSet.getString(1));
        }
        return splitAppId(null);
    }


    @Override
    public String splitAppId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("D0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            if (id < 10) {
                return "D00" + id;
            } else {
                return "D0" + id;
            }
        } else {
            return "D001";
        }
    }
     @Override
    public  List<Doctor> getAll() throws SQLException {

        ResultSet rst = SQLUtil.main("SELECT * FROM doctor");

        ArrayList<Doctor> list = new ArrayList<>();

        while (rst.next()) {
            Doctor dto = new Doctor(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4));
            list.add(dto);
        }
        return list;
    }
    @Override
    public boolean save(Doctor dto) throws SQLException {
        return SQLUtil .main("INSERT INTO doctor Values(?,?,?,?)",dto.getDoc_id(),dto.getName(),dto.getEmail(),dto.getContact_no());

    }
    @Override
    public boolean update(Doctor docdto) throws SQLException {
        return SQLUtil .main("UPDATE doctor SET name=?,email=?,contact_no=? WHERE doc_id=?",docdto.getName(),docdto.getEmail(),docdto.getContact_no(),docdto.getDoc_id());
    }

    @Override
    public boolean delete(String id) throws SQLException {

        return SQLUtil .main("DELETE FROM doctor WHERE doc_id=?",id) ;

    }
    @Override
    public Doctor search(String id) throws SQLException {
        ResultSet rst = SQLUtil.main("SELECT * FROM doctor WHERE doc_id = ?",id);
        Doctor dto = null;
        if (rst.next()) {
            dto = new Doctor(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4));
        }
        return dto;
    }

}
