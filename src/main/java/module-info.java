module com.user.education_blockchain {

    // Required to use JavaFX UI components (Button, Label, TextField, etc.)
    requires javafx.controls;
    // Required to load JavaFX FXML files (FXML UI designs)
    requires javafx.fxml;
    // ControlsFX provides advanced UI components (e.g., notifications, validation fields)
    requires org.controlsfx.controls;
    // FormsFX is a library that provides advanced form components
    requires com.dlsc.formsfx;
    // ValidatorFX is used for form validation
    requires net.synedra.validatorfx;
    // Icon library allows usage of various icons in the UI
    requires org.kordamp.ikonli.javafx;
    // BootstrapFX integrates Bootstrap-like CSS styles into JavaFX
    requires org.kordamp.bootstrapfx.core;
    // Lombok is used at compile-time, so it's declared as "static"
    requires static lombok;
    // Required to perform SQL operations in Java (Connection, Statement, ResultSet, etc.)
    requires java.sql;

    // requires commons.codec;

    requires com.google.gson;
    requires org.apache.commons.codec;

    // The `opens` directive allows a package to be accessed via reflection at runtime
    opens com.user.education_blockchain to javafx.fxml;
    // Controller classes must be open for FXML to access them
    opens com.user.education_blockchain.controller to javafx.fxml;
    // DTO (Data Transfer Object) package should be accessible to JavaFX and Lombok
    opens com.user.education_blockchain.dto to javafx.base, lombok;
    // DAO (Data Access Object) classes use SQL, so they must be opened
    opens com.user.education_blockchain.dao to java.sql;
    // Database connection classes must also be open to the SQL module
    opens com.user.education_blockchain.database to java.sql;

    // #######################################################################################

    // Exporting the blockchain package so it can be accessed from other classes like controllers
    exports com.user.education_blockchain.blockchain;
    // Exporting the main package so its content can be used by other modules
    exports com.user.education_blockchain;
    // Exporting DAO classes so other modules can perform database operations
    exports com.user.education_blockchain.dao;
    // Exporting the database connection package so it can be accessed externally
    exports com.user.education_blockchain.database;
}
