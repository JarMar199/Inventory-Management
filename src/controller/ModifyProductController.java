package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Modifies a product that is already created and in the system
 */
public class ModifyProductController implements Initializable {

    @FXML
    private Label addProductLbl;

    @FXML
    private TableColumn<Part, Integer> associatedPartIdCol;

    @FXML
    private TableColumn<Part, Integer> associatedPartInventoryCol;

    @FXML
    private TableColumn<Part, String> associatedPartNameCol;

    @FXML
    private TableColumn<Part, Double> associatedPartPriceCol;

    @FXML
    private TableView<Part> associatedPartTable;

    @FXML
    private Label productMinLbl;

    @FXML
    private Label productIdLbl;

    @FXML
    private TextField productIdTxt;

    @FXML
    private Label productInvLbl;

    @FXML
    private TextField productInvTxt;

    @FXML
    private Label productMaxLbl;

    @FXML
    private TextField productMaxTxt;

    @FXML
    private TextField productMinTxt;

    @FXML
    private Label productNameLbl;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TableColumn<Part, Integer> productPartIdCol;

    @FXML
    private TableColumn<Part, Integer> productPartInventoryCol;

    @FXML
    private TableColumn<Part, String> productPartNameCol;

    @FXML
    private TableColumn<Part, Double> productPartPriceCol;

    @FXML
    private TableView<Part> productPartTable;

    @FXML
    private Label productPriceLbl;

    @FXML
    private TextField productPriceTxt;

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
                    productPartTable.setItems(Inventory.getFilteredParts());
                    productPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                    productPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                    productPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                    productPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                } else {
                    productPartTable.setItems(Inventory.getAllParts());
                    productPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                    productPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                    productPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                    productPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Not Found");
                    alert.setContentText("Part not found");
                    alert.showAndWait();
                }
            } else {
                String searchId = partSearchBar.getText();
                productPartTable.setItems(Inventory.lookupPartName(searchId));
                productPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                productPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                productPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
                productPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            }
        }

        if (event.getCode() == KeyCode.ENTER && partSearchBar.getText().isEmpty()) {
            productPartTable.setItems(Inventory.getAllParts());
            productPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            productPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            productPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
    }

    /**
     *
     * @param event adds associated part to the product's associated parts list
     */
    @FXML
    void onActionAddAssociativePart(ActionEvent event) {
        int i = 0;
        Product updateProduct = null;
        for(Product product : Inventory.getAllProducts()){
            if(product.getId() == Integer.parseInt(productIdTxt.getText())){
                updateProduct = product;
                break;
            }
            i++;
        }

        if(updateProduct != null) {
            Part part = productPartTable.getSelectionModel().getSelectedItem();
            if (!updateProduct.getAllAssociatedParts().contains(part)) {
                updateProduct.getAllAssociatedParts().add(part);
            }
            associatedPartTable.setItems(updateProduct.getAllAssociatedParts());
            associatedPartIdCol.setCellValueFactory((new PropertyValueFactory<>("id")));
            associatedPartNameCol.setCellValueFactory((new PropertyValueFactory<>("name")));
            associatedPartInventoryCol.setCellValueFactory((new PropertyValueFactory<>("stock")));
            associatedPartPriceCol.setCellValueFactory((new PropertyValueFactory<>("price")));
        }
    }

    /**
     * @param event removes associated part from the product's associated parts list
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        int i = 0;
        Product updateProduct = null;
        for(Product product : Inventory.getAllProducts()){
            if(product.getId() == Integer.parseInt(productIdTxt.getText())){
                updateProduct = product;
                break;
            }
            i++;
        }

        if(updateProduct != null) {
            Part selectedAssociativePart = associatedPartTable.getSelectionModel().getSelectedItem();
            if (selectedAssociativePart != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selectedAssociativePart.getName() + "?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                    updateProduct.deleteAssociatedPart(selectedAssociativePart);
            }
        }
    }

    /**
     * @param event saves updated product information.
     *              Checks input validation. Creates new product and replaces the product to be modified.
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        int i = 0;
        Product updateProduct = null;
        for(Product product : Inventory.getAllProducts()){
            if(product.getId() == Integer.parseInt(productIdTxt.getText())){
                updateProduct = product;
                break;
            }
            i++;
        }

        if(updateProduct != null) {
            try {
                int id = Integer.parseInt(productIdTxt.getText());
                String name = productNameTxt.getText();
                int stock = Integer.parseInt(productInvTxt.getText());
                double price = Double.parseDouble(productPriceTxt.getText());
                int max = Integer.parseInt(productMaxTxt.getText());
                int min = Integer.parseInt(productMinTxt.getText());
                if (stock > max || stock < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please enter correct inventory values");
                    alert.showAndWait();
                } else {
                    Product newProd = new Product(id, name, price, stock, min, max);
                    for (Part part : updateProduct.getAllAssociatedParts()) {
                        newProd.addAssociatedPart(part);
                    }
                    Inventory.updateProduct(id, newProd);
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Main Menu");
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter valid values");
                alert.showAndWait();
            }
        }
    }

    /**
     * @param actionEvent returns user to the main menu screen
     */
    @FXML
    void cancelToMainMenu(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you wish to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * @param product Fills information for product to be modified.
     *             Selected product's information is filled into the form.
     */
    public void sendProduct(Product product){
        productIdTxt.setText(String.valueOf(product.getId()));
        productNameTxt.setText(product.getName());
        productInvTxt.setText(String.valueOf(product.getStock()));
        productPriceTxt.setText(String.valueOf(product.getPrice()));
        productMaxTxt.setText(String.valueOf(product.getMax()));
        productMinTxt.setText(String.valueOf(product.getMin()));

        associatedPartTable.setItems(product.getAllAssociatedParts());
        associatedPartIdCol.setCellValueFactory((new PropertyValueFactory<>("id")));
        associatedPartNameCol.setCellValueFactory((new PropertyValueFactory<>("name")));
        associatedPartInventoryCol.setCellValueFactory((new PropertyValueFactory<>("stock")));
        associatedPartPriceCol.setCellValueFactory((new PropertyValueFactory<>("price")));
    }

    /**
     * populates the available associated parts table
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    productPartTable.setItems((Inventory.getAllParts()));
    productPartIdCol.setCellValueFactory((new PropertyValueFactory<>("id")));
    productPartNameCol.setCellValueFactory((new PropertyValueFactory<>("name")));
    productPartInventoryCol.setCellValueFactory((new PropertyValueFactory<>("stock")));
    productPartPriceCol.setCellValueFactory((new PropertyValueFactory<>("price")));


    }

}
