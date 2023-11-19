package com.lazerycode.selenium.tests;

import com.lazerycode.selenium.DriverBase;
import com.lazerycode.selenium.page_objects.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WohnungsCheckIT extends DriverBase {

    WebDriver driver;
    @BeforeClass
    public void setup() throws Exception {
        driver = getDriver();
    }
    @AfterClass
    public void shutdown() {
        driver.quit();
    }

    @Test(enabled = true)
    public void checkDhu() throws Exception {
        // DHU
        final DhuHomePage dhuHomePage = new DhuHomePage();
        dhuHomePage.acceptCookieLayer();
        final boolean hasNoFreeFlats_dhu = dhuHomePage.hasNoFreeFlats();
        assertThat(hasNoFreeFlats_dhu).isEqualTo(true);
    }

    @Test(enabled = true)
    public void checkGartenstadt() throws Exception {
        // Gartenstadt
        final GartenstadtWandsbekFreieWhg gartenstadtWandsbekFreieWhg = new GartenstadtWandsbekFreieWhg();
        gartenstadtWandsbekFreieWhg.acceptCookieLayer();
        final boolean hasNoFreeFlats_gartenstadt = gartenstadtWandsbekFreieWhg.hasNoFreeFlats();
        assertThat(hasNoFreeFlats_gartenstadt).isEqualTo(true);
    }

    @Test(enabled = false)
    public void checkHbh() throws Exception {
        // HBH
        final HbhPage hbh = new HbhPage();
        final boolean hasNoFreeFlats= hbh.hasNoFreeFlats();
        assertThat(hasNoFreeFlats).isEqualTo(true);
    }

    @Test(enabled = false)
    public void checkMeravis() throws Exception {
        // Meravis
        final MeravisPage meravis = new MeravisPage();
        meravis.acceptCookieLayer();
        final boolean hasNoFreeFlats = meravis.hasNoInterestingFlats();
        assertThat(hasNoFreeFlats).isEqualTo(true);
    }

    @Test(enabled = true)
    public void checkWohnbau() throws Exception {
        final WohnbauPage wohnbau = new WohnbauPage();
        wohnbau.acceptCookieLayer();
        final boolean hasNoFreeFlats = wohnbau.hasNoInterestingFlats();
        assertThat(hasNoFreeFlats).isEqualTo(true);
    }
}
