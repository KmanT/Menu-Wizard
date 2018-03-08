package com.company;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{

    Stage windowMain;
    Scene scene;
    Label lblWelcome;
    Button btnRecordShift, btnItemBuilder;


    public static void main(String[] args) {
	    launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        windowMain = primaryStage;
        windowMain.setTitle("Engrained Menu Wizard");
        windowMain.setMinWidth(500);
        windowMain.setMinHeight(200);

        lblWelcome = new Label("Welcome to the Menu Wizard!");

        btnItemBuilder = new Button("Menu Item Builder");
        btnItemBuilder.setOnAction( e -> {
            try {
                Stage builderStage = new Stage();
                ItemBuilder.display(builderStage);
            } catch (Exception el) {
                AlertBox.display("Error", "There was an error when launching ItemBuilder.");
            }
        });

        btnRecordShift = new Button("Record Shifts");
        btnRecordShift.setOnAction( e -> {
            try {
                Stage shiftStage = new Stage();
                RecordShift.display(shiftStage);
            } catch (Exception el) {
                AlertBox.display("Error", "There was an error when launching ShiftRecorder.");
            }
        });


        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(lblWelcome,btnItemBuilder,btnRecordShift);
        scene = new Scene(layout);
        windowMain.setScene(scene);
        windowMain.show();
    }
}
