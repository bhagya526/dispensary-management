package lk.ijse.bo.Custom;

import lk.ijse.Dto.DoctorDto;
import lk.ijse.Dto.tm.DocTm;
import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Doctor;

import java.sql.SQLException;
import java.util.List;

public interface DoctorBO extends SuperBO {
    List<DoctorDto> loadAllDoctor() throws SQLException;
     String generateNextId() throws SQLException;
     boolean saveDoctor(DoctorDto dto) throws SQLException;
     boolean updateDoctor(DoctorDto dto) throws SQLException;
     boolean deleteDoctor(String id) throws SQLException;
     Doctor searchDoctor(String id) throws SQLException;
     boolean MouseClickDeleteDoctor(DocTm docTm) throws SQLException;

}
