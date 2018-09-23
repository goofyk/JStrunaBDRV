package UMainPack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class UDialogSettings extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField ServerNameBDRV;
    private JPanel panel1;
    private JPanel panel2;
    private JTextField NameDbBDRV;
    private JTextField UsernameBDRV;
    private JTextField ServerPortBDRV;
    private JPasswordField PasswordBDRV;
    private JTextField ServerNameStruna;
    private JTextField PathToDbStruna;
    private JTextField UsernameStruna;
    private JPasswordField PasswordStruna;
    private JTextField ServerPortStruna;
    private JPanel pnlBDRV;
    private JPanel pnlStruna;
    private JTextField NameService;
    private JTextField IntervalScheduler;
    private JButton createEXEButton;
    private JTextField PathToFileOfService;
    private JTextField EncodingStruna;

    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int sizeWidth = 800;
    public static int sizeHeight = 600;
    public static int locationX = (screenSize.width - sizeWidth) / 2;
    public static int locationY = (screenSize.height - sizeHeight) / 2;

    public UDialogSettings() {

        String root = System.getProperty("user.dir");
        //setLocationRelativeTo(UMain.mainForm);
        setBounds(locationX, locationY, this.getWidth(), this.getHeight());
        setContentPane(contentPane);
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(buttonOK);
        loadProperties();
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

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        createEXEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String command = "";
//                    String javaHome = System.getProperty("java.home");
//                    System.out.println(javaHome);
//                    String sysdir = System.getenv("WINDIR") + "\\system32\\";
//                    String nameOfServiceJar = "StrunaBdrvService.jar";
//                    String pathToServicePack = root + "\\src\\main\\java\\UServicePack\\";
//
//                    String command = sysdir + "javac.exe " + pathToServicePack + "UServiceTask.java";
//                    Runtime.getRuntime().exec(command);
//
//                    command = sysdir + "java.exe cvmf .\\" + pathToServicePack + nameOfServiceJar + ".\\src\\main\\java\\META-INF\\MANIFEST.MF *.class";
//                    Runtime.getRuntime().exec(command);
//
//                    makeJarFile(pathToServicePack + nameOfServiceJar);

                    command = root + "\\launch4j\\launch4j.exe " + root + "\\launch4j\\config\\config.xml";
                    Runtime.getRuntime().exec(command);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }

    private void onOK() {
        saveProperties();
        this.dispose();
    }

    private void onCancel() {
        this.dispose();
    }

    public static void main(String[] args) {
        UDialogSettings dialog = new UDialogSettings();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public void saveProperties() {

        // BDRV
        UProperties.setProperty("ServerNameBDRV", ServerNameBDRV.getText());
        UProperties.setProperty("ServerPortBDRV", ServerPortBDRV.getText());
        UProperties.setProperty("NameDbBDRV", NameDbBDRV.getText());
        UProperties.setProperty("UsernameBDRV", UsernameBDRV.getText());
        String strPasswordBDRV  = new String(PasswordBDRV.getPassword());
        if(!strPasswordBDRV.isEmpty()) UProperties.setProperty("PasswordBDRV", strPasswordBDRV);

        // Struna
        UProperties.setProperty("ServerNameStruna", ServerNameStruna.getText());
        UProperties.setProperty("ServerPortStruna", ServerPortStruna.getText());
        UProperties.setProperty("PathToDbStruna", PathToDbStruna.getText());
        UProperties.setProperty("UsernameStruna", UsernameStruna.getText());
        UProperties.setProperty("EncodingStruna", EncodingStruna.getText());
        String strPasswordStruna  = new String(PasswordStruna.getPassword());
        if(!strPasswordStruna.isEmpty()) UProperties.setProperty("PasswordStruna", strPasswordStruna);

        // Service
        UProperties.setProperty("NameService", NameService.getText());
        UProperties.setProperty("IntervalScheduler", IntervalScheduler.getText());
        UProperties.setProperty("PathToFileOfService", PathToFileOfService.getText());

    }

    public void loadProperties(){

        // BDRV
        ServerNameBDRV.setText(UProperties.getProperty("ServerNameBDRV"));
        ServerPortBDRV.setText(UProperties.getProperty("ServerPortBDRV"));
        NameDbBDRV.setText(UProperties.getProperty("NameDbBDRV"));
        UsernameBDRV.setText(UProperties.getProperty("UsernameBDRV"));

        // Struna
        ServerNameStruna.setText(UProperties.getProperty("ServerNameStruna"));
        ServerPortStruna.setText(UProperties.getProperty("ServerPortStruna"));
        PathToDbStruna.setText(UProperties.getProperty("PathToDbStruna"));
        UsernameStruna.setText(UProperties.getProperty("UsernameStruna"));
        EncodingStruna.setText(UProperties.getProperty("EncodingStruna"));

        // Service
        NameService.setText(UProperties.getProperty("NameService"));
        IntervalScheduler.setText(UProperties.getProperty("IntervalScheduler"));
        PathToFileOfService.setText(UProperties.getProperty("PathToFileOfService"));

    }

    private static void makeJarFile(String pathname){
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(new File(pathname));
            Enumeration<JarEntry> e = jarFile.entries();
            while (e.hasMoreElements()) {
                process(e.nextElement());
            }
            jarFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void process(Object obj) {
        JarEntry entry = (JarEntry) obj;
        String name = entry.getName();
        long size = entry.getSize();
        long compressedSize = entry.getCompressedSize();
        System.out.println(name + "\t" + size + "\t" + compressedSize);
    }

}
