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
 * Modifies a Part that is already created and in the system
 */
public class ModifyPartController implements Initializable{

    @FXML
    private ToggleGroup addPartTG;

    @FXML
    private Label modifyPartLbl;

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
     * @param event changes label to 'Machine ID' if in house button selected
     */
    @FXML
    void onActionInHouse(ActionEvent event) {
        partTypeLbl.setText("Machine ID");
    }

    /**
     * @param event changes label to 'Company Name' if outsourced button selected
     */
    @FXML
    void onActionOutsourced(ActionEvent event) {
        partTypeLbl.setText("Company Name");
    }

    /**
     * @param event saves updated part information.
     *              Checks input validation. Creates new part and replaces the part to be modified.
     */
    @FXML
    void onActionSavePart(ActionEvent event) {
        try{
            int id = Integer.parseInt(partIdTxt.getText());
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
                    InHouse part = new InHouse(id,name,price,stock,min,max,machineId);
                    Inventory.updatePart(id, part);
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
                    Outsourced part = new Outsourced(id,name,price,stock,min,max,companyName);
                    Inventory.updatePart(id, part);
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Main Menu");
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
        catch(NumberFormatException | IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid values");
            alert.showAndWait();
        }

    }

    /**
     * @param part Fills information for part to be modified.
     *             Selected part's information is filled into the form.
     */
    public void sendPart(Part part){
        partIdTxt.setText(String.valueOf(part.getId()));
        partNameTxt.setText(part.getName());
        partInvTxt.setText(String.valueOf(part.getStock()));
        partPriceTxt.setText(String.valueOf(part.getPrice()));
        partMaxTxt.setText(String.valueOf(part.getMax()));
        partMinTxt.setText(String.valueOf(part.getMin()));
        if(part instanceof InHouse) {
            int machineId = ((InHouse) part).getMachineId();
            partTypeTxt.setText(String.valueOf(machineId));
            partInHouseRBtn.setSelected(true);
        }else{
            String companyName = ((Outsourced) part).getCompanyName();
            partTypeTxt.setText(companyName);
            partTypeLbl.setText("Company Name");
            partOutsourcedRBtn.setSelected(true);
        }
    }

    /**
     * Disables ID text field
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdTxt.setDisable(true);

    }


}
