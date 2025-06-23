module com.user.education_blockchain {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.user.education_blockchain to javafx.fxml;
    exports com.user.education_blockchain;
}