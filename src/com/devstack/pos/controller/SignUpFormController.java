package com.devstack.pos.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpFormController {

    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public AnchorPane context;

    public void alreadyHaveAnAccountOnAction(ActionEvent event) throws IOException {
        setUi("LoginForm");
    }

    public void registerOnAction(ActionEvent event) {

    }
    public void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml"))));
    }
}
