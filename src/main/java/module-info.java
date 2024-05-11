module via.dk {
    requires javafx.controls;
    requires javafx.fxml;

    opens via.dk.cueandbrew.view to javafx.fxml;
    opens via.dk.cueandbrew to javafx.fxml;

    exports via.dk.cueandbrew;
}