module via.dk {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.rmi;
    requires remoteobserver;
    requires java.desktop;
    requires java.naming;


    exports via.dk.cueandbrew;
    exports via.dk.cueandbrew.server;
    exports via.dk.cueandbrew.shared;
    exports via.dk.cueandbrew.model;
    exports via.dk.cueandbrew.view;
    exports via.dk.cueandbrew.viewmodel;
    exports via.dk.cueandbrew.viewmodel.Reservation;
    exports via.dk.cueandbrew.viewmodel.Start;
    exports via.dk.cueandbrew.viewmodel.MainPages;

    opens via.dk.cueandbrew.view.MainPages to javafx.fxml, org.junit.jupiter.api;
    opens via.dk.cueandbrew.view.Reservation to javafx.fxml, org.junit.jupiter.api;
    opens via.dk.cueandbrew.view.Start to javafx.fxml, org.junit.jupiter.api;
    opens via.dk.cueandbrew.view to javafx.fxml, org.junit.jupiter.api;
    opens via.dk.cueandbrew.viewmodel.Reservation to org.junit.jupiter.api;
    opens via.dk.cueandbrew to javafx.fxml, org.junit.jupiter.api;
}