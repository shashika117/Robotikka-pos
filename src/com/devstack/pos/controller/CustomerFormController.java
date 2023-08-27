package com.devstack.pos.controller;

import com.devstack.pos.dao.DatabaseAccessCode;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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
        try {
            if (DatabaseAccessCode.creatCustomer(
                    txtEmail.getText().toLowerCase().trim(),
                    txtName.getText().trim(),
                    txtContact.getText(),
                    Double.parseDouble(txtSalary.getText().trim())
            )) {
                new Alert(Alert.AlertType.INFORMATION,"Customer saved successfully!").show();
                clearFields();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try again!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        txtName.clear();
        txtEmail.clear();
        txtSalary.clear();
        txtContact.clear();
    }

    public void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + url + ".fxml"))));
    }
}
