package UMainPack;

import org.apache.log4j.PropertyConfigurator;

import javax.swing.*;

public class UMain {

    public static JFrame mainForm = new UFormMain();

    public static void main(String[] args) {
        PropertyConfigurator.configure("ULogger.properties");
        UProperties.loadProperties();
        mainForm.setVisible(true);
    }

}
