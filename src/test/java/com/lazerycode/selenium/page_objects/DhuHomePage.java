package com.lazerycode.selenium.page_objects;

import com.lazerycode.selenium.DriverBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DhuHomePage {
    public static final String DHU_TEXT_TO_CHECK = "Momentan sind keine Objekte im Online-Angebot verfügbar";

    final WebDriver driver;

    private final WebDriverWait wait;
    public DhuHomePage() throws Exception {
        this.driver = DriverBase.getDriver();
        driver.get("https://hpm2.immosolve.eu/immosolve_presentation/pub/modern/2223228/HP/immo.jsp");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(DriverBase.getDriver(), Duration.ofSeconds(15), Duration.ofMillis(100));
    }

    public void acceptCookieLayer() throws Exception {
        final WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), Duration.ofSeconds(15), Duration.ofMillis(100));
        By acceptCookieLayerButtonSelector = By.id("uc-btn-accept-banner");
        wait.until(ExpectedConditions.presenceOfElementLocated(acceptCookieLayerButtonSelector));
        WebElement acceptCookieLayerButton = driver.findElement(acceptCookieLayerButtonSelector);
        acceptCookieLayerButton.click();
    }

    public boolean hasNoFreeFlats() {
        String textToCheck = String.format("//*[text()='%s']", DHU_TEXT_TO_CHECK);
        System.out.println(textToCheck);
        final By byTextToCheck = By.xpath(textToCheck);
        wait.until(ExpectedConditions.presenceOfElementLocated(byTextToCheck));

        WebElement e = driver.findElement(byTextToCheck);

        //Normally you would have some assertions to check things that you really care about
        return DHU_TEXT_TO_CHECK.equals(e.getText());
    }
}
