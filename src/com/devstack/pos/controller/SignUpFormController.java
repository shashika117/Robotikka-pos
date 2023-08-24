package com.devstack.pos.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.jdbc.Driver;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpFormController {

    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;
    public AnchorPane context;

    public void alreadyHaveAnAccountOnAction(ActionEvent event) throws IOException {
        setUi("LoginForm");
    }

    public void registerOnAction(ActionEvent event) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/robotikka","root","1234");
            String sql = "INSERT INTO user VALUES (?,?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,txtEmail.getText());
            statement.setString(2,txtPassword.getText());

            if (statement.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"User added!").show();
                txtEmail.clear();
                txtPassword.clear();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try again!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }
    public void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+url+".fxml"))));
    }
}
