import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

public class WindowXMLViewer extends JFrame {

    boolean isFirstElement = true;

    public WindowXMLViewer(File file) {
        setTitle("Visor XML");
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTree jTree = new XmlJTree(file);
        add(new JScrollPane(jTree));

        setVisible(true);
    }

}
