package com.devstack.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormController {

    public AnchorPane context;

    public void customerManageOnAction(ActionEvent event) throws IOException {
        setUi("CustomerForm");
    }

    public void productManageOnAction(ActionEvent event) throws IOException {
        setUi("ProductMainForm");
    }

    public void placeOrderOnAction(ActionEvent event) {

    }

    public void orderDetailOnAction(ActionEvent event) {

    }

    public void incomeReportOnAction(ActionEvent event) {

    }

    public void logoutOnAction(ActionEvent event) throws IOException {
        setUi("LoginForm");
    }

    public void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + url + ".fxml"))));
    }
}
