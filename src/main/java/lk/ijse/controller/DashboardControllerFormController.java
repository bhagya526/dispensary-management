package lk.ijse.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.CustomerBO;
import lk.ijse.bo.Custom.MedicineBO;
import lk.ijse.bo.Custom.PrescriptionBO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardControllerFormController {
    public AnchorPane changePain;
    public AnchorPane root;
    public Label lblTime;
    public Label txtMedicineCount;
    public Label txtPatientsCount;
    public Label txtprescripcount;


    MedicineBO medicineBO= (MedicineBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.MEDICINE);
    PrescriptionBO prescriptionBO= (PrescriptionBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PRESCRIPTION);

    CustomerBO customerBO= (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMER);



    public void initialize() throws SQLException {
        SetDateAndTime();
        SetMedicineCount();
        SetPatientsCount();
        SetPrescripCount();
    }

    private void SetDateAndTime() {
        Timeline time = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    lblTime.setText(LocalDateTime.now().format(formatter));
                }), new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();


    }


    public void patientOnAction(ActionEvent actionEvent) throws IOException {

        URL resourse = getClass().getResource("/view/patient_form.fxml");
        assert resourse != null;
        Parent load = FXMLLoader.load(resourse);
        changePain.getChildren().clear();
        changePain.getChildren().add(load);
    }

    public void medicineOnAction(ActionEvent actionEvent) throws IOException {
        URL resourse = getClass().getResource("/view/MedicineForm.fxml");
        assert resourse != null;
        Parent load = FXMLLoader.load(resourse);
        changePain.getChildren().clear();
        changePain.getChildren().add(load);
    }

    public void signoutOnAction(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(this.getClass().getResource("/view/LoginPageForm.fxml"));
        Scene scene = new Scene(parent);

        root.getChildren().clear();
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(scene);

    }

    public void prescriptionOnaction(ActionEvent actionEvent) throws IOException {
        URL resourse = getClass().getResource("/view/PrescriptionForm.fxml");
        assert resourse != null;
        Parent load = FXMLLoader.load(resourse);
        changePain.getChildren().clear();
        changePain.getChildren().add(load);
    }

    public void doctorOnAction(ActionEvent actionEvent) throws IOException {
        URL resourse = getClass().getResource("/view/DoctorForm.fxml");
        assert resourse != null;
        Parent load = FXMLLoader.load(resourse);
        changePain.getChildren().clear();
        changePain.getChildren().add(load);
    }

    public void OrdersOnAction(ActionEvent actionEvent) throws IOException {
        URL resourse = getClass().getResource("/view/OrderForm.fxml");
        assert resourse != null;
        Parent load = FXMLLoader.load(resourse);
        changePain.getChildren().clear();
        changePain.getChildren().add(load);
    }

    public void SetMedicineCount() throws SQLException {

        int count = medicineBO.getMedicineCount();
        Integer medCount = count;
        txtMedicineCount.setText(String.valueOf(count));


    }

    public void SetPatientsCount() throws SQLException {

        int count= customerBO.getPatientsCount();
        Integer cusCount = count;
        txtPatientsCount.setText(String.valueOf(count));
    }
    public void SetPrescripCount() throws SQLException {

        int count = prescriptionBO.getPrescriptionCount();
        Integer cusCount = count;
        txtprescripcount.setText(String.valueOf(count));
    }



}
