package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Dto.UserDto;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.Custom.CreateAccountBO;
import lk.ijse.entity.User;
import lk.ijse.mail.Mail;
import lk.ijse.regex.Regex;

import java.io.IOException;
import java.sql.SQLException;

public class  CreateAccountFormController {
    public AnchorPane rootnode;
    public TextField FirstName;
    public TextField SecondName;
    public TextField Address;
    public TextField TelNum;
    public TextField Email;


    private CreateAccountBO createAccountBO = (CreateAccountBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CREATE_ACCOUNT);


    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/view/LoginPageForm.fxml"));
        Scene scene = new Scene(parent);

        rootnode.getChildren().clear();
        Stage stage = (Stage) rootnode.getScene().getWindow();

        stage.setScene(scene);
    }

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {



        if(Regex.getEmailPattern().matcher(Email.getText()).matches()){

            String password1 = FirstName.getText();
            String password2 = SecondName.getText();
            String email = Email.getText();

            if (!email.isEmpty() && !password1.isEmpty() && !password2.isEmpty() ) {
                try {
                    User dto = createAccountBO.searchByEmail(email);

                    if (dto == null) {
                        if (password1.equals(password2)) {
                            UserDto dto2 = new UserDto(email, password1);
                            boolean isSaved = createAccountBO.saveCredentials(dto2);

                            if (isSaved) {
                                new Alert(Alert.AlertType.CONFIRMATION, "User Saved!").show();

                                Mail mail = new Mail();
                                mail.setMsg("You are a new user. Welcome to MEDHELP MEDICAL CENTER.");
                                mail.setTo(email);
                                mail.setSubject("MEDHELP MEDICAL CENTER");

                                Thread thread = new Thread(mail);
                                thread.start();

                            }
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Passwords do not match!").show();
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }else {
            new Alert(Alert.AlertType.WARNING,"Invalid Email").show();
        }


    }
}
