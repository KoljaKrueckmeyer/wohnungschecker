package com.lazerycode.selenium.page_objects;

import com.lazerycode.selenium.DriverBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WohnbauPage {

    public final static int MIN_ROOM_COUNT = 2;
    private static final double MAX_MIETE_IN_EUR = 1350;
    private static final Double MIN_FLEACHE = 69.0;

    final WebDriver driver;

    public WohnbauPage() throws Exception {
        this.driver = DriverBase.getDriver();
        driver.get("https://www.wohnbau-gmbh.de/fuer-interessenten/wohnfinder?ort=Hamburg");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void acceptCookieLayer() throws Exception {
        final WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), Duration.ofSeconds(15), Duration.ofMillis(100));
        By acceptCookieLayerButtonSelector = By.id("uc-btn-accept-banner");
        wait.until(ExpectedConditions.presenceOfElementLocated(acceptCookieLayerButtonSelector));
        WebElement acceptCookieLayerButton = driver.findElement(acceptCookieLayerButtonSelector);
        acceptCookieLayerButton.click();
    }

    public boolean hasNoInterestingFlats() throws Exception {
        boolean ret = true;
        final WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), Duration.ofSeconds(1), Duration.ofMillis(100));
        By tileSelector = By.className("res-detail");
        wait.until(ExpectedConditions.presenceOfElementLocated(tileSelector));
        final List<WebElement> tiles = driver.findElements(tileSelector);

        String textToCheckRoomCount = String.format(".//div[contains(@class, 'zimmer') and contains(./span, '%s')]", MIN_ROOM_COUNT + ",");
        String textToCheckFleache = ".//div[contains(@class, 'flaeche')]/span";
        String textMiete = ".//div[contains(@class, 'miete')]/span";

        final By byTextRoomCount = By.xpath(textToCheckRoomCount);
        final By byMiete = By.xpath(textMiete);
        final By byFlaeche = By.xpath(textToCheckFleache);
        System.out.println("tiles found res-detail: " + tiles.size());
        for(WebElement tile : tiles) {
            try {
                //final WebElement cityElement = tile.findElement(byTextCity);
                final WebElement roomElement = tile.findElement(byTextRoomCount);
                final WebElement mieteElement = tile.findElement(byMiete);
                final WebElement flaecheElement = tile.findElement(byFlaeche);
                final String mieteInEurStr = mieteElement.getAttribute("innerHTML").replaceAll(" EUR", "").replace(".","");
                double mieteInEur = Double.parseDouble(mieteInEurStr);
                System.out.println("mieteElement.getText() = " + mieteInEur);
                final String fleacheStr = flaecheElement.getAttribute("innerHTML").replaceAll(" m²", "").replace(".", "");
                double fleache = Double.parseDouble(fleacheStr);
                if(roomElement != null && mieteInEur < MAX_MIETE_IN_EUR && fleache > MIN_FLEACHE) {
                    ret = false;
                    break;
                }
            } catch(Throwable e) {
                System.out.println(e.getMessage());
            }
        }
        return ret;
    }
}
