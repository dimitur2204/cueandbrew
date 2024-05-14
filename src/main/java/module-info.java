module via.dk {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.rmi;
    requires remoteobserver;
    requires java.desktop;

    opens via.dk.cueandbrew.view to javafx.fxml;
    opens via.dk.cueandbrew to javafx.fxml;

    exports via.dk.cueandbrew;
    exports via.dk.cueandbrew.server;
    exports via.dk.cueandbrew.shared;

    opens via.dk.cueandbrew.view.MainPages to javafx.fxml;
    opens via.dk.cueandbrew.view.Reservation to javafx.fxml;
    opens via.dk.cueandbrew.view.Start to javafx.fxml;
}