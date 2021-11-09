package controller;

// javadoc found in \JMInventoryMac\javadoc

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
 * Creates an inventory management application
 */
public class Main extends Application {

    /**
     * RUNTIME ERROR.
     * I was having difficult implementing the search function. When searching for a Part ID, the table would only highlight
     * the searched part. I used an ObservableList for the filtered part that would be used for strings in order to display
     * only the searched part in the tableview. This allowed me to display all the parts or the searched part depending if
     * the search was successful.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * FUTURE ENHANCEMENT.
     * A feature I would like to implement in a future update would be the ability to drag and drop associated parts in the
     * product form. It would give the user the ability to select a part from the available list and use their mouse to drag
     * the selected item into the associated parts list.
     */
    public static void main(String[] args) {

        InHouse brakes = new InHouse(Inventory.getInventoryPartId(), "Brakes", 15.00, 3, 1, 5, 111);
        Inventory.addPart(brakes);
        Outsourced wheel = new Outsourced(Inventory.getInventoryPartId(), "Wheel", 11.00, 2, 0, 6, "Wheelieos");
        Inventory.addPart(wheel);

        Product giantBike = new Product(Inventory.getInventoryProductId(), "Giant Bike", 299.99, 3, 0, 5);
        giantBike.addAssociatedPart(wheel);
        giantBike.addAssociatedPart(brakes);
        Inventory.addProduct(giantBike);

        Product tricycle = new Product(Inventory.getInventoryProductId(), "Tricycle", 99.99, 1, 0, 3);
        Inventory.addProduct(tricycle);
        tricycle.addAssociatedPart(wheel);

        launch(args);
    }
}
