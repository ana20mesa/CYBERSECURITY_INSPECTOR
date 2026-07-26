package com.unad.cybersecurityinspector;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.nio.file.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FuncionalidadApli3Controller {

    @FXML
    public AnchorPane aplicacion3;
    @FXML
    private TableView<AlertaCarpetaDto> tablApli3;
    @FXML
    private TableColumn<AlertaCarpetaDto, String> colTipoC;
    @FXML
    private TableColumn<AlertaCarpetaDto, String> colNombreC;
    @FXML
    private TableColumn<AlertaCarpetaDto, String> colRutaC;
    @FXML
    private TableColumn<AlertaCarpetaDto, String> colHora;

    @FXML
    public void initialize() {
        if (!DatosGlobales.eventos.isEmpty()) {
            colTipoC.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            colNombreC.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colRutaC.setCellValueFactory(new PropertyValueFactory<>("ruta"));
            colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
            tablApli3.setItems(DatosGlobales.eventos);
        }
    }

    @FXML
    private void monitorearCarpeta() {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Seleccione una carpeta");
        File carpeta = chooser.showDialog(aplicacion3.getScene().getWindow());
        if (carpeta != null) {
            monitorear(carpeta.toPath());
        }


    }


    public void monitorear(Path carpeta) {

        new Thread(() -> {
            try {

                colTipoC.setCellValueFactory(new PropertyValueFactory<>("tipo"));
                colNombreC.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                colRutaC.setCellValueFactory(new PropertyValueFactory<>("ruta"));
                colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
                tablApli3.setItems(DatosGlobales.eventos);

                WatchService watchService = FileSystems.getDefault().newWatchService();
                carpeta.register(
                        watchService,
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY
                );
                while (true) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path archivo = (Path) event.context();
                        String tipo = "";
                        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                            tipo = "Creado";
                        }
                        if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                            tipo = "Eliminado";
                        }
                        if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                            tipo = "Modificado";
                        }
                        System.out.println(tipo + " -> " + archivo);

                        String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                        AlertaCarpetaDto evento = new AlertaCarpetaDto(
                                tipo,
                                archivo.getFileName().toString(),
                                carpeta.resolve(archivo).toString(),
                                hora);

                        Platform.runLater(() -> {
                            DatosGlobales.eventos.add(evento);
                        });

                    }
                    key.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}
