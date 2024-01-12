package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.Dto.MedicineDto;
import lk.ijse.Dto.tm.MedicineTm;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.MedicineBO;
import lk.ijse.entity.Medicine;
import lk.ijse.regex.Regex;

import java.sql.SQLException;
import java.util.List;

public class MedicineFormController {
    public JFXTextField MedicineId;
    public JFXTextField Name;
    public JFXTextField Type;
    public JFXTextField Quantity;
    public JFXTextField Price;
    public TableView TblMedicine;
    public TableColumn ColId;
    public TableColumn ColName;
    public TableColumn ColQuantity;
    public TableColumn ColPrice;
    public TableColumn ColType;
    public Button btnSearch;
    MedicineBO medicineBO = (MedicineBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.MEDICINE);

    public void initialize() {
        setCellValueFactory();
        loadAllMedicine();
        generateNextId();

    }

    private void generateNextId() {
        try {
            String appId = medicineBO.generateNextMedicineId();
            MedicineId.setText(appId);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadAllMedicine() {
        ObservableList<MedicineTm> oblist = FXCollections.observableArrayList();
        try {
            List<MedicineDto> dtos = medicineBO.getAllMedicine();

            for (MedicineDto dto : dtos) {
                oblist.add(new MedicineTm(
                        dto.getMedicine_id(),
                        dto.getName(),
                        dto.getType(),
                        dto.getQty(),
                        dto.getPrice()));
            }
            TblMedicine.setItems(oblist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        ColId.setCellValueFactory(new PropertyValueFactory<>("medId"));
        ColName.setCellValueFactory(new PropertyValueFactory<>("medName"));
        ColType.setCellValueFactory(new PropertyValueFactory<>("medType"));
        ColQuantity.setCellValueFactory(new PropertyValueFactory<>("medQty"));
        ColPrice.setCellValueFactory(new PropertyValueFactory<>("medPrice"));

    }

    public void SaveOnAction(ActionEvent actionEvent) throws SQLException {

        String id = MedicineId.getText();
        String name = Name.getText();
        String type = Type.getText();
        String qty = Quantity.getText();
        String price = Price.getText();

        if (Regex.getIntPattern().matcher(Quantity.getText()).matches()) {
            if (Regex.getNamePattern().matcher(Name.getText()).matches()) {
                if (Regex.getDoublePattern().matcher(Price.getText()).matches()) {

                    MedicineDto dto = new MedicineDto(id, name, type, qty, price);
                    boolean isSaved = medicineBO.saveMedicine(dto);

                    if (isSaved) {

                        new Alert(Alert.AlertType.INFORMATION, "Saved").show();
                        clearFields();
                        initialize();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again").show();
                    }

                } else {
                    new Alert(Alert.AlertType.WARNING, "Invalid Email").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Invalid Name").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Invalid Number").show();
        }


    }

    public void UpdateOnAction(ActionEvent actionEvent) throws SQLException {
        String id = MedicineId.getText();
        String name = Name.getText();
        String type = Type.getText();
        String qty = Quantity.getText();
        String price = Price.getText();

        if (Regex.getIntPattern().matcher(Quantity.getText()).matches()) {
            if (Regex.getNamePattern().matcher(Name.getText()).matches()) {
                if (Regex.getDoublePattern().matcher(Price.getText()).matches()) {

                    MedicineDto dto = new MedicineDto(id, name, type, qty, price);

                    boolean isUpdated = medicineBO.updateMedicine(dto);
                    if (isUpdated) {
                        System.out.println("Updated");
                        new Alert(Alert.AlertType.CONFIRMATION, "Updated").show();
                        clearFields();
                        initialize();
                    } else {
                        System.out.println("Not Updated");
                        new Alert(Alert.AlertType.INFORMATION, "Updated").show();
                    }

                } else {
                    new Alert(Alert.AlertType.WARNING, "Invalid Email").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Invalid Name").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Invalid Contact Number").show();
        }


    }

    public void DeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String id = MedicineId.getText();
        boolean isDeleted = medicineBO.deleteMedicine(id);
        if (isDeleted) {
            System.out.println("Deleted");
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            clearFields();
            initialize();
        } else {
            System.out.println("Not Deleted");
            new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
        }
    }


    public void ClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        MedicineId.clear();
        Name.clear();
        Type.clear();
        Quantity.clear();
        Price.clear();
    }


    public void idOInAction(ActionEvent actionEvent) throws SQLException {
        String id = MedicineId.getText();

        Medicine dto = medicineBO.searchMedicine(id);

        if (dto == null) {
            System.out.println("Not Found");
            new Alert(Alert.AlertType.INFORMATION, "Not Found").show();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Found").show();

            Name.setText(dto.getName());
            Type.setText(dto.getType());
            Quantity.setText(dto.getQty());
            Price.setText(dto.getPrice());

        }


    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException {

        String id = MedicineId.getText();
        try {
            Medicine dto = medicineBO.searchMedicine(id);
            if (dto != null) {
                Name.setText(dto.getName());
                Type.setText(dto.getType());
                Quantity.setText(dto.getQty());
                Price.setText(dto.getPrice());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Not Found").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void TblDoctorOnAction(MouseEvent mouseEvent) {
    }

    public void MouseClickedDeleteOnAction(MouseEvent mouseEvent) {
    }
}



