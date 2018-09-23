package UMainPack;

import net.sourceforge.jdpapi.DataProtector;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class EncryptedProperties {

    private static DataProtector protector = new DataProtector();
    private static Properties properties = new Properties();
    private static Base64 base64 = new Base64();
    private static Map<String, String> keys = new HashMap<String, String>();
    private static FileWriter fw;
    private static BufferedWriter bw;
    private static FileReader fr;
    private static BufferedReader br;

    public static void main(String[] args) {
        try {
            initProp();
            setProperty("test", "test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void initProp() throws IOException {
        File file = new File("./filename.txt");
        if (!file.exists()) file.createNewFile();
        fw = new FileWriter(file.getAbsoluteFile());
        bw = new BufferedWriter(fw);
        fr = new FileReader(file.getAbsoluteFile());
        br = new BufferedReader(fr);
    }

    static void store() throws IOException {
        properties.store(bw, "");
    }

    static void load(Reader reader) throws IOException {
        properties.load(reader);
        for (String key : properties.stringPropertyNames()) {
            keys.put(decrypt(key), key);
        }
    }

    static void setProperty(String key, String value) {
        String encryptedKey = encrypt(key);
        String encryptedValue = encrypt(value);
        keys.put(key, encryptedKey);
        properties.setProperty(encryptedKey, encryptedValue);
    }

    static String getProperty(String key) {
        String encryptedKey = keys.get(key);
        String encryptedValue = properties.getProperty(encryptedKey);
        return decrypt(encryptedValue);
    }

    static String encrypt(String key) {
        byte [] data = protector.protect(key);
        return new String(base64.encode(data));
    }

    static String decrypt(String encryptedString) {
        byte [] data = base64.decode(encryptedString.getBytes());
        return protector.unprotect(data);
    }

    static {
        System.loadLibrary("jdpapi-native.dll");
        //System.out.println(System.getProperty("java.library.path"));
    }
}
