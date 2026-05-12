module com.example.memlibrary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.memlibrary to javafx.fxml;
    exports com.example.memlibrary;
}