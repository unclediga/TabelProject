import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 *
 */
public class Test {
    public static void main(String[] args) {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("tabel.property"));
        } catch (IOException e) {
            System.err.print("Can't read property file");
            return;
        }
        System.out.println(prop.getProperty("db.connect.string"));
        System.out.println(prop.getProperty("db.connect.user"));
        System.out.println(prop.getProperty("db.connect.password"));


    }

}
