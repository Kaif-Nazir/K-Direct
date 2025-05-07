package com.clientmessenger.clientmessenger;

import com.clientmessenger.clientmessenger.Client.Client;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class clientController implements Initializable {


    public Button button_send;
    public TextField tf_message;
    public ScrollPane sp_main;
    public VBox vbox_message;
    public Button button_reset;
    public FontAwesomeIconView reset_icon;
    private Client client;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_reset.setOnAction(this::reset);

        try{
            client = new Client(new Socket("localhost" , 1234));
        } catch (IOException e) {
            e.printStackTrace();
        }

        vbox_message.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldVal, Number newVal) {
                sp_main.setVvalue((Double) newVal);
            }
        });
        client.recieveMessageFromServer(vbox_message);

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
                            "-fx-color : rgb(239 , 242, 255);"+
                            "-fx-background-color: rgb(15,125,242);" +
                            "-fx-background-radius: 20px;" +
                            "-fx-padding: 8px;"
                    );

                    textFlow.setPadding(new Insets(5 ,10 ,5 ,10));
                    text.setFill(Color.color(0.934 , 0.945 , 0.996));

                    hBox.getChildren().add(textFlow);
                    vbox_message.getChildren().add(hBox);
                    client.sendMessageToServer(messageToSend);
                    tf_message.clear();
                }
            }
        });
    }
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