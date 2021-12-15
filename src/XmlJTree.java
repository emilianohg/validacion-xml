import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.text.MessageFormat;

class XmlJTree extends JTree {

    DefaultTreeModel dtModel = null;

    public XmlJTree(File file) {

        final Font currentFont = getFont();
        final Font bigFont = new Font(
            currentFont.getName(),
            currentFont.getStyle(),
            currentFont.getSize() + 10
        );
        setFont(bigFont);

        if (file != null)
            setFile(file);
    }

    public void setFile(File filePath) {
        Node root = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(filePath);
            root = (Node) doc.getDocumentElement();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                null,
                "Can't parse file",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        if (root != null) {
            dtModel = new DefaultTreeModel(builtTreeNode(root));
            this.setModel(dtModel);
        }
    }

    private DefaultMutableTreeNode builtTreeNode(Node root) {
        DefaultMutableTreeNode dmtNode;

        dmtNode = new DefaultMutableTreeNode(root.getNodeName());
        if (root.getChildNodes().getLength() == 1) {
            dmtNode = new DefaultMutableTreeNode(
                    MessageFormat.format("{0}: {1}",
                    root.getNodeName(),
                    root.getTextContent()
                )
            );
        }

        NodeList nodeList = root.getChildNodes();
        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);

            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (tempNode.hasChildNodes()) {
                    dmtNode.add(builtTreeNode(tempNode));
                }
            }
        }
        return dmtNode;
    }


    @Override
    public TreeCellRenderer getCellRenderer() {
        return super.getCellRenderer();
    }
}
