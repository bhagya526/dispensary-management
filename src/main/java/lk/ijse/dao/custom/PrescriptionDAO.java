package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Prescription;

import java.sql.SQLException;


public interface PrescriptionDAO extends CrudDAO<Prescription> {
    int getPrescriptionCount() throws SQLException;
}
