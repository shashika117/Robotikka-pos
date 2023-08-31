package com.devstack.pos.controller;

import com.devstack.pos.dao.DatabaseAccessCode;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ProductMainFormController {
    public JFXTextField txtProductCode;
    public TextArea txtDescription;
    public JFXButton btnSaveProduct;
    public TextField txtSearchHere;
    public TableView tblProduct;
    public TableColumn colProductId;
    public TableColumn colProductDescription;
    public TableColumn colProductSeeMore;
    public TableColumn colProductDelete;
    public AnchorPane context;

    public void initialize() {
        productCodeSettingUp();
        loadDataToTable();
    }

    private void productCodeSettingUp() {
        try {
            txtProductCode.setText(String.valueOf(new DatabaseAccessCode().getLastProductCode()+1));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void backToHomeOnAction(ActionEvent event) throws IOException {
        setUi("DashBoardForm");
    }

    public void addNewProductOnAction(ActionEvent event) {
        clearFieldsAndFinalize();
    }

    public void saveProductOnAction(ActionEvent event) {
        if (Objects.equals(txtDescription.getText(), "")) {
            new Alert(Alert.AlertType.WARNING, "Description cannot be empty!").show();
        } else {
            try {
                if (btnSaveProduct.getText().equals("Save Product")) {
                    if (new DatabaseAccessCode().saveProduct(Integer.parseInt(txtProductCode.getText()), txtDescription.getText())) {
                        new Alert(Alert.AlertType.INFORMATION,"Product saved!").show();
                        clearFieldsAndFinalize();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Try again!").show();
                    }

                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, e.getMessage()).show();
            }
        }
    }

    private void clearFieldsAndFinalize() {
        txtDescription.clear();
        productCodeSettingUp();
        btnSaveProduct.setText("Save Product");
        loadDataToTable();
    }

    private void loadDataToTable() {

    }

    public void addNewBatchOnAction(ActionEvent event) {

    }

    public void setUi(String url) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + url + ".fxml"))));
    }
}
