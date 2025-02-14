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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DhuHomePage {
    public static final String DHU_TEXT_TO_CHECK = "Momentan sind keine Objekte im Online-Angebot verfügbar";

    final WebDriver driver;

    private final WebDriverWait wait;

    WebElement e;

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

        e = driver.findElement(byTextToCheck);
        //Normally you would have some assertions to check things that you really care about
        return DHU_TEXT_TO_CHECK.equals(e.getText());
    }

    private String getHash() {
        WebElement el = driver.findElement(By.id("ispage"));
        try {
            Thread.sleep(6000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        byte[] screenshot = el.getScreenshotAs(OutputType.BYTES);
        return DigestUtils.md5Hex(screenshot).toUpperCase();
    }

    public boolean hasChangedSinceLastRun() throws IOException, NoSuchAlgorithmException {
        String currentHash = getHash();
        File file = new File("src/test/resources/hashes/dhu_hash.txt");
        String lastHash = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        FileUtils.writeStringToFile(file, currentHash, StandardCharsets.UTF_8);
        return !currentHash.equalsIgnoreCase(lastHash);
    }
}
