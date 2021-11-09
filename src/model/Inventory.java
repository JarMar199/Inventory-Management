package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * Stores Part and Product objects
 */
public class Inventory {


    private static int inventoryPartId = 1;
    private static int inventoryProductId = 1000;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**
     * @return part id
     */
    public static int getInventoryPartId() {
        return inventoryPartId;
    }

    /**
     * @param inventoryPartId set part id
     */
    public static void setInventoryPartId(int inventoryPartId) {
        Inventory.inventoryPartId = inventoryPartId;
    }

    /**
     * @return the product id
     */
    public static int getInventoryProductId() {
        return inventoryProductId;
    }

    /**
     * @param inventoryProductId set product id
     */
    public static void setInventoryProductId(int inventoryProductId) {
        Inventory.inventoryProductId = inventoryProductId;
    }

    /**
     * @param newPart the part to be added to inventory list
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
        int updatePartId = getInventoryPartId() + 1;
        setInventoryPartId(updatePartId);
    }

    /**
     * @param newFilteredPart the part to be added to filtered list
     */
    public static void addFilteredPart(Part newFilteredPart){
        filteredParts.add(newFilteredPart);
    }

    /**
     * @param newFilteredProduct the product to be added to filtered list
     */
    public static void addFilteredProduct(Product newFilteredProduct){
        filteredProducts.add(newFilteredProduct);
    }

    /**
     * @param newProduct the product to be added to inventory list
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
        int updateProductId = getInventoryProductId() + 1;
        setInventoryProductId(updateProductId);
    }

    /**
     * @param partId the part id to search
     * @return the part with matching ID
     */
    public static boolean lookupPart(int partId){
        if(!(Inventory.getFilteredParts().isEmpty())){
            Inventory.getFilteredParts().clear();
        }

        for(Part part : Inventory.getAllParts()) {
            if(part.getId()  == partId){
                Inventory.addFilteredPart(part);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param productId the product id to search
     * @return the product with matching ID
     */
    public static boolean lookupProduct(int productId){
        if(!(Inventory.getFilteredProducts().isEmpty())){
            Inventory.getFilteredProducts().clear();
        }

        for(Product product : Inventory.getAllProducts()) {
            if(product.getId()  == productId){
                Inventory.addFilteredProduct(product);
                return true;
            }
        }
        return false;
    }

    /**
     * @param name the input string for part search
     * @return the part that contains string input
     */
    public static ObservableList<Part> lookupPartName(String name){

        if(!(Inventory.getFilteredParts().isEmpty())){
            Inventory.getFilteredParts().clear();
        }
        for(Part part : Inventory.getAllParts()){
            if(part.getName().toLowerCase().contains(name.toLowerCase() )) {
                Inventory.getFilteredParts().add(part);
            }
        }
        if((Inventory.getFilteredParts().isEmpty())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not Found");
            alert.setContentText("Part not found");
            alert.showAndWait();
            return Inventory.getAllParts();
        }else {
            return Inventory.getFilteredParts();
        }
    }

    /**
     * @param name the input string for product search
     * @return the product that contains string input
     */
    public static ObservableList<Product> lookupProduct(String name){
        if(!(Inventory.getFilteredProducts().isEmpty())){
            Inventory.getFilteredProducts().clear();
        }
        for(Product product : Inventory.getAllProducts()){
            if(product.getName().toLowerCase().contains(name.toLowerCase() )) {
                Inventory.getFilteredProducts().add(product);
            }
        }
        if((Inventory.getFilteredProducts().isEmpty())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not Found");
            alert.setContentText("Product not found");
            alert.showAndWait();
            return Inventory.getAllProducts();
        }else {
            return Inventory.getFilteredProducts();
        }
    }

    /**
     * @param index the part id to search
     * @param selectedPart the updated part information to replace
     */
    public static void updatePart(int index, Part selectedPart) {
        int i = 0;

        for(Part part : Inventory.getAllParts()){
            if(part.getId() == index){
                Inventory.getAllParts().set(i, selectedPart);
                return;
            }
            i++;
        }
    }

    /**
     * @param index the product id to search
     * @param newProduct the updated product information to replace
     */
    public static void updateProduct(int index, Product newProduct) {
        int i = 0;

        for(Product product : Inventory.getAllProducts()){
            if(product.getId() == index){
                Inventory.getAllProducts().set(i, newProduct);
                return;
            }
            i++;
        }
    }

    /**
     * @param selectedPart the part to be removed from inventory list
     */
    public static boolean deletePart(Part selectedPart) {
        for(Part part : Inventory.getAllParts()) {
            if(part == selectedPart) {
               Inventory.getAllParts().remove(selectedPart);
               return true;
            }
        }
        return false;
    }

    /**
     * @param selectedProduct the product to be removed from inventory list
     */
    public static boolean deleteProduct(Product selectedProduct){
        for(Product product : Inventory.getAllProducts()) {
            if(product == selectedProduct) {
                Inventory.getAllProducts().remove(selectedProduct);
                return true;
            }
        }
        return false;
    }

    /**
     * @return the parts in inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return the filtered parts in inventory from search
     */
    public static ObservableList<Part> getFilteredParts() {
        return filteredParts;
    }

    /**
     * @return the products in inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * @return the filtered products in inventory from search
     */
    public static ObservableList<Product> getFilteredProducts(){
        return filteredProducts;
    }
}
