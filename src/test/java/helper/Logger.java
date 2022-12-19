package helper;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private final String fontBlueLarge = "<font color=blue size= \"+1\" >";
    private final String fontOrange = "<font color=orange>";
    private final String fontRed = "<font color=red>";
    private final String fontRedLarge = "<font color=red size= \"+1\" >";
    private final String fontGreenLarge = "<font color=green size= \"+1\" >";
    private final String fontCloseTag = "</font>";
    private int counter;
    private String name;
    private WebDriver driver;
    private ExtentTest test;
    private ExtentReports report;
    private Commons commons = new Commons();

    public Logger(String name, WebDriver driver) {
        this.name = name;
        this.driver = driver;
        System.out.println(ANSI_GREEN + "####### SETUP METHOD INTITIALIZED SUCCESSFULLY #######" + ANSI_RESET);
    }

    public String getName() {
        return this.name;
    }

    public WebDriver getDriver() {
        return this.driver;
    }


    public void step(String info) {
        counter++;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
        LocalDateTime now = LocalDateTime.now();
        String timenow = dtf.format(now);
        test.log(LogStatus.INFO,
                fontBlueLarge + timenow + " - " + name +  " - STEP-" + counter + ": " + info + fontCloseTag);
        System.out.println(ANSI_BLUE + timenow + " - " + name + " - STEP-" + counter + ": " + info + ANSI_RESET);
    }

    public void info(String info) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
        LocalDateTime now = LocalDateTime.now();
        String timenow = dtf.format(now);
        test.log(LogStatus.INFO, timenow + " - " + name + " - [INFO]: " + info);
        System.out.println(ANSI_WHITE + timenow + " - " + name + " - [INFO]: " + info + ANSI_RESET);
    }

    public void warning(String info) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
        LocalDateTime now = LocalDateTime.now();
        String timenow = dtf.format(now);
        test.log(LogStatus.INFO, fontOrange + timenow + " - " + name + " - [WARNING]: " + info + fontCloseTag);
        System.out.println(ANSI_YELLOW + timenow + " - " + name + " - [WARNING]: " + info + ANSI_RESET);
    }

    public void error(String info) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
        LocalDateTime now = LocalDateTime.now();
        String timenow = dtf.format(now);
        test.log(LogStatus.INFO, fontOrange + timenow + " - " + name + " - [ERROR]: " + info + fontCloseTag);
        System.out.println(ANSI_RED + timenow + " - " + name + " - " + " - [ERROR]: " + info + ANSI_RESET);
    }

    public void failedStep(String info, Exception e) {
        counter++;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String timenow = dtf.format(now);
        test.log(LogStatus.INFO,
                timenow + " - " + name + " - " + " - [STEP-INFO]: STEP-" + counter + ": " + info + "\n" + e);
        System.out.println(ANSI_RED + timenow + " - " + name + " - [STEP-INFO]: STEP-" + counter + ": " + info + "\n" + e + ANSI_RESET);
        Assert.fail();
    }

    public void logResults(ITestResult testResult, String className,Exception exceptionMessage,
                           AssertionError assertionMessage) throws Exception {
        System.out.println(ANSI_YELLOW + "####### CLEANUP METHOD EXECUTION #######" + ANSI_RESET);
        if (testResult.getStatus() == ITestResult.FAILURE) {
            System.out.println(ANSI_RED + "####### EXECUTION FAILED! #######" + ANSI_RESET);
            String filename = className + "_" + commons.getRandomString(5) + ".png";
            String directory = new File(System.getProperty("user.dir")).getParent() + "\\screenshots\\";
            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(sourceFile, new File(directory + filename));
            String imagepath = test.addScreenCapture(directory + filename);
            test.log(LogStatus.FAIL, testResult.getMethod().getMethodName(), imagepath);
            if (assertionMessage != null) {
                test.log(LogStatus.FAIL, fontRedLarge + assertionMessage.getMessage() + fontCloseTag);
            }
            if (exceptionMessage != null) {
                test.log(LogStatus.FAIL, fontRedLarge + exceptionMessage.getMessage() + fontCloseTag);
            }
        } else {
            System.out.println(ANSI_GREEN + "####### EXECUTION PASSED! #######" + ANSI_RESET);
            test.log(LogStatus.PASS, fontGreenLarge + testResult.getMethod().getMethodName() + fontCloseTag);
        }
        report.endTest(test);
        report.flush();
    }

    public void prepareTest(String className) {
        report = new ExtentReports(new File(System.getProperty("user.dir")).getParent() + "\\reports\\" + className
                + "-" + "_" + commons.getRandomString(5) + ".html");
        test = report.startTest(className);
    }
}