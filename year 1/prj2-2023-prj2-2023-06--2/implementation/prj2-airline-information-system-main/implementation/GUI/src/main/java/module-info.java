module gui_module {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.base;
    
    requires businesslogic_module;
    requires datarecords_module;
    requires persistence_module;
    requires java.sql;
    requires java.desktop;

    opens gui to javafx.fxml;
    
    exports gui;
    exports gui.booking;
    opens gui.booking to javafx.fxml;
}
