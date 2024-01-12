package lk.ijse.bo.Custom;

import lk.ijse.Dto.MedicineDto;
import lk.ijse.bo.SuperBO;
import lk.ijse.entity.Medicine;

import java.sql.SQLException;
import java.util.List;

public interface MedicineBO extends SuperBO {

    String generateNextMedicineId() throws SQLException;
     List<MedicineDto> getAllMedicine() throws SQLException;
    boolean saveMedicine(MedicineDto dto) throws SQLException;
     boolean updateMedicine(MedicineDto dto) throws SQLException;
     boolean deleteMedicine(String id) throws SQLException;
     Medicine searchMedicine(String id) throws SQLException;
     int getMedicineCount() throws SQLException;

}
