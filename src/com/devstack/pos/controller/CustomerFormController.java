package com.devstack.pos.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerFormController {

    public AnchorPane context;
    public JFXTextField txtEmail;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtSalary;
    public TextField txtSearch;
    public TableView tblCustomer;
    public TableColumn colId;
    public TableColumn colEmail;
    public TableColumn colName;
    public TableColumn colSalary;
    public TableColumn colContact;
    public TableColumn colOperate;

    public void backToHomeOnAction(ActionEvent event) throws IOException {
        setUi("DashBoardForm");
    }

    public void manageLoyaltyCardOnAction(ActionEvent event) {

    }

    public void addNewCustomerOnAction(ActionEvent event) {

    }

    public void saveCustomerOnAction(ActionEvent event) {

    }
    public void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + url + ".fxml"))));
    }
}
