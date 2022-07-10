package com.lazerycode.selenium.tests;

import com.lazerycode.selenium.DriverBase;
import com.lazerycode.selenium.page_objects.DhuHomePage;
import com.lazerycode.selenium.page_objects.GartenstadtWandsbekFreieWhg;
import com.lazerycode.selenium.page_objects.HbhPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WohnungsCheckIT extends DriverBase {

    public static final String DHU_TEXT_TO_CHECK = "Momentan sind keine Objekte im Online-Angebot verfügbar";

    WebDriver driver;
    @BeforeClass
    public void setup() throws Exception {
        driver = getDriver();
    }
    @AfterClass
    public void shutdown() {
        driver.quit();
    }

    @Test
    public void checkDhu() throws Exception {
        // DHU
        final DhuHomePage dhuHomePage = new DhuHomePage();
        dhuHomePage.acceptCookieLayer();
        final boolean hasNoFreeFlats_dhu = dhuHomePage.hasNoFreeFlats();
        assertThat(hasNoFreeFlats_dhu).isEqualTo(true);
    }

    @Test
    public void checkGartenstadt() throws Exception {
        // Gartenstadt
        final GartenstadtWandsbekFreieWhg gartenstadtWandsbekFreieWhg = new GartenstadtWandsbekFreieWhg();
        gartenstadtWandsbekFreieWhg.acceptCookieLayer();
        final boolean hasNoFreeFlats_gartenstadt = gartenstadtWandsbekFreieWhg.hasNoFreeFlats();
        assertThat(hasNoFreeFlats_gartenstadt).isEqualTo(true);
    }

    @Test
    public void checkHbh() throws Exception {
        // HBH
        final HbhPage hbh = new HbhPage();
        final boolean hasNoFreeFlats= hbh.hasNoFreeFlats();
        assertThat(hasNoFreeFlats).isEqualTo(true);
    }
}
