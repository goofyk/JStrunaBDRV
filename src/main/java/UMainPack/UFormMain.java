package UMainPack;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import javax.swing.*;

public class UFormMain extends JFrame {

    private Dimension dimSizeBtn = new Dimension(200, 30);
    private JButton button = createButton("Info", dimSizeBtn);
    private JButton buttonStartService = createButton("Start", dimSizeBtn);
    private JButton buttonStopService = createButton("Stop", dimSizeBtn);
    private JButton buttonRestartService = createButton("Restart", dimSizeBtn);
    private JButton buttonCreateService = createButton("Create service", dimSizeBtn);
    private JButton buttonDeleteService = createButton("Delete service", dimSizeBtn);
    private JButton buttonUpdateTable = createButton("Update table", dimSizeBtn);
    private JButton buttonRunInit = createButton("Run init", dimSizeBtn);
    private JButton buttonSettings = createButton("Settings", dimSizeBtn);
    private JTable table = new JTable(USqlite.buildTableModel(USqlite.getAllDataLogs(0)));
    Timer timer = new Timer(10000, new actionListenerTimer());
    private String nameOfService = "StrunaBDRV";
    private String pathRoot = System.getProperty("user.dir");
    private String pathToFileOfService = pathRoot + "\\Service.exe";

    public UFormMain() {

        super("Struna - BDRV");
        this.setBounds(100,100,500,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();

        button.addActionListener(new ButtonEventListener());
        buttonStartService.addActionListener(new ButtonEventListenerStartService());
        buttonStopService.addActionListener(new ButtonEventListenerStopService());
        buttonRestartService.addActionListener(new ButtonEventListenerRestartService());
        buttonCreateService.addActionListener(new ButtonEventListenerCreateService());
        buttonDeleteService.addActionListener(new ButtonEventListenerDeleteService());
        buttonUpdateTable.addActionListener(new ButtonEventListenerUpdateTable());
        buttonRunInit.addActionListener(new ButtonEventListenerRunInit());
        buttonSettings.addActionListener(new ButtonEventListenerSettings());

        JPanel panelBtn = new JPanel();
        panelBtn.setLayout(new BoxLayout(panelBtn, BoxLayout.Y_AXIS));

        panelBtn.add(button);
        panelBtn.add(buttonStartService);
        panelBtn.add(buttonStopService);
        panelBtn.add(buttonRestartService);
        panelBtn.add(buttonCreateService);
        panelBtn.add(buttonDeleteService);
        panelBtn.add(buttonUpdateTable);
        panelBtn.add(buttonRunInit);
        panelBtn.add(buttonSettings);

        JTextArea textArea = new JTextArea();
        textArea.setWrapStyleWord(true);

        JScrollPane jscrlp = new JScrollPane(table);

        container.add(BorderLayout.WEST, panelBtn);
//        container.add(BorderLayout.CENTER, textArea);
        container.add(BorderLayout.CENTER, jscrlp);

        timer.start();

    }

    private JButton createButton(String text, Dimension size) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        return button;
    }

    private void updateTableLogs(int countTop){
        table.setModel(USqlite.buildTableModel(USqlite.getAllDataLogs(0)));
    }

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String message = pathRoot;
//            message += "Button was pressed\n";
//            message += "Text is " + input.getText() + "\n";
//            message += (radio1.isSelected()?"Radio #1":"Radio #2")
//                    + " is selected\n";
//            message += "CheckBox is " + ((check.isSelected())
//                    ?"checked":"unchecked");
            JOptionPane.showMessageDialog(null,
                    message,
                    "Output",
                    JOptionPane.PLAIN_MESSAGE);
        }
    }

    class actionListenerTimer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateTableLogs(200);
        }
    }

    class ButtonEventListenerStartService implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                updateNameService();
                String command = pathRoot + "\\nssm\\win64\\nssm.exe start " + nameOfService;
                Runtime.getRuntime().exec(command);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    class ButtonEventListenerStopService implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                updateNameService();
                String command = pathRoot + "\\nssm\\win64\\nssm.exe stop " + nameOfService;
                Runtime.getRuntime().exec(command);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    class ButtonEventListenerRestartService implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                updateNameService();
                String command = pathRoot + "\\nssm\\win64\\nssm.exe restart " + nameOfService;
                Runtime.getRuntime().exec(command);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    class ButtonEventListenerCreateService implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                updateNameService();
                String command = pathRoot + "\\nssm\\win64\\nssm.exe install " + nameOfService + " " + pathToFileOfService;
                Runtime.getRuntime().exec(command);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    class ButtonEventListenerDeleteService implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                updateNameService();
                String command = pathRoot + "\\nssm\\win64\\nssm.exe remove " + nameOfService;
                Runtime.getRuntime().exec(command);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    class ButtonEventListenerUpdateTable implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            updateTableLogs(1);
        }
    }

    class ButtonEventListenerRunInit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
//            initProgramm();
            try {
                updateNameService();
                //String command = System.getProperty("user.dir") + "\\nssm\\win64\\nssm.exe status " + nameOfService;
                String command = pathRoot + "\\nssm\\win64\\nssm.exe status MySQL57";
                Process proc = Runtime.getRuntime().exec(command);
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
                String messageInput = "";
                String messageError = "";
                String s = null;
                while ((s = stdInput.readLine()) != null) { messageInput += s + "\n"; }
                while ((s = stdError.readLine()) != null) { messageError += s + "\n"; }
                if(!messageInput.isEmpty())inform(messageInput, null);
                if(!messageError.isEmpty())inform(messageError, "Error");
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
    class ButtonEventListenerSettings implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JDialogTest dialog = new JDialogTest();
            dialog.pack();
            dialog.setModal(true);
            dialog.setVisible(true);
        }
    }

    private void initProgramm() {
    }

    private void updateNameService(){
        if(!UProperties.getProperty("NameService").isEmpty()) nameOfService = UProperties.getProperty("NameService");
    }

    private void inform(String message, String title){
        if(title == null) title = "Output";
        JOptionPane.showMessageDialog(null,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE);
    }

}