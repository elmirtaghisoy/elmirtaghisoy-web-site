package az.et.ws.component.filegenerator;

import org.springframework.core.io.ByteArrayResource;

public class CustomByteArrayResource extends ByteArrayResource {

    private final String description;

    public CustomByteArrayResource(byte[] byteArray, String description) {
        super(byteArray, description);
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
