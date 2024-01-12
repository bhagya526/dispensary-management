package lk.ijse.bo.Custom.impl;

import lk.ijse.Dto.DoctorDto;
import lk.ijse.Dto.tm.DocTm;
import lk.ijse.bo.Custom.DoctorBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.DoctorDAO;
import lk.ijse.entity.Doctor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorBOImpl implements DoctorBO {
    DoctorDAO  doctorDAO = (DoctorDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DOCTOR);
    @Override
    public List<DoctorDto> loadAllDoctor() throws SQLException {
        List<Doctor> entity = doctorDAO.getAll();
        ArrayList<DoctorDto> dto = new ArrayList<>();

        for (Doctor set : entity) {
            dto.add(new DoctorDto(set.getDoc_id(),
                    set.getName(),
                    set.getEmail(),
                    set.getContact_no())
            );
        }
        return dto;

    }
    @Override
    public String generateNextId() throws SQLException {
        return doctorDAO.generateNextId();
    }
    @Override
    public boolean saveDoctor(DoctorDto dto) throws SQLException {
        return doctorDAO.save(new Doctor(dto.getDoc_id(),dto.getName(),dto.getEmail(),dto.getContact_no()));
    }
    @Override
    public boolean updateDoctor(DoctorDto dto) throws SQLException {
        return doctorDAO.update(new Doctor(dto.getDoc_id(),dto.getName(),dto.getEmail(),dto.getContact_no()));
    }
    @Override
    public boolean deleteDoctor(String id) throws SQLException {
       return doctorDAO.delete(id);
    }
    @Override
    public Doctor searchDoctor(String id) throws SQLException {
       return doctorDAO.search(id);
    }
    @Override
   public boolean MouseClickDeleteDoctor(DocTm docTm) throws SQLException {
      return doctorDAO.delete(docTm.getDocId());
   }
}
