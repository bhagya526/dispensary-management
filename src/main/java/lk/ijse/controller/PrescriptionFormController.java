package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Dto.CustomerDto;
import lk.ijse.Dto.PrescriptionDto;
import lk.ijse.Dto.tm.PrescriptionTm;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.CustomerBO;
import lk.ijse.bo.Custom.PrescriptionBO;
import lk.ijse.entity.Prescription;

import java.sql.SQLException;
import java.util.List;

public class PrescriptionFormController {
    public JFXTextField Pres_id;
    public JFXTextField Date;
    public JFXTextField Description;
    public TableColumn ColId;
    public TableColumn ColDate;
    public TableColumn ColDescription;
    public TableColumn ColCost_id;
    public TableView TblPrescription;
    public JFXComboBox cmbcusId;
    PrescriptionBO prescriptionBO= (PrescriptionBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PRESCRIPTION);
    CustomerBO customerBO= (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() {
        setCellValueFactory();
        loadAllPrescription();
        loadallcustomer();
        generateNextId();
    }

    private void loadallcustomer() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {

            List<CustomerDto> idList = customerBO.getAllCustomers();

            for (CustomerDto dto : idList) {
                obList.add(dto.getCus_id());
            }

            cmbcusId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextId() {
        try {
            String appId = prescriptionBO.generateNextPrescriptionId();
            Pres_id.setText(appId);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void loadAllPrescription() {
        ObservableList<PrescriptionTm> oblist = FXCollections.observableArrayList();

        try {
            List<Prescription> dtos = prescriptionBO.LoadAllPrescription();

            for (Prescription dto : dtos) {
                oblist.add(new PrescriptionTm(
                        dto.getPres_id(),
                        dto.getDate(),
                        dto.getDescription(),
                        dto.getCust_id()));
            }
            TblPrescription.setItems(oblist);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    private void setCellValueFactory() {
        ColId.setCellValueFactory(new PropertyValueFactory<>("Pres_id"));
        ColDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        ColDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        ColCost_id.setCellValueFactory(new PropertyValueFactory<>("Cust_id"));


    }

    public void saveOnAction(ActionEvent actionEvent) throws SQLException {
        String id = Pres_id.getText();
        String date = Date.getText();
        String description = Description.getText();
        String cust_id = (String) cmbcusId.getValue();

            boolean dto = prescriptionBO.savePrescription(new PrescriptionDto(id,date,description,cust_id));

        if (dto) {
            clearFields();
            initialize();
            new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException {
        String id = Pres_id.getText();
        String date = Date.getText();
        String description = Description.getText();
        String cust_id = (String) cmbcusId.getValue();

        PrescriptionDto dto = new PrescriptionDto(id, date, description, cust_id);
        boolean isUpdated = prescriptionBO.updatePrescription(dto);
        if(isUpdated){
            System.out.println("Updated");
            clearFields();
            initialize();
            new Alert(Alert.AlertType.CONFIRMATION,"Updated").show();
        }else{
            System.out.println("Not Updated");
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }

    }


    public void DeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String id = Pres_id.getText();
        boolean isdeleted=prescriptionBO.deletePrescription(id);
        if(isdeleted){
            System.out.println("Deleted");
            clearFields();
            initialize();
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
        }else {
            System.out.println("Not Deleted");
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }

    }

    public void ClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        Pres_id.clear();
        Date.clear();
        Description.clear();
        cmbcusId.getItems().clear();
    }

    public void IdOnAction(ActionEvent actionEvent) throws SQLException {
        String id = Pres_id.getText();

        var dto = prescriptionBO.searchPrescription(id);

        if(dto == null){
            System.out.println("Not Found");
            new Alert(Alert.AlertType.INFORMATION, "Not Found").show();
        } else {

            new Alert(Alert.AlertType.INFORMATION, "Found").show();

            Date.setText(dto.getDate());
            Description.setText(dto.getDescription());
            cmbcusId.setValue(dto.getCust_id());

        }

    }

    public void SearchOnAction(ActionEvent actionEvent) throws RuntimeException {
        String id = Pres_id.getText();
        try{
            Prescription prescriptionDto= prescriptionBO.searchPrescription(id);
            if(prescriptionDto==null){
                new Alert(Alert.AlertType.INFORMATION,"Not Found").show();
            }else{
                new Alert(Alert.AlertType.INFORMATION,"Found").show();
                Date.setText(prescriptionDto.getDate());
                Description.setText(prescriptionDto.getDescription());
                cmbcusId.setValue(prescriptionDto.getCust_id());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

