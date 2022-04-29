module com.thomasparsley.task09 {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.thomasparsley.task09 to javafx.fxml;
    exports com.thomasparsley.task09;
}
