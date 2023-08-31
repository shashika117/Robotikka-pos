package com.devstack.pos.controller;


import com.devstack.pos.dao.DatabaseAccessCode;
import com.devstack.pos.dto.CustomerDto;
import com.devstack.pos.view.tm.CustomerTm;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class CustomerFormController {

    public AnchorPane context;
    public JFXTextField txtEmail;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtSalary;
    public TextField txtSearch;
    public TableView<CustomerTm> tblCustomer;
    public TableColumn<CustomerTm, Integer> colId;
    public TableColumn<CustomerTm, String> colEmail;
    public TableColumn<CustomerTm, String> colName;
    public TableColumn<CustomerTm, Double> colSalary;
    public TableColumn<CustomerTm, String> colContact;
    public TableColumn<CustomerTm, Button> colOperate;
    public JFXButton btnSaveCustomer;

    public void initialize() throws SQLException, ClassNotFoundException {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOperate.setCellValueFactory(new PropertyValueFactory<>("operate"));

        loadData();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        setItemsToFields(newValue);
                    }
                }
        );
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadData();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void loadData() throws SQLException, ClassNotFoundException {
        ObservableList<CustomerTm> customers = FXCollections.observableArrayList();
        int counter = 1;
        for (CustomerDto c :
                txtSearch.getText().length()>0? new DatabaseAccessCode().searchCustomer(txtSearch.getText().trim().toLowerCase()):
                        new DatabaseAccessCode().findAllCustomer()) {
            Button operate = new Button("Delete");
            operate.setOnAction(event -> {
                try {
                    deleteButtonSetOnAction(c);
                } catch (SQLException | ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                }
            });
            customers.add(
                    new CustomerTm(counter++, c.getEmail(), c.getName(), c.getContact(), c.getSalary(), operate)
            );
        }
        tblCustomer.setItems(customers);
    }

    private void deleteButtonSetOnAction(CustomerDto c) throws SQLException, ClassNotFoundException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are sure to delete?",ButtonType.YES,ButtonType.NO);

        Button yesButton = (Button) alert.getDialogPane().lookupButton(ButtonType.YES);
        yesButton.setDefaultButton(false);
        Button noButton = (Button) alert.getDialogPane().lookupButton(ButtonType.NO);
        noButton.setDefaultButton(false);

        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get().equals(ButtonType.YES)){

            if (new DatabaseAccessCode().deleteCustomer(c.getEmail())){
                new Alert(Alert.AlertType.INFORMATION,"Customer deleted!").show();
                clearFields();

                txtEmail.setEditable(true);
                loadData();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try again!").show();
            }
        }


    }

    private void setItemsToFields(CustomerTm customerTm) {
        txtEmail.setText(customerTm.getEmail());
        txtEmail.setEditable(false);
        txtName.setText(customerTm.getName());
        txtContact.setText(customerTm.getContact());
        txtSalary.setText(String.valueOf(customerTm.getSalary()));
        btnSaveCustomer.setText("Update Customer");
    }

    public void backToHomeOnAction(ActionEvent event) throws IOException {
        setUi("DashBoardForm");
    }

    public void manageLoyaltyCardOnAction(ActionEvent event) {

    }

    public void addNewCustomerOnAction(ActionEvent event) {
        txtEmail.setEditable(true);
        clearFields();
        txtEmail.requestFocus();
        btnSaveCustomer.setText("Save Customer");
    }

    public void saveCustomerOnAction(ActionEvent event) {
        if (Objects.equals(txtEmail.getText(), "") ||
                Objects.equals(txtName.getText(), "") ||
                Objects.equals(txtContact.getText(), "") ||
                Objects.equals(txtSalary.getText(), "")
        ) {
            new Alert(Alert.AlertType.WARNING, "fields cannot be empty!").show();
        } else {
            try {
                if (btnSaveCustomer.getText().equals("Update Customer")) {
                    if (new DatabaseAccessCode().updateCustomer(
                            txtEmail.getText().toLowerCase().trim(),
                            txtName.getText().trim(),
                            txtContact.getText(),
                            Double.parseDouble(txtSalary.getText().trim()))) {
                        new Alert(Alert.AlertType.INFORMATION, "Customer Updated Successfully!").show();
                        clearFields();
                        btnSaveCustomer.setText("Save Customer");
                        txtEmail.setEditable(true);
                        loadData();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try again").show();
                    }
                } else {
                    if (new DatabaseAccessCode().creatCustomer(
                            txtEmail.getText().toLowerCase().trim(),
                            txtName.getText().trim(),
                            txtContact.getText(),
                            Double.parseDouble(txtSalary.getText().trim()))
                    ) {
                        new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully!").show();
                        clearFields();
                        loadData();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Try again!").show();
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
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
