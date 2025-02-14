package com.lazerycode.selenium.config;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public interface DriverSetup {
    ChromeDriver getWebDriverObject(Capabilities capabilities);
}