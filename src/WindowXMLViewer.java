import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class WindowXMLViewer extends JFrame {

    JTree jTree;

    boolean isFirstElement = true;


    public WindowXMLViewer(Document doc) {
        jTree = new JTree();
        procesarDOM(doc);
        add(jTree);

        setTitle("Procesar XML");
        setResizable(false);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public void procesarDOM(Node nodo) {
        switch (nodo.getNodeType()) {
            case Node.DOCUMENT_NODE:
                System.out.println("<%xml version=\"1.0\">");
                Document doc = (Document) nodo;
                procesarDOM(doc.getDocumentElement());
                break;
            case Node.ELEMENT_NODE:

                String nombre = nodo.getNodeName();
                DefaultMutableTreeNode xmlElement = new DefaultMutableTreeNode(nombre);
                System.out.println("<"+nombre+">");

                if (isFirstElement) {
                    this.isFirstElement = false;
                    this.jTree.add(xmlElement);
                }

                if(nodo.getChildNodes() != null) {
                    for(int i=0 ; i < nodo.getChildNodes().getLength() ; i++) {
                        for(int j=0 ; j < nodo.getAttributes().getLength() ; j++) {
                            procesarDOM(nodo.getAttributes().item(j));
                        }
                        Node childNode = nodo.getChildNodes().item(i);
                        DefaultMutableTreeNode xmlChildElement = new DefaultMutableTreeNode(nombre);
                        xmlElement.add(xmlChildElement);
                        procesarDOM(childNode);
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
