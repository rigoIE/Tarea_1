module com.mycompa.tarea1_kevinrigo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    opens com.mycompa.tarea1_kevinrigo to javafx.fxml;
    exports com.mycompa.tarea1_kevinrigo;
}
