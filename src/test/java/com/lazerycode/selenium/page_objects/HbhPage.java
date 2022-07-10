package com.lazerycode.selenium.page_objects;

import com.lazerycode.selenium.DriverBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class HbhPage {
    public static final String TEXT_TO_CHECK = "Zurzeit keine freien Wohnungen vorhanden.";
    final WebDriver driver;
    private final WebDriverWait wait;

    public HbhPage() throws Exception {
        this.driver = DriverBase.getDriver();
        driver.get(" https://www.hanseatische.de/de/wohnungsangebote");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(DriverBase.getDriver(), Duration.ofSeconds(15), Duration.ofMillis(100));
    }

    public boolean hasNoFreeFlats() {
        String textToCheck = String.format("//*[text()='%s']", TEXT_TO_CHECK);
        System.out.println(textToCheck);
        final By byTextToCheck = By.xpath(textToCheck);
        wait.until(ExpectedConditions.presenceOfElementLocated(byTextToCheck));

        WebElement e = driver.findElement(byTextToCheck);

        //Normally you would have some assertions to check things that you really care about
        return TEXT_TO_CHECK.equals(e.getText());
    }
}
