package t;

import java.io.*;
import java.util.Properties;

public class TestProp {

    public static void main(String[] args) {


        //Set up the properties
        String filePath = "prop.properties";

        //Load the properties file
        Properties properties = new Properties();
        try {
            OutputStream outputStream = new FileOutputStream(filePath);
            properties.store(outputStream,"test");
        } catch (IOException e1) {
            e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("Properties file was saved successfully.");


//    catch(
//    IOException e
//    )
//
//    {
//        System.out.println("Properties file could not be loaded. Exception : " + e.getMessage());
//    }
//    catch(NullPointerException ex){
//        System.out.println("Null Pointer exception occurred when attempting to load Properties file. Exception : " + ex.getMessage());
//    }
    }
}
