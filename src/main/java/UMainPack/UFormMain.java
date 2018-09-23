package UMainPack;

import javax.swing.*;
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
    private JPanel JPanelSidebarL;

    private String nameOfService = "StrunaBDRV";
    private String pathRoot = System.getProperty("user.dir");
    private String pathToFileOfService = pathRoot + "\\service\\Service.exe";

    public UFormMain() {

        super("Struna - BDRV");
        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        Container container = getContentPane();

        btnInstallService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                installService();
            }
        });
        btnRemoveService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopService();
                removeService();
            }
        });
        btnStartService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startService();
            }
        });
        btnStopService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopService();
            }
        });
        btnRestartService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartService();
            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDialogSetting();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTableLogs(0);
            }
        });

        Timer timer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTableLogs(200);
            }
        });

        timer.start();

        container.add(JPanelMain);

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
        dialogSettings.setModal(true);
        dialogSettings.setVisible(true);
    };

}
