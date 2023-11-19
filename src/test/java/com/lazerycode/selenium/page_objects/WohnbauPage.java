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

    public final static String CITY = "Hamburg";
    public final static int MIN_ROOM_COUNT = 0;

    final WebDriver driver;
    private final WebDriverWait wait;

    public WohnbauPage() throws Exception {
        this.driver = DriverBase.getDriver();
        driver.get("https://www.wohnbau-gmbh.de/fuer-interessenten/wohnfinder?ort=Hamburg");
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

    public boolean hasNoInterestingFlats() throws Exception {
        boolean ret = true;
        final WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), Duration.ofSeconds(1), Duration.ofMillis(100));
        By tileSelector = By.className("resultcontainer");
        wait.until(ExpectedConditions.presenceOfElementLocated(tileSelector));
        final List<WebElement> tiles = driver.findElements(tileSelector);

        String textToCheckCity = String.format(".//div[contains(@class, 'ort)]", "");
        String textToCheckRoomCount = String.format(".//div[contains(@class, 'zimmer')]/span]");
        String textToCheckFleache = String.format(".//div[contains(@class, 'flaeche')]/span]");
        final By byTextCity = By.xpath(textToCheckCity);
        final By byTextRoomCount = By.xpath(textToCheckRoomCount);
        for(WebElement tile : tiles) {
            try {
                final WebElement cityElement = tile.findElement(byTextCity);
                final WebElement roomElement = tile.findElement(byTextRoomCount);
                if(roomElement.getText().startsWith(String.format("%d,", MIN_ROOM_COUNT))) {
                    ret = false;
                    break;
                }
            } catch(Exception e) {

            }
        }
        return ret;
    }
}
