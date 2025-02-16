package com.lazerycode.selenium.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;

import static com.lazerycode.selenium.config.DriverType.*;

public class DriverFactory {

    private static final Logger LOG = (Logger) LogManager.getLogger(DriverFactory.class);
    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
    private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");
    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
    private final String proxyHostname = System.getProperty("proxyHost");
    private final Integer proxyPort = Integer.getInteger("proxyPort");
    private final String proxyUsername = System.getProperty("proxyUsername");
    private final String proxyPassword = System.getProperty("proxyPassword");
    private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);

    private ChromeDriver driver;
    private final DriverType selectedDriverType;

    public DriverFactory() {
        DriverType driverType = CHROME;
        String browser = System.getProperty("browser", driverType.toString()).toUpperCase();
        try {
            driverType = valueOf(browser);
        } catch (IllegalArgumentException ignored) {
            LOG.warn("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
            LOG.warn("No driver specified, defaulting to '" + driverType + "'...");
        }
        selectedDriverType = driverType;
    }

    public ChromeDriver getDriver() throws Exception {
        if (null == driver) {
            instantiateWebDriver(selectedDriverType);
        }

        return driver;
    }

    public ChromeDriver getStoredDriver() {
        return driver;
    }

    public void quitDriver() {
        if (null != driver) {
            driver.quit();
            driver = null;
        }
    }

    private void instantiateWebDriver(DriverType driverType) throws MalformedURLException {
        LOG.info("Local Operating System: " + operatingSystem);
        LOG.info("Local Architecture: " + systemArchitecture);
        LOG.info("Selected Browser: " + selectedDriverType);
        LOG.info("Connecting to Selenium Grid: " + useRemoteWebDriver);

        Capabilities desiredCapabilities =  new ImmutableCapabilities();
        ChromeOptions chromeOptions = new ChromeOptions();
        if (HEADLESS) {
            chromeOptions.addArguments("--headless");
        }
        chromeOptions.addArguments("--no-default-browser-check");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");

//        if (proxyEnabled) {
//            Proxy proxy = new Proxy();
//            proxy.setProxyType(MANUAL);
//            proxy.setHttpProxy(proxyDetails);
//            proxy.setSslProxy(proxyDetails);
//            if (!proxyUsername.isEmpty()) {
//                proxy.setSocksUsername(proxyUsername);
//            }
//            if (!proxyPassword.isEmpty()) {
//                proxy.setSocksPassword(proxyPassword);
//            }
//            desiredCapabilities.setCapability(PROXY, proxy);
//        }
//
//        if (useRemoteWebDriver) {
//            URL seleniumGridURL = new URL(System.getProperty("gridURL"));
//            String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
//            String desiredPlatform = System.getProperty("desiredPlatform");
//
//            if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
//                desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
//            }
//
//            if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
//                desiredCapabilities.setVersion(desiredBrowserVersion);
//            }
//x^x^x
//            desiredCapabilities.setBrowserName(selectedDriverType.toString());
            driver = new ChromeDriver(chromeOptions);
//        } else {
//            driver = WebDriverManager.chromiumdriver().getWebDriver();
//        }
    }
}

