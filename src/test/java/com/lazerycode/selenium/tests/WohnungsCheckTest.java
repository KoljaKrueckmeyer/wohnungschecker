package com.lazerycode.selenium.tests;

import com.lazerycode.selenium.DriverBase;
import com.lazerycode.selenium.page_objects.*;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WohnungsCheckTest extends DriverBase {

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
        boolean hasNoFreeFlats_dhu;
        try {
            hasNoFreeFlats_dhu = dhuHomePage.hasNoFreeFlats();
        } catch (TimeoutException e) {
            hasNoFreeFlats_dhu = false;
        }
        boolean hasChangedSinceLastRun = dhuHomePage.hasChangedSinceLastRun();
        if (!hasNoFreeFlats_dhu)
            assertThat(!hasChangedSinceLastRun).isEqualTo(true);
    }

    @Test(enabled = true)
    public void checkGartenstadt() throws Exception {
        // Gartenstadt
        final GartenstadtWandsbekFreieWhg gartenstadtWandsbekFreieWhg = new GartenstadtWandsbekFreieWhg();
        boolean hasNoFreeFlats = true;
        try {
        gartenstadtWandsbekFreieWhg.acceptCookieLayer();
            hasNoFreeFlats = gartenstadtWandsbekFreieWhg.hasNoFreeFlats();
        } catch (TimeoutException e) {
            hasNoFreeFlats = false;
        }
        boolean hasChangedSinceLastRun = gartenstadtWandsbekFreieWhg.hasChangedSinceLastRun();
        if (!hasNoFreeFlats)
            assertThat(!hasChangedSinceLastRun).isEqualTo(true);
        assertThat(hasNoFreeFlats).isEqualTo(true);
    }

    @Test(enabled = true)
    public void checkHbh() throws Exception {
        // HBH
        final HbhPage hbh = new HbhPage();
        boolean hasNoFreeFlats = true;
        try {
            hasNoFreeFlats = hbh.hasNoFreeFlats();
        } catch (TimeoutException e) {
            hasNoFreeFlats = false;
        }
        boolean hasChangedSinceLastRun = hbh.hasChangedSinceLastRun();
        if (!hasNoFreeFlats)
            assertThat(!hasChangedSinceLastRun).isEqualTo(true);
    }

    @Test(enabled = false)
    public void checkMeravis() throws Exception {
        // Meravis
        final MeravisPage meravis = new MeravisPage();
        boolean hasNoFreeFlats = true;
        try {
            meravis.acceptCookieLayer();
            hasNoFreeFlats = meravis.hasNoInterestingFlats();
        } catch (TimeoutException e) {
            hasNoFreeFlats = false;
        }
        boolean hasChangedSinceLastRun = meravis.hasChangedSinceLastRun();
        if (!hasNoFreeFlats)
            assertThat(!hasChangedSinceLastRun).isEqualTo(true);
    }

    @Test(enabled = false)
    public void checkWohnbau() throws Exception {
        final WohnbauPage wohnbau = new WohnbauPage();
        boolean hasNoFreeFlats = true;
        try {
            wohnbau.acceptCookieLayer();
            hasNoFreeFlats = wohnbau.hasNoInterestingFlats();
        } catch (TimeoutException e) {
            hasNoFreeFlats = false;
        }
        boolean hasChangedSinceLastRun = wohnbau.hasChangedSinceLastRun();
        if (!hasNoFreeFlats)
            assertThat(!hasChangedSinceLastRun).isEqualTo(true);
    }
}
