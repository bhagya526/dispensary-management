package lk.ijse.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.Db.DbConnection;
import lk.ijse.Dto.CustomerDto;
import lk.ijse.Dto.tm.PatientTm;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.CustomerBO;
import lk.ijse.entity.Customer;
import lk.ijse.mail.Mail;
import lk.ijse.regex.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class CustomerFormController {
    public JFXTextField patId;
    public JFXTextField Email;
    public JFXTextField patName;
    public JFXTextField Address;
    public JFXTextField TelNo;
    public TableView tblPatient;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colTel;
    public TableColumn colEmail;
    public JFXTextField patSearch;


    CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMER);


    public void initialize() throws SQLException {
        setCellValueFactory();
        loadAllPatients();
        generateNextId();
    }

    private void generateNextId() {
        try {

            String appId = customerBO.generateNextId();
            patId.setText(appId);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("patId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("patName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("TelNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }

    private void loadAllPatients() throws SQLException {

        ObservableList<PatientTm> oblist = FXCollections.observableArrayList();

        try {

            List<CustomerDto> dtos = customerBO.getAllCustomers();

            for (CustomerDto dto : dtos) {
                oblist.add(new PatientTm(
                        dto.getCus_id(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getTel(),
                        dto.getEmail()));
            }
            tblPatient.setItems(oblist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveOnAction(ActionEvent actionEvent) throws SQLException {

        String id = patId.getText();
        String email = Email.getText();
        String name = patName.getText();
        String address = Address.getText();
        String telephone = TelNo.getText();


        if (Regex.getEmailPattern().matcher(Email.getText()).matches()) {
            if (Regex.getNamePattern().matcher(patName.getText()).matches()) {
                if (Regex.getMobilePattern().matcher(TelNo.getText()).matches()) {
                    if (Regex.getAddressPattern().matcher(Address.getText()).matches()) {
                        CustomerDto dto = new CustomerDto(id, name, email, address, telephone);
                        boolean isSaved = customerBO.saveCustomer(dto);

                        if (isSaved) {
                            System.out.println("Saved");
                            new Alert(Alert.AlertType.INFORMATION, "Saved").show();

                            Mail mail = new Mail();
                            mail.setMsg("Thank you for join with us..");
                            mail.setTo(email);
                            mail.setSubject("MedHealth Medical Center");

                            Thread thread = new Thread(mail);
                            thread.start();


                            clearFields();
                            initialize();

                        } else {
                            System.out.println("Not Saved");
                        }


                    } else {
                        new Alert(Alert.AlertType.WARNING, "Invalid Address").show();
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "Invalid Contact Number").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Invalid Name").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Invalid Email ").show();
        }


    }

    public void idOnAction(ActionEvent actionEvent) throws SQLException {
        String id = patId.getText();

        var dto = customerBO.searchByID(id);

        if (dto == null) {
            System.out.println("Not Found");
            new Alert(Alert.AlertType.INFORMATION, "Not Found").show();
        } else {

            new Alert(Alert.AlertType.INFORMATION, "Found").show();

            Email.setText(dto.getEmail());
            patName.setText(dto.getName());
            Address.setText(dto.getAddress());
            TelNo.setText(dto.getTel());
        }

    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        patId.clear();
        Email.clear();
        patName.clear();
        Address.clear();
        TelNo.clear();

    }

    public void updateOnAction(ActionEvent actionEvent) throws SQLException {
        String id = patId.getText();
        String email = Email.getText();
        String name = patName.getText();
        String address = Address.getText();
        String telephone = TelNo.getText();

        var dto = new CustomerDto(id, name, email, address, telephone);
        try {
            boolean isUpdated = customerBO.updateOnAction(dto);

            if (isUpdated) {
                System.out.println("Updated");
                new Alert(Alert.AlertType.INFORMATION, "Updated").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.CONFIRMATION, "Not Updated").show();

        }


    }

    public void deleteOnAction(ActionEvent actionEvent) throws SQLException {
        String id = patId.getText();


        boolean isDeleted = customerBO.deleteOnAction(id);

        if (isDeleted) {
            System.out.println("Deleted");
            new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
            clearFields();
            initialize();
        } else {
            System.out.println("Not Deleted");
            new Alert(Alert.AlertType.INFORMATION, "Not Deleted").show();
        }

    }

    public void ViewOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        InputStream resourceAsStream =
                getClass().getResourceAsStream("/reports/CustomerInfo.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport compileReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        compileReport, //compiled report
                        null,
                        DbConnection.getInstance().getConnection() //database connection
                );
        JasperViewer.viewReport(jasperPrint, false);
    }

    public void SearchOnAction(ActionEvent actionEvent) throws SQLException {
        String tel = TelNo.getText();
        try {

            Customer dto = customerBO.searchByTel(tel);
            if (dto != null) {
                patId.setText(dto.getCus_id());
                patName.setText(dto.getName());
                Email.setText(dto.getEmail());
                Address.setText(dto.getAddress());
                TelNo.setText(dto.getTel());
            } else {
                new Alert(Alert.AlertType.INFORMATION, " Customer Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}









