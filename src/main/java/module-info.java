module com.example.cs255assignment {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.cs255assignment to javafx.fxml;
    exports com.example.cs255assignment;
}