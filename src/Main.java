import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setValidating(true);

        try {
            DocumentBuilder builder = domFactory.newDocumentBuilder();

            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void error(SAXParseException exception) throws SAXException {
                    // Si no cumple lo definido en el dtd
                    System.out.println("Error 1");
                    exception.printStackTrace();
                }
                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    // Si no esta formateado correctamente el xml
                    System.out.println("Error 2");
                    exception.printStackTrace();
                }

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    System.out.println("Error 3");
                    exception.printStackTrace();
                }
            });

            Document doc = builder.parse("Note.xml");

            NodeList node = doc.getElementsByTagName("to");
            System.out.println(node.item(0).getNodeName());
            System.out.println(node.item(0).getTextContent());

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

    }
}
