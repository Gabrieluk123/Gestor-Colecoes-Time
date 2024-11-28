module start {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens start to javafx.fxml;
    exports start;

    opens controller to javafx.fxml;
    exports controller;
    
     opens model.dao to javafx.fxml;
    exports model.dao;
    
    opens model to javafx.fxml;
    exports model;
}
