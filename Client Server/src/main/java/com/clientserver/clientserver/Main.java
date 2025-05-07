package com.clientserver.clientserver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("server.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 560, 445);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("imgs/messenger.png"))));
        stage.setTitle("Server");
        stage.setX(20); // X position on screen (left side)
        stage.setY(100); // Y position on screen (top)
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}