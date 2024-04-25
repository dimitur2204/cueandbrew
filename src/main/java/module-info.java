module via.dk.cueandbrew {
    requires javafx.controls;
    requires javafx.fxml;


    opens via.dk.cueandbrew to javafx.fxml;
    exports via.dk.cueandbrew;
}