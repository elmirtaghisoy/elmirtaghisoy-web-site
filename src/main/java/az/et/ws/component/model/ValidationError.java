package az.et.ws.component.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationError {

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
