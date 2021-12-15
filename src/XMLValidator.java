import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class XMLValidator {
    XMLValidator() {

    }

    public Document validate(File xml) {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

        domFactory.setValidating(true);

        try {
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            List<ErrorXML> errors = new Vector<ErrorXML>();

            builder.setErrorHandler(new ErrorHandler() {
                public void warning(SAXParseException e) throws SAXException {
                    System.out.println("Warning: ");
                    printInfo(e);
                }
                public void error(SAXParseException e) throws SAXException {
                    System.out.println("Error: ");
                    printInfo(e);
                }
                public void fatalError(SAXParseException e)
                        throws SAXException {
                    System.out.println("Fattal error: ");
                    printInfo(e);
                }

                private void printInfo(SAXParseException e) {
                    System.out.println("   Public ID: "+e.getPublicId());
                    System.out.println("   System ID: "+e.getSystemId());
                    System.out.println("   Line number: "+e.getLineNumber());
                    System.out.println("   Column number: "+e.getColumnNumber());
                    System.out.println("   Message: "+e.getMessage());
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
                return null;
            }

            return doc;


        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static class MyErrorHandler implements ErrorHandler {
        public void warning(SAXParseException e) throws SAXException {
            System.out.println("Warning: ");
            printInfo(e);
        }
        public void error(SAXParseException e) throws SAXException {
            System.out.println("Error: ");
            printInfo(e);
        }
        public void fatalError(SAXParseException e)
                throws SAXException {
            System.out.println("Fattal error: ");
            printInfo(e);
        }
        private void printInfo(SAXParseException e) {
            System.out.println("   Public ID: "+e.getPublicId());
            System.out.println("   System ID: "+e.getSystemId());
            System.out.println("   Line number: "+e.getLineNumber());
            System.out.println("   Column number: "+e.getColumnNumber());
            System.out.println("   Message: "+e.getMessage());
        }
    }

}