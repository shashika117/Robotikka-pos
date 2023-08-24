package com.devstack.pos.controller;

import com.devstack.pos.util.PasswordManager;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.*;

public class LoginFormController {

    public JFXTextField txtEmail;

    public AnchorPane context;
    public JFXPasswordField txtPassword;

    public void signInOnAction() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/robotikka", "root", "1234");
            String sql = "SELECT * FROM user WHERE email = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, txtEmail.getText().trim().toLowerCase());

            ResultSet set = statement.executeQuery();
            if (set.next()) {
                if (PasswordManager.checkPassword(
                        txtPassword.getText().trim(), set.getString("password"))) {
                    System.out.println("Completed");
                } else {
                    new Alert(Alert.AlertType.ERROR, "Wrong password!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Email not found!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void createAnAccountOnAction(ActionEvent event) throws IOException {
        setUi("SignUpForm");
    }

    public void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + url + ".fxml"))));
    }

    public void emailOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    public void passwordOnAction(ActionEvent event) {
        signInOnAction();
    }


}
