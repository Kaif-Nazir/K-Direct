package com.clientserver.clientserver;

import com.clientserver.clientserver.Server.Server;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class severController implements Initializable {

    public AnchorPane ap_main;
    public Button button_send;
    public TextField tf_message;
    public ScrollPane sp_main;
    public VBox vbox_message;
    public Button button_reset;
    public FontAwesomeIconView reset_icon;

    private Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_reset.setOnAction(this::reset);

        try{
            // 0 - 1023 1023 - 65535
            server  = new Server(new ServerSocket(1234));
            System.out.println("Connected");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Creating Server");
        }

        vbox_message.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldVal, Number newVal) {
                sp_main.setVvalue((Double) newVal);
            }
        });
        server.recieveMessageFromClient(vbox_message);

        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String messageToSend = tf_message.getText();
                if(!messageToSend.isEmpty()){
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5,5,5,10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle(
                                    "-fx-background-color: rgba(15,125,242,0.85);" +  // Blue with opacity
                                    "-fx-background-radius: 16px;" +
                                    "-fx-padding: 10px 14px;" +
                                    "-fx-font-size: 14px;" +
                                    "-fx-font-family: 'Segoe UI', 'Arial', sans-serif;" +
                                    "-fx-text-fill: white;"  // Ensures the text color is clear
                    );


                    textFlow.setPadding(new Insets(5 ,10 ,5 ,10));
                    text.setFill(Color.color(0.934 , 0.945 , 0.996));

                    hBox.getChildren().add(textFlow);
                    vbox_message.getChildren().add(hBox);
                    server.sendMessageToClient(messageToSend);
                    tf_message.clear();
                }
            }
        });
    }

    // Receiving
    public static  void addLabel(String messageFromServer , VBox vbox){

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(messageFromServer);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle(
                "-fx-background-color: rgb(233, 233, 235);" + // light gray bubble
                        "-fx-background-radius: 16px;" +
                        "-fx-padding: 10px 14px;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-family: 'Segoe UI', 'Arial', sans-serif;" +
                        "-fx-text-fill: black;"
        );

        textFlow.setPadding(new Insets(5 ,10 , 5 , 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);
            }
        });
    }
    public void reset(ActionEvent event){
        vbox_message.getChildren().clear();
        RotateTransition rotate = new RotateTransition(Duration.millis(500), reset_icon); // assuming `refresh_icon` is your FontAwesome icon
        rotate.setByAngle(360);  // rotate 360 degrees
        rotate.setCycleCount(1);  // one full rotation
        rotate.setInterpolator(Interpolator.LINEAR); // smooth interpolation
        rotate.play();
    }
}