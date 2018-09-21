package UMainPack;

import org.apache.log4j.PropertyConfigurator;
import net.sf.launch4j.*;

public class UMain {

    public static void main(String[] args) {

        PropertyConfigurator.configure("ULogger.properties");
        UProperties.loadProperties();
        UFormMain objUForm = new UFormMain();
        objUForm.setVisible(true);

    }

}
