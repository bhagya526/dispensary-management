package lk.ijse.bo.Custom.impl;


import lk.ijse.Dto.PrescriptionDto;
import lk.ijse.bo.Custom.PrescriptionBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PrescriptionDAO;
import lk.ijse.entity.Prescription;

import java.sql.SQLException;
import java.util.List;

public class PrescriptionBOImpl implements PrescriptionBO {
    PrescriptionDAO prescriptionDAO = (PrescriptionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRESCRIPTION);

    @Override
    public String generateNextPrescriptionId() throws SQLException {
        return prescriptionDAO.generateNextId();
    }
   @Override
    public List<Prescription> LoadAllPrescription() throws SQLException {
        return prescriptionDAO.getAll();
    }
    @Override
    public boolean savePrescription(PrescriptionDto dto) throws SQLException {
        return prescriptionDAO.save(new Prescription(dto.getPres_id(),dto.getDate(),dto.getPres_id(),dto.getCust_id()));
    }
    @Override
    public boolean updatePrescription(PrescriptionDto dto) throws SQLException {
        return prescriptionDAO.update(new Prescription(dto.getPres_id(),dto.getDate(),dto.getPres_id(),dto.getCust_id()));
    }
    @Override
    public boolean deletePrescription(String id) throws SQLException {
        return prescriptionDAO.delete(id);
    }
    @Override
    public Prescription searchPrescription(String id) throws SQLException {
        return prescriptionDAO.search(id);
    }
   @Override
    public int getPrescriptionCount() throws SQLException {
        return prescriptionDAO.getPrescriptionCount();
    }

}
