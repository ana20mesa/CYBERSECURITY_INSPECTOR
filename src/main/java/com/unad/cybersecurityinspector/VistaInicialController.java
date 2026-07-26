package com.unad.cybersecurityinspector;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class VistaInicialController {

    @FXML
    public BorderPane vistaPrincipal;

    @FXML
    private AnchorPane centroInicio;

    @FXML
    private void cerrarAplicacion() {
        Platform.exit();
    }

    @FXML
    private void abrirAplicacion1() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("aplicacion1.fxml"));
        Parent vista = cargar.load();
        vistaPrincipal.setCenter(vista);
    }

    @FXML
    private void abrirAplicacion2() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("aplicacion2.fxml"));
        Parent vista = cargar.load();
        vistaPrincipal.setCenter(vista);
    }

    @FXML
    private void abrirAplicacion3() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("aplicacion3.fxml"));
        Parent vista = cargar.load();
        vistaPrincipal.setCenter(vista);
    }

    @FXML
    private void abrirAplicacion4() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("aplicacion4.fxml"));
        Parent vista = cargar.load();
        vistaPrincipal.setCenter(vista);

    }

    @FXML
    private void abrirInfo1() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("info1.fxml"));
        Parent vista = cargar.load();
        vistaPrincipal.setCenter(vista);
    }

    @FXML
    private void abrirInfo2() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("info2.fxml"));
        Parent vista = cargar.load();
        vistaPrincipal.setCenter(vista);
    }

    @FXML
    private void abrirInfo3() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("info3.fxml"));
        Parent vista = cargar.load();
        vistaPrincipal.setCenter(vista);
    }

    @FXML
    private void abrirInfo4() throws IOException {

        FXMLLoader cargar = new FXMLLoader(getClass().getResource("info4.fxml"));
        Parent vista = cargar.load();
        vistaPrincipal.setCenter(vista);
    }

    @FXML
    public void cerrarVentana() {
        vistaPrincipal.setCenter(centroInicio);
    }


}
