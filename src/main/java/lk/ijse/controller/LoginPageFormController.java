package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.LoginPageBO;
import lk.ijse.mail.Mail;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageFormController {
    public AnchorPane root;
    public TextField txtusername;
    public TextField txtpass;
    LoginPageBO loginPageBO = (LoginPageBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.LOGIN);

    public void createOnAction(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(this.getClass().getResource("/view/CreateAccountForm.fxml"));
        Scene scene = new Scene(parent);

        root.getChildren().clear();
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(scene);

    }

    public void signOnAction(ActionEvent actionEvent) throws IOException, SQLException {


        String username = txtusername.getText();
        String password = txtpass.getText();

        ResultSet check= loginPageBO.SignOnAction(username,password);
        if(username.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "username required..!!", ButtonType.OK).show();
        }else if (password.isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "password required..!!", ButtonType.OK).show();
        }
        if (check.next()){

            Parent parent = FXMLLoader.load(this.getClass().getResource("/view/dashboardController_form.fxml"));
            Scene scene = new Scene(parent);

            root.getChildren().clear();
            Stage stage = (Stage) root.getScene().getWindow();

            stage.setScene(scene);

            Mail mail = new Mail();
            mail.setMsg("Sucessfully login");
            mail.setTo(username);
            mail.setSubject("MEDHELP MEDICAL CENTER");

            Thread thread = new Thread(mail);
            thread.start();

        }else {
            new Alert(Alert.AlertType.ERROR,"Wrong Credentials").show();
        }

    }

}
