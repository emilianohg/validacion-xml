public class ErrorXML {
    private int line;
    private int column;
    private String message;
    private String systemId;
    private String publicId;

    public ErrorXML(int line, int column, String message, String systemId) {
        this(line, column, message, systemId, null);
    }

    public ErrorXML(
            int line,
            int column,
            String message,
            String systemId,
            String publicId
    ) {
        this.line = line;
        this.column = column;
        this.message = message;
        this.systemId = systemId;
        this.publicId = publicId;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getMessage() {
        return message;
    }

    public String getSystemId() {
        return systemId;
    }

    public String getPublicId() {
        return publicId;
    }
}
