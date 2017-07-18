package GitHubDemoTest;

        import junit.framework.Assert;

        import org.apache.log4j.Logger;
        import org.apache.log4j.PropertyConfigurator;
        import org.junit.After;
        import org.junit.Before;
        import org.junit.BeforeClass;
        import org.junit.Test;
        import org.openqa.selenium.remote.DesiredCapabilities;

        import java.io.File;
        import java.io.IOException;
        import java.net.URL;
        import java.util.List;
        import java.util.concurrent.TimeUnit;

        import io.appium.java_client.TouchAction;
        import io.appium.java_client.android.AndroidDriver;
        import io.appium.java_client.android.AndroidElement;
        import io.appium.java_client.android.AndroidKeyCode;
        import io.appium.java_client.remote.MobileCapabilityType;
        import io.appium.java_client.remote.MobilePlatform;

public class Setting {

    public AndroidDriver<AndroidElement> mDriver;
    public Logger lg = Logger.getLogger("accessibility");

    // will be run only once before run class
    @BeforeClass
    public static void log4j() {
        File f = new File("app/src");
        File fs = new File(f, "Log4J_Properties file.txt");
        // @BeforeClass: Absolut path - methode getAbsolutePath():
        // D:\WorkSpace_AndroidStudio\ApiDemo\app\src\Log4J_Properties file.txt
        System.out.println("@BeforeClass: Absolut path - methode getAbsolutePath():" + fs.getAbsolutePath());

        // @BeforeClass: rel. path - methode getpath():
        // app\src\Log4J_Properties file.txt
        System.out.println("@BeforeClass: rel. path - methode getpath():" + fs.getPath());

        PropertyConfigurator.configure(fs.getPath());  // Path to property file "Log4J_Properties file.txt"
    }

    // run before every @Test Method
    @Before
    public void setUp() throws IOException {

        Runtime runtime = Runtime.getRuntime();
        try {
            // See the server documentation for all the command line arguments: http://appium.io/slate/en/master/?java#server-args.md
            runtime.exec("cmd.exe /c start cmd.exe /k \"appium -a 127.0.0.1 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"false\"\"}\"\"");


            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        File f = new File("app/src");
        File fs = new File(f, "ApiDemos-debug.apk");

        // Absolut path - methode getAbsolutePath(): D:\WorkSpace_AndroidStudio\ApiDemo\app\src\ApiDemos-debug.apk
        System.out.println("@Before: Absolut path - methode getAbsolutePath():" + fs.getAbsolutePath());

        // rel. path - methode getpath(): app\src\ApiDemos-debug.apk
        System.out.println("@Before: rel. path - methode getpath():" + fs.getPath());

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Device");
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        cap.setCapability(MobileCapabilityType.APP, fs);
        cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "25");

        mDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
        mDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }




    // ApiDemoAccessibilityQueryBack Test via Android Setting using Homepage Icon
    // ----------------------------------------------------------
    @Test
    public void accessibilityAndroidSetting() throws InterruptedException {
        lg.info("@Test(accessibilityAndroidSetting) Methode entered");
        lg.info("ApiDemoAccessibilityQueryBack Test via Android Setting using Homepage Icon");
        // 1. Enable QueryBack (Settings -> ApiDemoAccessibilityQueryBack -> QueryBack).

        // for Key code see : https://developer.android.com/reference/android/view/KeyEvent.html

        // This was only a Test on my Private Handy
        // tap on HOME Button in APP API-DEMO
        mDriver.pressKeyCode(AndroidKeyCode.HOME);

        // tap on Folder "Privat"
        mDriver.findElementByXPath("//android.widget.FrameLayout[@content-desc='Folder: Privat']").click();


        mDriver.findElementsByClassName("android.widget.TextView").get(0).click();


        mDriver.findElementById("com.android.settings:id/search").click();


        mDriver.findElementById("android:id/search_src_text").sendKeys("Accessibility");
        lg.info("Search in serach Result list for text \"Accessibility\" and after find it click on it");

        // Search in serach Result list for text "Accessibility" and after find it click on it
        List<AndroidElement> searchResult = mDriver.findElementsById("com.android.settings:id/title");
        TouchAction t = new TouchAction(mDriver);
        for (int i = 0; i < searchResult.size(); i++) {
            if (searchResult.get(i).getText().contentEquals("Accessibility")) {
                t.tap(searchResult.get(i)).perform();  // Click on Serach Result "Accessibility"
                Thread.sleep(3000);  // Wait till Popup opened
                break;
            }
        }


        // Check in the header after tap on Accesibility in Search Result
        // the header also should be "Accessibility"
        String s0 = mDriver.findElementsByClassName("android.widget.TextView").get(0).getText();
        System.out.println(s0);
        String s1 = mDriver.findElementsByClassName("android.widget.TextView").get(1).getText();
        System.out.println(s1);
        String s2 = mDriver.findElementsByClassName("android.widget.TextView").get(2).getText();
        System.out.println(s2);
        String s3 = mDriver.findElementsByClassName("android.widget.TextView").get(3).getText();
        System.out.println(s3);
        String s4 = mDriver.findElementsByClassName("android.widget.TextView").get(4).getText();
        System.out.println(s4);
        String s5 = mDriver.findElementsByClassName("android.widget.TextView").get(5).getText();
        System.out.println(s5);


        Assert.assertEquals("Accessibility",s0);
    }

    @After
    public void stopServer() {
        Runtime runtime = Runtime.getRuntime();
        try {
            mDriver.closeApp();
            lg.info("App is closed");

            runtime.exec("taskkill /F /IM node.exe");
            runtime.exec("taskkill /F /IM cmd.exe");
            lg.info("Driver task is killed");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
