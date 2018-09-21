package UMainPack;

import net.sf.launch4j.Builder;
import net.sf.launch4j.BuilderException;
import net.sf.launch4j.Log;
import net.sf.launch4j.config.ConfigPersister;
import net.sf.launch4j.config.ConfigPersisterException;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JDialogTest extends JDialog {
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

    public JDialogTest() {
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
                    String root = System.getProperty("user.dir");
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
        savePropeties();
        this.dispose();
    }

    private void onCancel() {
        this.dispose();
    }

    public static void main(String[] args) {
        JDialogTest dialog = new JDialogTest();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public void savePropeties() {

        // BDRV
        UProperties.setPropety("ServerNameBDRV", ServerNameBDRV.getText());
        UProperties.setPropety("ServerPortBDRV", ServerPortBDRV.getText());
        UProperties.setPropety("NameDbBDRV", NameDbBDRV.getText());
        UProperties.setPropety("UsernameBDRV", UsernameBDRV.getText());
        String strPasswordBDRV  = new String(PasswordBDRV.getPassword());
        if(!strPasswordBDRV.isEmpty()) UProperties.setPropety("PasswordBDRV", strPasswordBDRV);

        // Struna
        UProperties.setPropety("ServerNameStruna", ServerNameStruna.getText());
        UProperties.setPropety("ServerPortStruna", ServerPortStruna.getText());
        UProperties.setPropety("PathToDbStruna", PathToDbStruna.getText());
        UProperties.setPropety("UsernameStruna", UsernameStruna.getText());
        String strPasswordStruna  = new String(PasswordStruna.getPassword());
        if(!strPasswordStruna.isEmpty()) UProperties.setPropety("PasswordStruna", strPasswordStruna);

        // Service
        UProperties.setPropety("NameService", NameService.getText());
        UProperties.setPropety("IntervalScheduler", IntervalScheduler.getText());

    }

    public void loadProperties(){

        // BDRV
        ServerNameBDRV.setText(UProperties.getPropety("ServerNameBDRV"));
        ServerPortBDRV.setText(UProperties.getPropety("ServerPortBDRV"));
        NameDbBDRV.setText(UProperties.getPropety("NameDbBDRV"));
        UsernameBDRV.setText(UProperties.getPropety("UsernameBDRV"));

        // Struna
        ServerNameStruna.setText(UProperties.getPropety("ServerNameStruna"));
        ServerPortStruna.setText(UProperties.getPropety("ServerPortStruna"));
        PathToDbStruna.setText(UProperties.getPropety("PathToDbStruna"));
        UsernameStruna.setText(UProperties.getPropety("UsernameStruna"));

        // Service
        NameService.setText(UProperties.getPropety("NameService"));
        IntervalScheduler.setText(UProperties.getPropety("IntervalScheduler"));

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
