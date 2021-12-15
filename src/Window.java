import org.w3c.dom.Document;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.text.MessageFormat;
import java.util.Optional;

public class Window extends JFrame {

    JButton btnFileChooserXML;
    JButton btnValidate;

    Optional<File> fileXML;

    XMLValidator validator;

    public Window() {
        setTitle("Procesar XML");
        setResizable(false);
        setSize(400, 100);
        setLocationRelativeTo(null);

        setLayout(new FlowLayout(FlowLayout.LEADING, 20, 0));

        btnFileChooserXML = new JButton("Selecciona XML");
        btnFileChooserXML.addActionListener(event -> this.openXML());

        btnValidate = new JButton("Validar");
        btnValidate.addActionListener(event -> this.validateSchema());


        JPanel panelFileChooser = new JPanel();
        panelFileChooser.setLayout(new BorderLayout());

        panelFileChooser.add(btnFileChooserXML, BorderLayout.WEST);
        panelFileChooser.add(btnValidate, BorderLayout.EAST);

        add(new JLabel("Cargar archivos"));
        add(panelFileChooser);

        setVisible(true);

        validator = new XMLValidator();
        this.fileXML = Optional.empty();
    }

    public void validateSchema() {

        if (this.fileXML.isEmpty()) {
            JOptionPane optionPane = new JOptionPane("Selecciona un archivo XML", JOptionPane.ERROR_MESSAGE);
            JDialog dialog = optionPane.createDialog("Failure");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            return;
        }

        Document doc = validator.validate(this.fileXML.get());

        if (doc != null) {
            new WindowXMLViewer(doc);
        }
    }

    public void openXML() {
        this.fileXML = this.openFile("XML", "Archivos .xml");
    }

    public Optional<File> openFile(String extension, String description) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(description, extension);
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println(
                    MessageFormat.format("You chose to open this file: {0}", chooser.getSelectedFile().getName())
            );
            return Optional.of(chooser.getSelectedFile());
        }
        return Optional.empty();
    }
}
