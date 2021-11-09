package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Creates a new Part to be added to the inventory system
 */
public class AddPartController implements Initializable {



    @FXML
    private Label addPartLbl;


    @FXML
    private ToggleGroup addPartTG;

    @FXML
    private Label partIdLbl;

    @FXML
    private TextField partIdTxt;

    @FXML
    private RadioButton partInHouseRBtn;

    @FXML
    private Label partInvLbl;

    @FXML
    private TextField partInvTxt;

    @FXML
    private Label partMaxLbl;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private Label partMinLbl;

    @FXML
    private TextField partMinTxt;

    @FXML
    private Label partNameLbl;

    @FXML
    private TextField partNameTxt;

    @FXML
    private RadioButton partOutsourcedRBtn;

    @FXML
    private Label partPriceLbl;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private Label partTypeLbl;

    @FXML
    private TextField partTypeTxt;

    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {

        try{
            int id = Inventory.getInventoryPartId();
            String name = partNameTxt.getText();
            int stock = Integer.parseInt(partInvTxt.getText());
            double price = Double.parseDouble(partPriceTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            if(partInHouseRBtn.isSelected()) {
                int machineId = Integer.parseInt(partTypeTxt.getText());
                if (stock > max || stock < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please enter correct inventory values");
                    alert.showAndWait();
                } else {
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Main Menu");
                    stage.setScene(scene);
                    stage.show();
                }
            }
            else{
                if (stock > max || stock < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please enter correct inventory values");
                    alert.showAndWait();
                }
                else {
                    String companyName = partTypeTxt.getText();
                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Main Menu");
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid values");
            alert.showAndWait();
        }
    }

    @FXML
    void onActionInHouse(ActionEvent event) {
        partTypeLbl.setText("Machine ID");
    }

    @FXML
    void onActionOutsourced(ActionEvent event) {
        partTypeLbl.setText("Company Name");
    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partInHouseRBtn.setSelected(true);
        //partIdTxt.setDisable(true);

    }

}
