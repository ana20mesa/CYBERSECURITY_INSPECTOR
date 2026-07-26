package com.unad.cybersecurityinspector;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader iniciarVista = new FXMLLoader(Application.class.getResource("inicio.fxml"));
        Scene scene = new Scene(iniciarVista.load());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        //stage.setMaximized(true); acomoda al tamano de la pntalla
        stage.show();
    }





}
