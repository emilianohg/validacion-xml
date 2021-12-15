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

    JLabel labelfilaName;

    public Window() {
        setTitle("Procesar XML");
        setResizable(false);
        setSize(430, 150);
        setLocationRelativeTo(null);

        Font font = new Font("Verdana", Font.BOLD, 18);

        validator = new XMLValidator();
        this.fileXML = Optional.empty();

        setLayout(new FlowLayout(FlowLayout.LEADING, 20, 0));

        btnFileChooserXML = new JButton("Selecciona XML");
        btnFileChooserXML.setFont(font);
        btnFileChooserXML.setPreferredSize(new Dimension(250, btnFileChooserXML.getHeight()));
        btnFileChooserXML.addActionListener(event -> this.openXML());

        btnValidate = new JButton("Visualizar");
        btnValidate.setFont(font);
        btnValidate.addActionListener(event -> this.validateSchema());

        labelfilaName = new JLabel("");
        labelfilaName.setFont(font);

        JPanel panelFileChooser = new JPanel();
        panelFileChooser.setLayout(new BorderLayout());

        panelFileChooser.add(btnFileChooserXML, BorderLayout.WEST);
        panelFileChooser.add(btnValidate, BorderLayout.EAST);

        JPanel panelLoadFile = new JPanel();
        JLabel labelLoad = new JLabel("Cargar archivo:");
        labelLoad.setFont(font);
        panelLoadFile.add(labelLoad);
        add(panelLoadFile, BorderLayout.NORTH);

        add(panelFileChooser, BorderLayout.CENTER);

        JPanel panelFileName = new JPanel();
        panelFileName.add(labelfilaName);
        add(panelFileName, BorderLayout.SOUTH);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void validateSchema() {

        if (this.fileXML.isEmpty()) {
            JOptionPane optionPane = new JOptionPane("Selecciona un archivo XML", JOptionPane.ERROR_MESSAGE);
            JDialog dialog = optionPane.createDialog("Failure");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
            return;
        }

        File file = this.fileXML.get();
        if (validator.validate(file)) {
            new WindowXMLViewer(file);
        }
    }

    public void openXML() {
        this.fileXML = this.openFile("XML", "Archivos .xml");
        if (this.fileXML.isPresent()) {
            labelfilaName.setText("Archivo: " + this.fileXML.get().getName());
        }
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
