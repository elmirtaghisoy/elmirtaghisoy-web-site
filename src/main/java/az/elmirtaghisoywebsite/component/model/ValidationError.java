package az.elmirtaghisoywebsite.component.model;

import java.io.Serializable;

public class ValidationError implements Serializable {

    public ValidationError() {
    }

    public ValidationError(String message) {
        this.message = message;
    }

    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }


    private String field;
    private String message;

    @Override
    public String toString() {
        return "Error{" +
                "field='" + field + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
