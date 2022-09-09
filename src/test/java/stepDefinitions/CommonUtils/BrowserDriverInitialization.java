package stepDefinitions.CommonUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BrowserDriverInitialization {

    String URL = "";
    //database connection details
    static Connection con = null;
    public static String DB_URL_STAGING = "jdbc:sqlserver://192.168.6.22";
    public static String DB_URL_LAUNCH = "jdbc:sqlserver://192.168.6.194\\launch";
    public static String DB_URL_TRANSFLO = "jdbc:sqlserver://192.168.6.166";
    public static String DB_URL_PROD = "jdbc:sqlserver://192.168.6.167";
    public static String DB_URL_DRIVER360 = "jdbc:sqlserver://192.168.16.51";
    public static String DB_USER = "svc_automation";
    public static String DB_PASSWORD = "svc_@u30m@tiOn";
    public static String DB_PASSWORD_PROD = "svc_@u30m@tiOnPr0d";



    public String getDataFromPropertiesFile(String environment, String browser) {

        if (environment.equals("launch") && browser.equals("chrome")) {
            URL = "http://agentlaunch.evansdelivery.com/";
            System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver105.0.5195.52.exe");
        } else if (environment.equals("launch") && browser.equals("edge")) {
            URL = "http://agentlaunch.evansdelivery.com/";
            System.setProperty("webdriver.edge.driver", "src/main/resources/drivers/msedgedriver103.exe");
        } else if (environment.equals("staging") && browser.equals("chrome")) {
            URL = "https://staging.evansdelivery.com/login.aspx";
            System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver105.0.5195.52.exe");
        }
        //  else if(environment.equals("staging") && browser.equals("edge")){
        //       URL = "https://staging.evansdelivery.com/login.aspx";
        //       System.setProperty("webdriver.edge.driver","src/main/resources/drivers/msedgedriver103.exe");
        //    }
        return URL;
    }


    public String getDataFromPropertiesFileForEBH(String environment, String browser) {

        if (environment.equals("ebhlaunch") && browser.equals("chrome")) {
            URL = "http://ebhlaunch.evansdelivery.com:8089/";
            System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver105.0.5195.52.exe");
        } else if (environment.equals("ebhlaunch") && browser.equals("edge")) {
            URL = "http://ebhlaunch.evansdelivery.com:8089/";
            System.setProperty("webdriver.edge.driver", "src/main/resources/drivers/msedgedriver103.exe");
        } else if (environment.equals("ebhprod") && browser.equals("chrome")) {
            URL = "http://ebh.evansdelivery.com/";
            System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver105.0.5195.52.exe");
        } else if (environment.equals("ebhstaging") && browser.equals("chrome")) {
            URL = "http://ebhstaging.evansdelivery.com:83/";
            System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver105.0.5195.52.exe");
        }
        return URL;
    }

    public Connection getConnectionToDatabase(String environment) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        String dbClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        Class.forName(dbClass).newInstance();
        if (environment.equals("ebhstaging")) {
            con = DriverManager.getConnection(DB_URL_STAGING, DB_USER, DB_PASSWORD);
        } else if (environment.equals("ebhlaunch")) {
            con = DriverManager.getConnection(DB_URL_LAUNCH, DB_USER, DB_PASSWORD);
        } else if (environment.equals("transflo")) {
            con = DriverManager.getConnection(DB_URL_TRANSFLO, DB_USER, DB_PASSWORD);
        } else if (environment.equals("ebhprod")) {
            con = DriverManager.getConnection(DB_URL_PROD, DB_USER, DB_PASSWORD_PROD);
        } else if (environment.equals("driver360staging")) {
            con = DriverManager.getConnection(DB_URL_DRIVER360, DB_USER, DB_PASSWORD);
        }
            return con;
    }



        public Connection getConnectionToDatabase1 (String environment1) throws
        ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
            String dbClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            Class.forName(dbClass).newInstance();
            if (environment1.equals("transflo")) {
                con = DriverManager.getConnection(DB_URL_TRANSFLO, DB_USER, DB_PASSWORD);
            } else if (environment1.equals("driver360staging")) {
                con = DriverManager.getConnection(DB_URL_DRIVER360, DB_USER, DB_PASSWORD);
            }
            return con;
        }


        public String getDataFromPropertiesFileForEquipmentConsole (String environment, String browser){

            if (environment.equals("eclaunch") && browser.equals("chrome")) {
                URL = "https://chassislaunch.evansdelivery.com";
                System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver105.0.5195.52.exe");
            } else if (environment.equals("eclaunch") && browser.equals("edge")) {
                URL = "https://chassislaunch.evansdelivery.com";
                System.setProperty("webdriver.edge.driver", "src/main/resources/drivers/msedgedriver103.exe");
            } else if (environment.equals("ecstaging") && browser.equals("chrome")) {
                URL = "https://chassisstaging.evansdelivery.com";
                System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver105.0.5195.52.exe");
            } else if (environment.equals("ecstaging") && browser.equals("edge")) {
                URL = "https://chassisstaging.evansdelivery.com";
                System.setProperty("webdriver.edge.driver", "src/main/resources/drivers/msedgedriver103.exe");
            }
            return URL;
        }


    }
