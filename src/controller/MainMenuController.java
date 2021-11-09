package controller;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import model.Inventory;
import model.Part;
import model.Product;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *Main menu screen that shows Parts and Product in inventory. Options to Add, Modify, or Delete inventory
 */
public class MainMenuController implements Initializable {

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Part> partTable;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, Integer> productInventoryCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TextField productSearchBar;

    @FXML
    private TextField partSearchBar;

    /**
     * @param event Displays parts that meet search criteria.
     *              Shows parts that meet search input. Displays all available parts if no parts are found.
     */
    @FXML
    void searchPartEnter(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER && !partSearchBar.getText().isEmpty()) {
            if(partSearchBar.getText().matches("\\d+")) {
                int searchId = Integer.parseInt(partSearchBar.getText());
                if (Inventory.lookupPart(searchId)) {
                    partTable.setItems(Inventory.getFilteredParts());
                    partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                    partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                    partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                    partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                } else {
                    partTable.setItems(Inventory.getAllParts());
                    partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                    partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                    partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                    partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Not Found");
                    alert.setContentText("Part not found");
                    alert.showAndWait();
                }
            } else {
                String searchId = partSearchBar.getText();
                partTable.setItems(Inventory.lookupPartName(searchId));
                partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }

        if (event.getCode() == KeyCode.ENTER && partSearchBar.getText().isEmpty()) {
            partTable.setItems(Inventory.getAllParts());
            partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }

    }

    /**
     * @param event Displays products that meet search criteria.
     *              Shows products that meet search input. Displays all available products if no parts are found.
     */
    @FXML
    void searchProductEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !productSearchBar.getText().isEmpty()) {
            if(productSearchBar.getText().matches("\\d+")) {
                int searchId = Integer.parseInt(productSearchBar.getText());
                if (Inventory.lookupProduct(searchId)) {
                    productTable.setItems(Inventory.getFilteredProducts());
                    productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                    productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                    productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                    productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                } else {
                    productTable.setItems(Inventory.getAllProducts());
                    productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                    productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                    productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                    productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Not Found");
                    alert.setContentText("Product not found");
                    alert.showAndWait();
                }
            } else {
                String searchId = productSearchBar.getText();
                productTable.setItems(Inventory.lookupProduct(searchId));
                productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }

        if (event.getCode() == KeyCode.ENTER && productSearchBar.getText().isEmpty()) {
            productTable.setItems(Inventory.getAllProducts());
            productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
    }

    /**
     * @param event terminates application
     */
    @FXML
    void exitProgram(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you wish to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    /**
     * @param event takes user to the Add part display screen
     */
    public void toAddPart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param event takes user to modify part display screen with selected part information
     */
    @FXML
    void toModifyPart(ActionEvent event) throws IOException{
        if(partTable.getSelectionModel().getSelectedItem() != null  ) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendPart(partTable.getSelectionModel().getSelectedItem());


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * @param event delete selected part from inventory
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + selectedPart.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }
    }
    /**
     * @param event takes user to the Add product display screen
     */
    @FXML
    void toAddProduct(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param event takes user to modify product display screen with selected product information
     */
    @FXML
    void toModifyProduct(ActionEvent event) throws IOException {
        if(productTable.getSelectionModel().getSelectedItem() != null  ) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MPController = loader.getController();
            MPController.sendProduct(productTable.getSelectionModel().getSelectedItem());


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * @param event delete selected product from inventory
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if(selectedProduct != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + selectedProduct.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if(selectedProduct.getAllAssociatedParts().isEmpty()) {
                    Inventory.deleteProduct(selectedProduct);
                } else {
                    Alert productAlert = new Alert(Alert.AlertType.ERROR);
                    productAlert.setTitle("Error");
                    productAlert.setContentText("Remove associated parts before deleting");
                    productAlert.showAndWait();
                }
            }
        }
    }

    /**
     * gathers and displays available inventory for the Part and the Product tables
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        productTable.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


}
