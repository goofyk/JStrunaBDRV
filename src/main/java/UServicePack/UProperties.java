package UServicePack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class UProperties {

    static Properties properties = new Properties();
    static File file = new File("properties.properties");

    UProperties(){
    }

    public static void main(String[] args) {
        loadProperties();
    }

    static boolean loadProperties(){

        if(!createFileProperties()) return false;

        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static boolean createFileProperties(){
        if(!file.exists() || file.isDirectory()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    static void initPropeties(){}

    public static String getPropety(String name){
        return properties.getProperty(name) == null ? "" : properties.getProperty(name);
    }

    static boolean setPropety(String name, String value){
        try {
            properties.setProperty(name, value);
            properties.store(new FileOutputStream(file), null);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getPropety1(String name){
        return getPropety(name);
    }

}
