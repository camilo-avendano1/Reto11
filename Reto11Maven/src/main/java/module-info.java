module com.mycompany.reto11maven {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.reto11maven to javafx.fxml;
    exports com.mycompany.reto11maven;
}
