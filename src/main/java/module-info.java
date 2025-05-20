module com.vincent.richtexteditor {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.vincent.richtexteditor to javafx.fxml;
    exports com.vincent.richtexteditor;
}