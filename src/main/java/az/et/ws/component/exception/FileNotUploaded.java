package az.et.ws.component.exception;

public class FileNotUploaded extends RuntimeException {
    public FileNotUploaded(String fileName) {
        super(fileName);
    }
}
