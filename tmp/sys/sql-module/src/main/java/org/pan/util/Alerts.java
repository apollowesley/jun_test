package org.pan.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Created by Administrator on 2016/4/30.
 */
public class Alerts {

    private Alert alert;

    public Alerts(Alert.AlertType alertType) {
        alert = new Alert(alertType);
    }

    public Alerts setTitle(String title) {
        alert.setTitle(title);
        return this;
    }

    public static Alerts create(Alert.AlertType alertType) {
        return new Alerts(alertType);
    }

    public Alerts setHeaderText(String headerText) {
        alert.setHeaderText(headerText);
        return this;
    }

    public void show() {
        alert.show();
    }

    Optional<ButtonType> showAndWait() {
        return alert.showAndWait();
    }
}
