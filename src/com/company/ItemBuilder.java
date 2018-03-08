package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ItemBuilder{

    private static Stage window;
    private static Scene scene;
    private static ComboBox<String> cmbItem;
    private static Label lblItem, lblName, lblUnit, lblAmount;
    private static TextField txtName, txtUnit, txtAmount;
    private static Button btnAddIngredient, btnSave;
    private static TableView<Ingredient> table;
    private static ObservableList<Ingredient> ingredientsList;

    private static final String DIR_MENU_ITEMS = "C:\\Users\\Kyle\\IdeaProjects\\Menu Wizard\\MenuItems\\";

    public static void display(Stage primaryStage) throws Exception
    {
        window = primaryStage;
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Menu Item Builder");

        VBox leftLayout = new VBox();
        lblItem = new Label("Menu Item");
        cmbItem = new ComboBox<>();
        for (String s : foodFiles(DIR_MENU_ITEMS)) {
            cmbItem.getItems().add(s);
        }
        cmbItem.setEditable(true);
        cmbItem.setOnAction( e -> {
            cmbItem.getItems().add(cmbItem.getValue());
            ingredientsList.clear();
        });

        lblName = new Label("Ingredient Name");
        txtName = new TextField();

        lblUnit = new Label("Units");
        txtUnit = new TextField();

        lblAmount = new Label("Amount");
        txtAmount = new TextField();
        txtAmount.setOnAction( e -> {
            if (Validator.doubleCheck(txtAmount.getText()) == false) {
                AlertBox.display("Data Type Error.", "Please enter a number for the amount.");
                txtAmount.clear();
            }
        });

        btnAddIngredient = new Button("Add Ingredient");

        leftLayout.setPadding(new Insets(20,20,25,20));
        leftLayout.setSpacing(5);
        leftLayout.getChildren().addAll(lblItem, cmbItem, lblName, txtName, lblUnit,
                txtUnit, lblAmount, txtAmount,btnAddIngredient);

        //Name column
        TableColumn<Ingredient, String> nameColumn = new TableColumn<>("Ingredient");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Unit column
        TableColumn<Ingredient, String> unitColumn = new TableColumn<>("Unit");
        unitColumn.setMinWidth(100);
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("units"));

        //Amount column
        TableColumn<Ingredient, Double> amountColumn = new TableColumn<>("Amount");
        amountColumn.setMinWidth(100);
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        table = new TableView<>();
        ingredientsList = FXCollections.observableArrayList();
        //table.setItems(getProduct());
        table.getColumns().addAll(nameColumn, unitColumn, amountColumn);

        btnAddIngredient.setOnAction( e -> {
            if (Validator.emptyCheck(cmbItem.getValue()) == true) {
                AlertBox.display("Empty Menu Item", "Please enter the name of the menu item.");
            } else if (Validator.emptyCheck(txtName.getText()) == true) {
                AlertBox.display("Empty Ingredient Name",
                        "Please enter the name of the ingredient you want ot add.");
            } else if (Validator.emptyCheck(txtUnit.getText()) == true) {
                AlertBox.display("Empty Units", "Please enter the units used to measure your ingredient.");
            } else if (Validator.emptyCheck(txtAmount.getText()) == true) {
                AlertBox.display("Empty Amount", "Please enter the amount of your ingredient used");
            } else {
                Boolean confirm = ConfirmBox.display("Confirm Save", "Add this ingredient to you current Menu Item?");
                if (confirm == true) {
                    try {
                        Ingredient newIngredient = new Ingredient(txtName.getText(), txtUnit.getText(),
                                Double.parseDouble(txtAmount.getText()));
                        ingredientsList.add(newIngredient);
                        //ingredientsList.sort(); eventually automatically sort the list.
                        table.setItems(ingredientsList);
                    } catch (Exception el) {
                        AlertBox.display("Error", "There was an error when trying to add an ingredient to the list");
                    }
                }
            }
        });

        btnSave = new Button("Submit");
        btnSave.setOnAction( e -> {
            Boolean confirm = ConfirmBox.display("Confirm Print Menu Item", "Print" + cmbItem.getValue() +
                " to an '.fd' file?");
            if (confirm == true) {
                try {
                    buildMenuItem(cmbItem.getValue(), ingredientsList);
                }catch (Exception el){
                    AlertBox.display("Error", "There was an error when trying to create an '.fd' file");
                }
            }
        });

        //Layout
        VBox centerLayout = new VBox(10);
        centerLayout.setPadding(new Insets(20,20,20,20));
        leftLayout.setSpacing(5);
        centerLayout.getChildren().addAll(table, btnSave);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(centerLayout);
        mainLayout.setLeft(leftLayout);


        scene = new Scene(mainLayout, 700,325);
        window.setScene(scene);
        window.show();
    }

    public static void buildMenuItem(String name, ObservableList<Ingredient> ingredients) throws IOException{
        MenuItem newItem = new MenuItem(name, ingredients);

        String fileName = DIR_MENU_ITEMS + newItem.getName() + ".fd";

        String printVal = newItem.getName() + "\n\n";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (Ingredient ing: newItem.getIngredients()) {
            printVal += ing.getName() + "|" + ing.getUnits() + "|" + ing.getAmount() +"\n";
        }
        writer.write(printVal);
        writer.close();

    }

    public static List<String> foodFiles(String directory) {
        List<String> foodFiles = new ArrayList<>();
        File dir = new File(directory);
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(".fd")) {
                foodFiles.add(file.getName().substring(0, file.getName().length() - 3));
            }
        }
        return foodFiles;
    }

//    public static Ingredient fileIngredients(String foodName, String directory) {
//        BufferedReader reader = null;
//        File dir = new File(directory);
//        String delim = "|";
//        try {
//            for (File file : dir.listFiles()) {
//                if (file.getName().equals(foodName + ".fd")) {
//                    reader = new BufferedReader(new FileReader(directory + foodName +".fd"));
//
//                }
//            }
//
//        } catch (Exception e) {
//            AlertBox.display("Read file error", "The Menu Item file you were searhing for either doesn't exist or is corrupted");
//        }
//
//    }

}
