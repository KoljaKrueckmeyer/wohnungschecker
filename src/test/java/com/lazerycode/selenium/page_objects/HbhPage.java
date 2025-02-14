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

    private String getHash() {
        WebElement el = driver.findElement(By.className("v_object"));
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        byte[] screenshot = el.getScreenshotAs(OutputType.BYTES);
        return DigestUtils.md5Hex(screenshot).toUpperCase();
    }

    public boolean hasChangedSinceLastRun() throws IOException, NoSuchAlgorithmException {
        String currentHash = getHash();
        File file = new File("src/test/resources/hashes/hbh_hash.txt");
        String lastHash = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        FileUtils.writeStringToFile(file, currentHash, StandardCharsets.UTF_8);
        return !currentHash.equalsIgnoreCase(lastHash);
    }
}
