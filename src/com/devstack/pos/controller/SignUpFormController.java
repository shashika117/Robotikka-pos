package com.devstack.pos.controller;

import com.devstack.pos.dao.DatabaseAccessCode;
import com.devstack.pos.util.PasswordManager;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpFormController {

    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;
    public AnchorPane context;

    public void alreadyHaveAnAccountOnAction(ActionEvent event) throws IOException {
        setUi("LoginForm");
    }

    public void registerOnAction(ActionEvent event) {
        if (txtEmail.getText().trim().equals("") || txtPassword.getText().trim().equals("")) {
            new Alert(Alert.AlertType.WARNING, "email or password cannot be empty!").show();
        } else {
            try {
                if (new DatabaseAccessCode().createUser(
                        txtEmail.getText().toLowerCase().trim(),
                        PasswordManager.encryptPassword(txtPassword.getText().trim()))) {

                    new Alert(Alert.AlertType.INFORMATION, "User added!").show();
                    clearFields();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try again!").show();
                }

            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }

    private void clearFields() {
        txtEmail.clear();
        txtPassword.clear();
    }

    public void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + url + ".fxml"))));
    }
}
