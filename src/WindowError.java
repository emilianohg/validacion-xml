import javax.swing.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.util.List;

public class WindowError extends JFrame {

    private List<ErrorXML> errors;

    public WindowError(List<ErrorXML> errors) {
        //array bidimencional de objetos con los datos de la tabla
        Object[][] data = new Object[errors.size()][3];

        for (int i = 0; i < errors.size(); i++) {
            ErrorXML errorXML = errors.get(i);
            data[i] = new Object[]{
                    errorXML.getMessage(),
                    errorXML.getLine(),
                    errorXML.getColumn(),
            };
        }

        String[] columnNames = {"Message", "Line", "Column"};

        final JTable table = new JTable(data, columnNames);

        table.getColumnModel().getColumn(0).setPreferredWidth(599);
        table.getColumnModel().getColumn(1).setPreferredWidth(50);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);

        table.setPreferredScrollableViewportSize(new Dimension(700, 200));

        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().add(scrollPane, BorderLayout.CENTER);

        setSize(700, 200);
        setLocationRelativeTo(null);

        setVisible(true);
    }
}
