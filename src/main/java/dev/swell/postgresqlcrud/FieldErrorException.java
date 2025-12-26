package dev.swell.postgresqlcrud;

import javafx.scene.layout.HBox;

public class FieldErrorException extends RuntimeException {

    private final HBox container;

    public FieldErrorException(HBox container, String message) {
        super(message);
        this.container = container;
    }

    public HBox getContainer() {
        return container;
    }
}
