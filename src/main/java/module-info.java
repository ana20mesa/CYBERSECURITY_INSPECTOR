module com.unad.cybersecurityinspector {
    requires javafx.controls;
    requires javafx.fxml;

//    requires com.dlsc.formsfx;
    requires org.apache.pdfbox;

    opens com.unad.cybersecurityinspector to javafx.fxml;
    exports com.unad.cybersecurityinspector;
}