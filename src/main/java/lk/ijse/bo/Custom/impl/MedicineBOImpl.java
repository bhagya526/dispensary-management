package lk.ijse.bo.Custom.impl;

import lk.ijse.Dto.MedicineDto;
import lk.ijse.bo.Custom.MedicineBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.MedicineDAO;
import lk.ijse.entity.Medicine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineBOImpl implements MedicineBO {
    MedicineDAO medicineDAO = (MedicineDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.MEDICINE);
    @Override
    public String generateNextMedicineId() throws SQLException {
       return medicineDAO.generateNextId();
    }
   @Override
    public List<MedicineDto> getAllMedicine() throws SQLException {
       List<Medicine> list=medicineDAO.getAll();
       ArrayList<MedicineDto> dtoList = new ArrayList<>();

       for(Medicine set:list){
           dtoList.add(new MedicineDto(set.getMedicine_id(),set.getName(),set.getType(),set.getQty(),set.getPrice()));
       }
        return dtoList;
    }
   @Override
    public boolean saveMedicine(MedicineDto dto) throws SQLException {
       return medicineDAO.save(new Medicine(dto.getMedicine_id(),dto.getName(),dto.getType(),dto.getQty(),dto.getPrice()));
   }

   @Override
   public boolean updateMedicine(MedicineDto dto) throws SQLException {
       return medicineDAO.update(new Medicine(dto.getMedicine_id(),dto.getName(),dto.getType(),dto.getQty(),dto.getPrice()));
   }
   @Override
   public boolean deleteMedicine(String id) throws SQLException {
       return medicineDAO.delete(id);
   }
   @Override
   public Medicine searchMedicine(String id) throws SQLException {
       return medicineDAO.search(id);
   }
   @Override
   public int getMedicineCount() throws SQLException {
       return medicineDAO.getMedicineCount();
   }
}
