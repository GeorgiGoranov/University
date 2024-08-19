module businesslogic_module {   
    requires datarecords_module;
    requires persistence_module;
    requires java.sql;
    requires javafx.base;

    exports businesslogic;
    exports businesslogic.exceptions;
}
