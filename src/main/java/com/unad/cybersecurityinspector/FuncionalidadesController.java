package com.unad.cybersecurityinspector;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class FuncionalidadesController {

    @FXML
    public AnchorPane aplicacion1;
    @FXML
    private TableView<ArchivoRiesgosoDto> tablApli1;
    @FXML
    private TableColumn<ArchivoRiesgosoDto, String> colTipo;
    @FXML
    private TableColumn<ArchivoRiesgosoDto, String> colNombre;
    @FXML
    private TableColumn<ArchivoRiesgosoDto, String> colRuta;
    @FXML
    private TableColumn<ArchivoRiesgosoDto, String> colDescripcion;
    @FXML
    private TableColumn<ArchivoRiesgosoDto, String> colOculto;
    @FXML
    public AnchorPane aplicacion2;
    @FXML
    private TableView<AlertaPdfDto> tablApli2;
    @FXML
    private TableColumn<AlertaPdfDto, String> colTipoPDF;
    @FXML
    private TableColumn<AlertaPdfDto, String> colDescripcionPDF;
    public EscanearArchivos escaner;
    @FXML
    private Label resultado;
    @FXML
    private Label dlp;
    @FXML
    private ImageView alerta;
    @FXML
    public AnchorPane aplicacion4;
    @FXML
    private TableView<DobleExtensionDto> tablApli4;
    @FXML
    private TableColumn<DobleExtensionDto, String> colNom;
    @FXML
    private TableColumn<DobleExtensionDto, String> colRut;
    @FXML
    private TableColumn<DobleExtensionDto, String> colExtension;


    @FXML
    private void escanearCarpeta() {

        try {

            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Seleccione una carpeta");
            File carpeta = chooser.showDialog(aplicacion1.getScene().getWindow());
            if (carpeta == null) {
                return;
            }
            escaner = new EscanearArchivos();
            List<ArchivoRiesgosoDto> resultados = escaner.escanear(carpeta.toPath());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Escaneo Finalizado");
            alert.setHeaderText(null);
            alert.setContentText("Se encontraron " + resultados.size() + " archivos riesgosos.");
            alert.showAndWait();

            colTipo.setCellValueFactory(new PropertyValueFactory<>("extension"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colRuta.setCellValueFactory(new PropertyValueFactory<>("ruta"));
            colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
            colOculto.setCellValueFactory(new PropertyValueFactory<>("oculto"));

            ObservableList<ArchivoRiesgosoDto> datos = FXCollections.observableArrayList(resultados);
            tablApli1.setItems(datos);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void escanearPDF() throws IOException {
        escaner = new EscanearArchivos();
        colTipoPDF.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colDescripcionPDF.setCellValueFactory(new PropertyValueFactory<>("valor"));
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));

        File archivo = chooser.showOpenDialog(aplicacion2.getScene().getWindow());
        if (archivo != null) {
            List<AlertaPdfDto> lista = escaner.analizar(archivo);
            ObservableList<AlertaPdfDto> datos = FXCollections.observableArrayList(lista);
            tablApli2.setItems(datos);


            resultado.setText("- RESULTADO DEL ANÁLISIS\n" +
                    "\n" +
                    "- Documento: " + archivo.getName() + "\n" +
                    "\n" +
                    "- Alertas encontradas: " + lista.size() + "\n" +
                    "\n" +
                    "- Nivel de exposición: " + (lista.size() > 12 ? "ALTO" : "BAJO"));

            alerta.visibleProperty().set(true);
        }

    }


    @FXML
    private void escanearCarpetaExtension() throws IOException {

        DirectoryChooser chooser = new DirectoryChooser();
        File carpeta = chooser.showDialog(aplicacion4.getScene().getWindow());
        if (carpeta == null) {
            return;
        }
        ObservableList<DobleExtensionDto> lista = FXCollections.observableArrayList();
        Files.walk(carpeta.toPath())
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    String nombre = path.getFileName().toString();
                    if (dobleExtension(nombre)) {
                        String ultimaExtension = nombre.substring(nombre.lastIndexOf('.') + 1);
                        DobleExtensionDto archivo = new DobleExtensionDto(nombre, path.toAbsolutePath().toString(), ultimaExtension);
                        Platform.runLater(() -> lista.add(archivo));
                    }
                });
        colNom.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRut.setCellValueFactory(new PropertyValueFactory<>("ruta"));
        colExtension.setCellValueFactory(new PropertyValueFactory<>("extension"));
        tablApli4.setItems(lista);
    }

    private boolean dobleExtension(String nombre) {
        return nombre.matches(".*\\.(pdf|doc|docx|xls|xlsx|jpg|png|txt|html)\\.(exe|bat|cmd|scr|js|jar|vbs|ps1)$");
    }


}
