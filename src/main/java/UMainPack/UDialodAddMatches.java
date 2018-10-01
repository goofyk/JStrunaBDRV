package UMainPack;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class UDialodAddMatches extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable tableMatches;
    private JButton addButton;

    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static int sizeWidth = 800;
    private static int sizeHeight = 600;
    private static int locationX = (screenSize.width - sizeWidth) / 2;
    private static int locationY = (screenSize.height - sizeHeight) / 2;


    public static void main(String[] args) {
        new UDialodAddMatches();
    }

    public UDialodAddMatches() {

        setBounds(locationX, locationY, this.getWidth(), this.getHeight());
        setContentPane(contentPane);
        setModal(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        tableMatches.setModel(USqlite.getBuildTableModel(USqlite.getAllDataMatches()));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewRow();
            }
        });
    }

    private void addNewRow() {
        DefaultTableModel model = (DefaultTableModel) tableMatches.getModel();
        model.addRow(new Object[]{});
    }

    private void onOK() {
        USqlite.cleareTable("MatchStrunaBDRV");
        USqlite.insertDataToDBFromTable((TableModel) tableMatches.getModel());
        this.setVisible(false);
    }

    private void onCancel() {
        dispose();
    }
}
