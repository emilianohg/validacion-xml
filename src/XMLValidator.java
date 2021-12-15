import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class XMLValidator {

    public boolean validate(File xml) {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

        domFactory.setValidating(true);

        try {
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            List<ErrorXML> errors = new Vector<ErrorXML>();

            builder.setErrorHandler(new ErrorHandler() {
                public void warning(SAXParseException e) throws SAXException {
                    System.out.println("Warning: ");
                    addError(e);
                }
                public void error(SAXParseException e) throws SAXException {
                    System.out.println("Error: ");
                    addError(e);
                }
                public void fatalError(SAXParseException e)
                        throws SAXException {
                    System.out.println("Fattal error: ");
                    addError(e);
                }

                private void addError(SAXParseException e) {
                    errors.add(new ErrorXML(
                            e.getLineNumber(),
                            e.getColumnNumber(),
                            e.getMessage(),
                            e.getPublicId()
                        )
                    );
                }
            });

            Document doc = builder.parse(xml);
            if (errors.size() > 0) {
                new WindowError(errors);
                return false;
            }

            return true;

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        return false;
    }

}
