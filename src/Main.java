import org.w3c.dom.Document;
import org.w3c.dom.Node;
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

            Document doc = builder.parse("Blog.xml");

            NodeList node = doc.getElementsByTagName("name");
            System.out.println(node.item(0).getNodeName());
            System.out.println(node.item(0).getTextContent());

            Node root = doc.getElementsByTagName("blog").item(0);
            procesarDOM(root);

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

    }

    // TODO: revisar y si pueden mejorarlo
    public static void procesarDOM(Node nodo) {
        switch (nodo.getNodeType()) {
            case Node.DOCUMENT_NODE:
                System.out.println("<%xml version=\"1.0\">");
                Document doc = (Document) nodo;
                procesarDOM(doc.getDocumentElement());
                break;
            case Node.ELEMENT_NODE:
                String nombre=nodo.getNodeName();
                System.out.println("<"+nombre+">");
                if(nodo.getChildNodes() != null) {
                    for(int i=0 ; i<nodo.getChildNodes().getLength() ; i++) {
                        for(int j=0 ; j< nodo.getAttributes().getLength() ; j++) {
                            procesarDOM(nodo.getAttributes().item(j));
                        }
                        procesarDOM(nodo.getChildNodes().item(i));
                    }
                }
                System.out.println("</"+nombre+">");
                break;
            case Node.ATTRIBUTE_NODE:
                System.out.println(""+nodo.getNodeName()+"=\""+nodo.getNodeValue()+"\"");

                break;
            case Node.TEXT_NODE://"\n? *\n?"
                if (!nodo.getTextContent().matches("\n? *\n?"))
                    System.out.println(nodo.getTextContent());
        }

    }
}
