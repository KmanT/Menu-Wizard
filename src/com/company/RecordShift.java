package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class RecordShift {

    private static Stage window;
    private static Label lblWeek, lblCycle, lblDay, lblStoneOVen, lblGreenPlate;
    private static TitledPane paneShift, paneLocation;
    private static ChoiceBox choiceCycle, choiceDay;
    private static RadioButton radLunch, radDinner;
    private static TextField txtWeek, txtStoneOven, txtGreenPlate;
    private static TableView<Record> recordTable;
    private static Button btnSaveShift, btnCommit;

    private static final String[] DAY_NAME = {"MON","TUE","WED","THR","FRI","SAT","SUN"};
    private static final String DIR_RECORD = "C:\\Users\\Kyle\\IdeaProjects\\Menu Wizard\\ShiftReports\\";
    private static final int[] CYCLE = {1,2,3};


    public static void display(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Engrained Menu Planner");

        Queue<Record> recordList = new LinkedList<>();
        //TableView<Record> recordTableView = new TableView<>();

        //TopLayout
        lblWeek = new Label("Week Number");
        txtWeek = new TextField();
        txtWeek.setOnAction( e-> {
            if (Validator.intCheck(txtWeek.getText()) == false) {
                AlertBox.display("Data Type Error.", "Please enter a whole number for the Week Number.");
                txtWeek.clear();
            }
        });
        lblCycle = new Label("Cycle");
        choiceCycle = new ChoiceBox<Integer>();
        choiceCycle.getItems().addAll(CYCLE[0],CYCLE[1],CYCLE[2]);
        choiceCycle.setValue(CYCLE[0]);
        lblDay = new Label("Day of the Week");
        choiceDay = new ChoiceBox<Integer>();
        choiceDay.getItems().addAll(DAY_NAME[0],DAY_NAME[1],DAY_NAME[2],
                DAY_NAME[3],DAY_NAME[4], DAY_NAME[5],DAY_NAME[6]);
        choiceDay.setValue(DAY_NAME[0]);

        HBox topLayout = new HBox();
        topLayout.setPadding(new Insets(10,10,10,10));
        topLayout.setSpacing(5);
        topLayout.getChildren().addAll(lblWeek,txtWeek,lblCycle,choiceCycle,lblDay,choiceDay);


        //ShiftPane
        radLunch = new RadioButton("Lunch");
        radLunch.setUserData("Lunch");
        radDinner = new RadioButton("Dinner");
        radDinner.setUserData("Dinner");
        ToggleGroup shiftGroup = new ToggleGroup();
        shiftGroup.getToggles().addAll(radLunch,radDinner);
        radLunch.setSelected(true);
        HBox shiftLayout = new HBox(10);
        shiftLayout.setPadding(new Insets(10,10,10,10));
        shiftLayout.getChildren().addAll(radLunch,radDinner);
        paneShift = new TitledPane("Shift",shiftLayout);
        paneShift.setCollapsible(false);

        //LocationPane
        GridPane locLayout = new GridPane();
        locLayout.setHgap(5);
        locLayout.setVgap(5);
        lblStoneOVen = new Label("Stone Oven");
        GridPane.setConstraints(lblStoneOVen,0,0);
        txtStoneOven = new TextField();
        txtStoneOven.setOnAction( e -> {
            if (Validator.intCheck(txtStoneOven.getText()) == false) {
                AlertBox.display("Data Type Error.", "Please enter a whole number for Stone Oven.");
                txtStoneOven.clear();
            }
        });
        GridPane.setConstraints(txtStoneOven,1,0);
        lblGreenPlate = new Label("Green Plate");
        GridPane.setConstraints(lblGreenPlate,0,1);
        txtGreenPlate = new TextField();
        txtGreenPlate.setOnAction( e -> {
            if (Validator.intCheck(txtWeek.getText()) == false) {
                AlertBox.display("Data Type Error.", "Please enter a whole number for the Green Plate.");
                txtWeek.clear();
            }
        });
        GridPane.setConstraints(txtGreenPlate,1,1);
        locLayout.setPadding(new Insets(10,10,10,10));
        locLayout.getChildren().addAll(lblStoneOVen,txtStoneOven,lblGreenPlate,txtGreenPlate);
        paneLocation = new TitledPane("Location",locLayout);
        paneLocation.setCollapsible(false);

        VBox leftLayout = new VBox();
        leftLayout.setPadding(new Insets(10,10,10,10));
        leftLayout.setSpacing(5);
        leftLayout.getChildren().addAll(paneShift,paneLocation);


        VBox centerLayout = new VBox();
        centerLayout.setPadding(new Insets(10,10,10,10));
        centerLayout.setSpacing(5);

        //DoW column
        TableColumn<Record, String> dayColumn = new TableColumn<>("DoW");
        dayColumn.setMinWidth(150);
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));

        //Shift column
        TableColumn<Record, String> shiftColumn = new TableColumn<>("Shift");
        shiftColumn.setMinWidth(100);
        shiftColumn.setCellValueFactory(new PropertyValueFactory<>("shift"));

        //StoneOven column
        TableColumn<Record, String> stoneOvenColumn = new TableColumn<>("SO");
        stoneOvenColumn.setMinWidth(100);
        stoneOvenColumn.setCellValueFactory(new PropertyValueFactory<>("stoneOven"));

        //GreenPlate column
        TableColumn<Record, String> greenPlateColumn = new TableColumn<>("GP");
        greenPlateColumn.setMinWidth(100);
        greenPlateColumn.setCellValueFactory(new PropertyValueFactory<>("greenPlate"));

        recordTable = new TableView<>();
        recordTable.setEditable(true);
        ObservableList<Record> ingredientsList = FXCollections.observableArrayList();
        recordTable.getColumns().addAll(dayColumn,shiftColumn,stoneOvenColumn,greenPlateColumn);
        centerLayout.getChildren().add(recordTable);

        //BottomLayout
        //Save Button
        btnSaveShift = new Button("Save Shift");
        btnSaveShift.setOnAction( e -> {
            if (Validator.emptyCheck(txtWeek.getText()) == true) {
                AlertBox.display("Empty Week Number", "Please enter a Week Number");
            } else if (Validator.emptyCheck(txtStoneOven.getText()) == true){
                AlertBox.display("Empty Stone Oven", "Please enter the number of meals for Stone Oven.");
            } else if (Validator.emptyCheck(txtGreenPlate.getText()) == true) {
                AlertBox.display("Empty Green Plate", "Please enter a the number of meals for Green Plate.");
            } else {
                boolean confirm = ConfirmBox.display("Save Confirmation",
                        "Are you sure you want to save this record to memory?");
                if (confirm == true) {
                    Record newRec = new Record(choiceDay.getValue().toString(),
                            shiftGroup.getSelectedToggle().getUserData().toString(),
                            Integer.parseInt(txtStoneOven.getText()), Integer.parseInt(txtGreenPlate.getText()));
                    recordList.add(newRec);
                    recordTable.getItems().add(newRec);
                }
            }

        });
        //Commit Button
        btnCommit = new Button("Commit Week");
        btnCommit.setOnAction( e -> {
            boolean confirm = ConfirmBox.display("Save Confirmation", "Are sure you want to print a report file?");
            if (confirm == true) {
                try {
                    int cycleNum = (int) choiceCycle.getValue();
                    int weekNum = Integer.parseInt(txtWeek.getText());
                    printRecord(recordList, weekNum, cycleNum);
                } catch (IOException ei) {
                    System.err.println("There was an error.");
                    AlertBox.display("Error","There was an error in the program.");
                }
            }
        } );

        HBox bottomLayout = new HBox();
        bottomLayout.setPadding(new Insets(10,10,10,10));
        bottomLayout.setSpacing(5);
        bottomLayout.setAlignment(Pos.CENTER);
        bottomLayout.getChildren().addAll(btnSaveShift,btnCommit);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topLayout);
        mainLayout.setLeft(leftLayout);
        mainLayout.setCenter(centerLayout);
        mainLayout.setBottom(bottomLayout);

        Scene scene = new Scene(mainLayout, 775,350);
        //scene.getStylesheets().add("form_style.css");
        window.setScene(scene);
        window.show();
    }

    public static void printRecord(Queue<Record> recordList, int weekNumber, int cycle) throws IOException{
        String fileName = DIR_RECORD + "Week_" + weekNumber + "_Cycle_" + cycle + ".rpt";

        String printVal = "Week: " + weekNumber + "\tCycle: " + cycle + "\n\n";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (Record rec: recordList) {
            printVal += rec.getDay() + "\t" + rec.getShift() + "\tSO:"
                    + rec.getStoneOven() + "\tGP:" + rec.getGreenPlate() + "\n";
        }
        writer.write(printVal);
        writer.close();
    }

}
