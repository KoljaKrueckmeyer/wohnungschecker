package com.lazerycode.selenium.page_objects;

import com.lazerycode.selenium.DriverBase;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GartenstadtWandsbekFreieWhg {

    public static final String TEXT_TO_CHECK = "Momentan sind keine Objekte verfügbar1.";

    final WebDriver driver;

    private final WebDriverWait wait;
    public GartenstadtWandsbekFreieWhg() throws Exception {
        this.driver = DriverBase.getDriver();
        driver.get("https://asp2.immosolve.eu/immosolve_presentation/pub/modern/2227623/HP/immo.jsp");
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
        boolean ret = true;
        String textToCheck = String.format("//*[text()='%s']", TEXT_TO_CHECK);
        System.out.println(textToCheck);
        final By byTextToCheck = By.xpath(textToCheck);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(byTextToCheck));
            WebElement e = driver.findElement(byTextToCheck);
            ret = TEXT_TO_CHECK.equals(e.getText());
        } catch (TimeoutException e) {
            final By byAnzZimmer = By.xpath("//span[@id='immo[0].labels.anzahlZimmer']");
            final List<WebElement> anzZimmerElements = driver.findElements(byAnzZimmer);
            ret = !anzZimmerElements.stream().anyMatch(el -> "3".equals(el.getText()));
        }
        //Normally you would have some assertions to check things that you really care about
        return ret;
    }
}
