module com.example.progettoisw {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jetbrains.annotations;


    opens com.example.progettoisw to javafx.fxml;
    exports com.example.progettoisw;
    exports com.example.progettoisw.view.auth;
    opens com.example.progettoisw.view.auth to javafx.fxml;
    exports com.example.progettoisw.view.operator;
    opens com.example.progettoisw.view.operator to javafx.fxml;
    exports com.example.progettoisw.view.mainmenu;
    opens com.example.progettoisw.view.mainmenu to javafx.fxml;
}