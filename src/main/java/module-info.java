module com.example.cst4_finalproject {
    requires javafx.fxml;
    requires javafx.controls;
    requires com.dlsc.formsfx;
    requires com.jfoenix;
    requires javafx.graphics;


    opens com.example.cst4_finalproject to javafx.fxml;
    exports com.example.cst4_finalproject;
}