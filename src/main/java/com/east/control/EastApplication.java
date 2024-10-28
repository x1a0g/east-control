package com.east.control;

import com.east.control.controller.ConnectController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class EastApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EastApplication.class.getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 860, 500);

        //获取其他控制器
        ConnectController connectController = fxmlLoader.getController();

        stage.setTitle("east-control");
        stage.setScene(scene);

        connectController.setStage(stage);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}