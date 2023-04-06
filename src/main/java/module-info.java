module com.example.fazekas_tamas_stopper {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fazekas_tamas_stopper to javafx.fxml;
    exports com.example.fazekas_tamas_stopper;
}