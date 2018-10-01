package UMainPack;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UFormMain extends JFrame{
    private JTable UTableLog;
    private JButton btnInstallService;
    private JButton btnRemoveService;
    private JButton btnStartService;
    private JButton btnStopService;
    private JButton btnRestartService;
    private JButton updateButton;
    private JButton settingsButton;
    private JPanel JPanelMain;
    private JPanel panelTable;
    private JButton addMatchesButton;

    private String nameOfService = "StrunaBDRV";
    private String pathRoot = System.getProperty("user.dir");
    private String pathToFileOfService = pathRoot + "\\service\\Service.exe";

    public UFormMain() {

        super("Struna - BDRV");
        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Container container = getContentPane();

        JTableHeader header = UTableLog.getTableHeader();
        panelTable.add(header, BorderLayout.NORTH);

        btnInstallService.addActionListener(e -> installService());
        btnRemoveService.addActionListener(e -> {
            stopService();
            removeService();
        });
        btnStartService.addActionListener(e -> startService());
        btnStopService.addActionListener(e -> stopService());
        btnRestartService.addActionListener(e -> restartService());
        settingsButton.addActionListener(e -> openDialogSetting());
        updateButton.addActionListener(e -> updateTableLogs(0));

        Timer timer = new Timer(10000, e -> updateTableLogs(200));

        timer.start();

        container.add(JPanelMain);

        addMatchesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDialogAddMatches();
            }
        });
    }

    /**
     *  HANDLER LISTENERS
     */

    private void installService(){
        updateNameService();
        runCommandService("install");
    }

    private void removeService(){
        updateNameService();
        runCommandService("remove");
    };

    private void startService(){
        updateNameService();
        runCommandService("start");
    };

    private void stopService(){
        updateNameService();
        runCommandService("stop");
    };

    private void restartService(){
        updateNameService();
        runCommandService("restart");
    };

    private void updateNameService(){
        if(!UProperties.getProperty("NameService").isEmpty()) nameOfService = UProperties.getProperty("NameService");
    };

    private void runCommandService(String command){
        switch (command){
            case "install":
                execCommandService(command);
                break;

            case "remove":
                execCommandService(command);
                break;

            case "start":
                execCommandService(command);
                break;

            case "stop":
                execCommandService(command);
                break;

            case "restart":
                execCommandService(command);
                break;
        }
    };

    private Process execCommandService(String command){

        String fullCommand = pathRoot + "\\nssm\\win64\\nssm.exe " + command + " " + nameOfService;
        if(command.equals("install")){ fullCommand = fullCommand + " " + pathToFileOfService;}
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(fullCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proc;
    };

    private void updateTableLogs(int countTop){

        UTableLog.setModel(USqlite.getBuildTableModel(USqlite.getAllDataLogs(0)));
    };

    private void openDialogSetting(){
        UDialogSettings dialogSettings = new UDialogSettings();
        dialogSettings.pack();
        dialogSettings.setVisible(true);
    };

    private void openDialogAddMatches(){
        UDialodAddMatches dialogAddMatches = new UDialodAddMatches();
        dialogAddMatches.pack();
        dialogAddMatches.setVisible(true);
    };

}
