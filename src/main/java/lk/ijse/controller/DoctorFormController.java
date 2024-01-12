package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.Dto.DoctorDto;
import lk.ijse.Dto.tm.DocTm;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.DoctorBO;
import lk.ijse.entity.Doctor;
import lk.ijse.regex.Regex;

import java.sql.SQLException;
import java.util.List;

public class DoctorFormController {
        public JFXTextField docId;
        public JFXTextField Name;
        public JFXTextField Email;
        public JFXTextField ContactNo;
        public TableView tbldoc;
        public TableColumn<?, ?> colId;
        public TableColumn<?, ?> colName;
        public TableColumn<?, ?> colMail;
        public TableColumn<?, ?> colTel;
        public Button txtSearch;
        DoctorBO doctorBO = (DoctorBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.DOCTOR);

        String contact;

        public void initialize() throws SQLException {
            generateNextId();
            setCellValueFactory();
            loadAllDoctors();


        }

        private void loadAllDoctors() {
            System.out.println("Load All");
        ObservableList<DocTm> oblist = FXCollections.observableArrayList();
        try {

            List<DoctorDto> dtos = doctorBO.loadAllDoctor();

            for (DoctorDto dto : dtos) {
                oblist.add(new DocTm(
                        dto.getDoc_id(),
                        dto.getName(),
                        dto.getEmail(),
                        dto.getContact_no()));
            }
            tbldoc.setItems(oblist);
            System.out.println(oblist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("docId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private void generateNextId() {
        try {

            String appId = doctorBO.generateNextId();
            docId.setText(appId);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void SaveOnAction(ActionEvent actionEvent) throws SQLException {
        String id = docId.getText();
        String name = Name.getText();
        String email = Email.getText();
        String Con_no = ContactNo.getText();

        if (Regex.getMobilePattern().matcher(ContactNo.getText()).matches()) {
            if (Regex.getNamePattern().matcher(Name.getText()).matches()) {
                if (Regex.getEmailPattern().matcher(Email.getText()).matches()) {

                    Integer Con_no1 = Integer.parseInt(Con_no);

                    boolean dto = doctorBO.saveDoctor(new DoctorDto(id, name, email, Con_no1));
                    System.out.println(dto);
                    if (dto) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Saved").show();
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
            new Alert(Alert.AlertType.WARNING, "Invalid Contact Number").show();
        }

    }

    public void UpdateOnAction(ActionEvent actionEvent) throws SQLException {

        String id = docId.getText();
        String name = Name.getText();
        String email = Email.getText();
        String contact = ContactNo.getText();
        Integer Con_no = Integer.parseInt(contact);

        boolean doc = doctorBO.updateDoctor(new DoctorDto(id, name, email,Con_no));
        if (Regex.getMobilePattern().matcher(ContactNo.getText()).matches()) {
            if (Regex.getNamePattern().matcher(Name.getText()).matches()) {
                if (Regex.getEmailPattern().matcher(Email.getText()).matches()) {
                    boolean isUpdated = doctorBO.updateDoctor(new DoctorDto(id, name, email,Con_no));
                    if (isUpdated) {
                        System.out.println("Updated");
                        new Alert(Alert.AlertType.CONFIRMATION, "Updated").show();
                        clearFields();
                        initialize();
                    } else {
                        System.out.println("not Updated");
                        new Alert(Alert.AlertType.WARNING, "Try Again").show();
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
        String id = docId.getText();
        boolean isDeleted = doctorBO.deleteDoctor(id);
        if (isDeleted) {
            System.out.println("Deleted");
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            clearFields();
            initialize();
        } else {
            System.out.println("Not Deleted");
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
            clearFields();
        }
    }

    public void ClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        docId.clear();
        Name.clear();
        Email.clear();
        ContactNo.clear();
    }

    public void DoctorOnAction(ActionEvent actionEvent) throws SQLException {
        String id = docId.getText();

        Doctor dto = doctorBO.searchDoctor(id);

        if (dto == null) {
            System.out.println("Not Found");
            new Alert(Alert.AlertType.INFORMATION, "Not Found").show();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Found").show();

String contact = String.valueOf(dto.getContact_no());

            Name.setText(dto.getName());
            Email.setText(dto.getEmail());
            ContactNo.setText(contact);

        }
    }

    public void BtnSearchOnAction(ActionEvent actionEvent) throws SQLException {
        String id = docId.getText();
        try {
            Doctor dto = doctorBO.searchDoctor(id);

            if (dto!= null) {
                Name.setText(dto.getName());
                Email.setText(dto.getEmail());
                ContactNo.setText(contact);

            } else {
                new Alert(Alert.AlertType.INFORMATION, "Not Found").show();
            }
        }catch (RuntimeException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void tblDocOnMouseClicked(MouseEvent mouseEvent) throws SQLException {
        DocTm selectedDoc = (DocTm) tbldoc.getSelectionModel().getSelectedItem();

        if (selectedDoc != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete selected Doc?");
            alert.showAndWait();

            ButtonType result = alert.getResult();
            if (result == ButtonType.OK) {
                try {
                    boolean isDeleted = doctorBO.MouseClickDeleteDoctor(selectedDoc);
                    if (isDeleted) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
                        clearFields();
                        initialize();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try Again").show();
                    }
                } catch (RuntimeException e) {
                    new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
                }

            }
        }

        Integer index= tbldoc.getSelectionModel().getSelectedIndex();
        if (index <= -1){
            return;
        }
        docId.setText(colId.getCellData(index).toString());
        Name.setText(colName.getCellData(index).toString());
        Email.setText(colMail.getCellData(index).toString());
        ContactNo.setText(colTel.getCellData(index).toString());

    }

}
