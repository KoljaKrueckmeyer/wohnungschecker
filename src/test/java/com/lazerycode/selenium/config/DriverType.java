package com.lazerycode.selenium.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.HashMap;
public enum DriverType implements DriverSetup {

    CHROME {
        public ChromeDriver getWebDriverObject(Capabilities capabilities) {
            HashMap<String, Object> chromePreferences = new HashMap<>();
            chromePreferences.put("profile.password_manager_enabled", false);

            ChromeOptions options = new ChromeOptions();
            options.merge(capabilities);
            if (HEADLESS) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--no-default-browser-check");
            options.setExperimentalOption("prefs", chromePreferences);

            return new ChromeDriver(options);
        }
    };

    String getBraveBinaryLocation() {
        String defaultBraveLocation = "C:\\Program Files (x86)\\BraveSoftware\\Brave-Browser\\Application\\brave.exe";
        String currentOperatingSystemName = System.getProperties().getProperty("os.name");
        if (currentOperatingSystemName.toLowerCase().contains("mac")) {
            defaultBraveLocation = "/Applications/Brave Browser.app/Contents/MacOS/Brave Browser";
        }
        if (currentOperatingSystemName.toLowerCase().contains("linux")) {
            defaultBraveLocation = "/usr/bin/brave-browser";
        }

        return System.getProperty("braveBinaryLocation", defaultBraveLocation);
    }

    public final static boolean HEADLESS = Boolean.getBoolean("headless");

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
