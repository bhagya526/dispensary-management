package lk.ijse.bo.Custom;

import lk.ijse.Dto.PrescriptionDto;
import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Prescription;

import java.sql.SQLException;
import java.util.List;

public interface PrescriptionBO extends SuperBO {


    String generateNextPrescriptionId() throws SQLException;
     List<Prescription> LoadAllPrescription() throws SQLException;
     boolean savePrescription(PrescriptionDto dto) throws SQLException;
     boolean updatePrescription(PrescriptionDto dto) throws SQLException;
     boolean deletePrescription(String id) throws SQLException;
     Prescription searchPrescription(String id) throws SQLException;
     int getPrescriptionCount() throws SQLException;
}
