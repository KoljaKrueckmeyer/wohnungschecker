package com.lazerycode.selenium.tests;

import com.lazerycode.selenium.DriverBase;
import com.lazerycode.selenium.page_objects.GoogleHomePage;
import com.lazerycode.selenium.page_objects.GoogleSearchPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WohnungsCheckIT extends DriverBase {


    @Test
    public void checkDhu() throws Exception {
        // Create a new WebDriver instance
        // Notice that the remainder of the code relies on the interface, not the implementation.
        WebDriver driver = getDriver();

        /*
        driver.get("https://www.dhu.hamburg/wohnungsangebote/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), Duration.ofSeconds(15), Duration.ofMillis(100));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));

        WebElement eIf = driver.findElement(By.tagName("iframe"));
        String src = eIf.getAttribute("src");
        System.out.println("eIf:" + src);
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        */
        driver.get("https://hpm2.immosolve.eu/immosolve_presentation/pub/modern/2223228/HP/immo.jsp");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Locating element with text()
        WebElement e =driver.findElement(By.xpath("//*[text()='Momentan sind keine Objekte im Online-Angebot verfügbar']"));

        //Normally you would have some assertions to check things that you really care about
        assertThat(e.getText()).isEqualTo("Momentan sind keine Objekte im Online-Angebot verfügbar");
        driver.quit();
    }
}
