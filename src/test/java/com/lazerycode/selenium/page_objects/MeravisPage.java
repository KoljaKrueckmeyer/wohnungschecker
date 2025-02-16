package com.lazerycode.selenium.page_objects;

import com.lazerycode.selenium.DriverBase;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MeravisPage {

    public final static String CITY = "Hamburg";
    public final static int MIN_ROOM_COUNT = 3;

    final WebDriver driver;
    private final WebDriverWait wait;

    public MeravisPage() throws Exception {
        this.driver = DriverBase.getDriver();
        driver.get("https://www.meravis.de/mieten/wohnungssuche/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(DriverBase.getDriver(), Duration.ofSeconds(15), Duration.ofMillis(100));
    }

    public void acceptCookieLayer() throws Exception {
        final WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), Duration.ofSeconds(15), Duration.ofMillis(1000));
        By acceptCookieLayerButtonSelector = By.xpath("//*[contains(@data-testid, 'uc-accept-all-button')]");
        wait.until(ExpectedConditions.presenceOfElementLocated(acceptCookieLayerButtonSelector));
        WebElement acceptCookieLayerButton = driver.findElement(acceptCookieLayerButtonSelector);
        acceptCookieLayerButton.click();
    }

    public boolean hasNoInterestingFlats() throws Exception {
        boolean ret = true;
        final WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), Duration.ofSeconds(1), Duration.ofMillis(100));
        By tileSelector = By.className("immo-list-teaser");
        wait.until(ExpectedConditions.presenceOfElementLocated(tileSelector));
        final List<WebElement> tiles = driver.findElements(tileSelector);

        String textToCheckCity = String.format(".//div[contains(@class, 'immo-list-teaser-text')]/p", "");
        String textToCheckRoomCount = String.format(".//span[contains(@class, 'immo-info')]/ul/li[last()]");
        final By byTextCity = By.xpath(textToCheckCity);
        final By byTextRoomCount = By.xpath(textToCheckRoomCount);
        for(WebElement tile : tiles) {
            try {
                final WebElement cityElement = tile.findElement(byTextCity);
                final WebElement roomElement = tile.findElement(byTextRoomCount);
                if(cityElement.getText().endsWith(CITY) && roomElement.getText().startsWith(String.format("%d,", MIN_ROOM_COUNT))) {
                    ret = false;
                    break;
                }
            } catch(Exception e) {

            }
        }
        return ret;
    }

    private String getHash() {
        WebElement el = driver.findElement(By.className("immo-list"));
        try {
            Thread.sleep(8000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        byte[] screenshot = el.getScreenshotAs(OutputType.BYTES);
        return DigestUtils.md5Hex(screenshot).toUpperCase();
    }

    public boolean hasChangedSinceLastRun() throws IOException, NoSuchAlgorithmException {
        String currentHash = getHash();
        File file = new File("src/test/resources/hashes/meravis_hash.txt");
        String lastHash = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        FileUtils.writeStringToFile(file, currentHash, StandardCharsets.UTF_8);
        return !currentHash.equalsIgnoreCase(lastHash);
    }
}
