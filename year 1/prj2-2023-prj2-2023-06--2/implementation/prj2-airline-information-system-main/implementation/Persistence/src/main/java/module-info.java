module persistence_module { 
    requires datarecords_module;
    requires java.sql;
    requires java.desktop;
    requires javafx.base;
    requires java.naming;
    requires org.postgresql.jdbc;
    exports persistence;
    exports persistence.exceptions;
}
