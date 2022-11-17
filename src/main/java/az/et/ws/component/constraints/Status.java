package az.et.ws.component.constraints;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Status {

    INTERNAL_ERROR(1000, "standard.internal-error"),
    SUCCESS(1001, "standard.successful-operation"),
    FETCH(1002, "standard.fetch-operation"),
    CREATE(1003, "standard.create-operation"),
    UPDATE(1004, "standard.update-operation"),
    DELETE(1005, "standard.delete-operation"),
    VALIDATION_ERROR(1006, "standard.validation-error"),
    DATA_NOT_FOUND(1007, "standard.data-not-found"),
    WRONG_USERNAME_OR_PASSWORD(1008, "standard.wrong-username-or-password"),
    BAD_LOGIN_REQUEST(1009, "standard.bad-login-request"),
    ACCESS_DENIED(1009, "standard.access-denied"),
    INVALID_TOKEN(1010, "standard.invalid-token"),
    EXPIRED_TOKEN(1011, "standard.expired-token"),
    USER_ALREADY_EXISTS(1012, "standard.user-already-exists"),
    WRONG_AUTH_PROVIDER(1013, "standard.wrong-auth-provider"),

    EVENT_NOT_ACCEPTABLE(1013, "standard.event-not-acceptable"),
    WRONG_OTP_CODE(1014, "standard.wrong-otp-code"),
    WRONG_ENUM_TYPE(1015, "standard.wrong-enum-type"),
    UNDEFINED_FILE_TYPE(1016, "standard.undefined-file-type"),
    UNKNOWN_ERROR(2000, "standard.unknown-error");

    private final int code;
    private final String message;

}
