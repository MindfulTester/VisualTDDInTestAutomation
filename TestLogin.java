package com.mindfultester;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
// %import com.applitools.eyes.BatchInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.URI;
import java.net.URISyntaxException;

public  class TestLogin {
    static private String  appName  = "Login App";

    // change the value of testName so that it has a unique value on your Eyes system
    static private String  testName = "Login test";

    // if you have a dedicated Eyes server, set the value of the variable serverURLstr to your URL
    static String serverURLstr = "https://eyesapi.applitools.com";

    // set the value of changeTest to true to introduce changes that Eyes will detect as mismatches
    // static private Boolean changeTest = true;

    static private String  weburl = "https://the-internet.herokuapp.com/login";

    public static void main(String[] args) {
        URI serverURL;
        try {
            serverURL = new URI(serverURLstr);
        } catch (URISyntaxException e) {
            System.out.println("URI Exception ");
            return;
        }
        Eyes eyes = new Eyes();
        eyes.setServerUrl(serverURL);
        setup(eyes);

        RectangleSize viewportSizeLandscape = new RectangleSize(/*width*/ 1200, /*height*/ 600 );

        WebDriver innerDriver = new ChromeDriver();  // Open a Chrome browser.

        // if (!changeTest) {
        test01(innerDriver, eyes, viewportSizeLandscape);
        // test01(innerDriver, eyes, viewportSizePortrait);
//        } else {
//            test01Changed(innerDriver, eyes, viewportSizeLandscape);
//            // test01Changed(innerDriver, eyes, viewportSizePortrait);
//        }
        innerDriver.quit();
    }

    static private void test01(WebDriver innerDriver, Eyes eyes, RectangleSize viewportSize) {
        // Start the test and set the browser's viewport size
        WebDriver driver = eyes.open(innerDriver, appName, testName, viewportSize);
        try {
            // Go to Login dialog.
            driver.get(weburl);

            // TODO: fill in the username

            // TODO: add test.
            // TODO: In other words add code for making a baseline and comparing the screen with the baseline.

 //           eyes.checkWindow("Before enter name");                 // Visual checkpoint 1
//
//            driver.findElement(By.id("name")).sendKeys("My Name"); //enter the name
//            eyes.checkWindow("After enter name");                  // Visual checkpoint 2
//
//            driver.findElement(By.tagName("button")).click();      // Click the  button
//            eyes.checkWindow("After Click");                       // Visual checkpoint 3
//
            TestResults result = eyes.close(false); //false means don't thow exception for failed tests
            handleResult(result);
        } finally {
            eyes.abortIfNotClosed();
        }
    }

//    static private void test01Changed(WebDriver innerDriver, Eyes eyes, RectangleSize viewportSize) {
//        TestResults result;
//
//        // Start the test and set the browser's viewport size
//        WebDriver driver = eyes.open(innerDriver, appName, testName, viewportSize);
//        try {
//            String webUrlToUse = weburl;
//
//            if (changeTest) {
//                webUrlToUse += "?diff2";
//            }
//
//            driver.get(webUrlToUse);                                // Navigate the browser to the web-site.
//            if (!changeTest) {
//                eyes.checkWindow(" Before enter name");   			// skip visual checkpoint number 1
//            }
//
//            driver.findElement(By.id("name")).sendKeys("My Name");  // enter the name
//            eyes.checkWindow("After enter name)");                  //visual checkpoint number 2
//
//            driver.findElement(By.tagName("button")).click();      // Click the "Click me!" button.
//            eyes.checkWindow("After click");                       // Visual checkpoint 3
//
//            if (changeTest) {
//                eyes.checkWindow("After click again");    			// additional visual checkpoint 4
//            }
//
//            result = eyes.close(false);
//            handleResult(result);
//        } finally {
//            eyes.abortIfNotClosed();
//        }
//    }

    static private  void handleResult(TestResults result) {
        String resultStr;
        String url;
        if (result == null) {
            resultStr = "Test aborted";
            url = "undefined";
        } else {
            url = result.getUrl();
            int totalSteps = result.getSteps();
            if (result.isNew()) {
                resultStr = "New Baseline Created: " + totalSteps + " steps";
            } else if (result.isPassed()) {
                resultStr = "All steps passed:     " + totalSteps + " steps";
            } else {
                resultStr = "Test Failed     :     " + totalSteps + " steps";
                resultStr += " matches=" +  result.getMatches();      /*  matched the baseline */
                resultStr += " missing=" + result.getMissing();       /* missing in the test*/
                resultStr += " mismatches=" + result.getMismatches(); /* did not match the baseline */
            }
        }
        resultStr += "\n" + "results at " + url;
        System.out.println(resultStr);
    }


    static private void setup(Eyes eyes) {
        String apiKey = System.getenv("APPLITOOLS_API_KEY");
        eyes.setApiKey(apiKey);

        //eliminate artifacts caused by a blinking cursor - on by default in latest SDK
        eyes.setIgnoreCaret(true);
    }
}