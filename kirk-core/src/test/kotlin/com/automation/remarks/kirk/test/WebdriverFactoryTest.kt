package com.automation.remarks.kirk.test

import com.automation.remarks.kirk.Configuration
import com.automation.remarks.kirk.Kirk
import com.automation.remarks.kirk.Kirk.Companion.closeBrowser
import com.automation.remarks.kirk.Kirk.Companion.open
import com.automation.remarks.kirk.conditions.text
import com.automation.remarks.kirk.core.WebDriverFactory
import com.automation.remarks.kirk.core.configuration
import com.automation.remarks.kirk.core.loadConfig
import com.automation.remarks.kirk.test.pages.StartPage
import org.aeonbits.owner.Config
import org.openqa.grid.selenium.GridLauncherV3
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

/**
 * Created by sepi on 8/11/2017.
 */
class WebDriverFactoryTest : BaseTest() {

    @BeforeClass
    fun setUp() {
        val hub = arrayOf("-port", "4444", "-host", "localhost", "-role", "hub")
        GridLauncherV3.main(hub)

        val node = arrayOf("-port", "5555", "-host", "localhost", "-role", "node", "-hub", "http://localhost:4444/grid/register")
        GridLauncherV3.main(node)
    }

    @Test
    fun testCanSpinFirefox() {
        configuration = loadConfig(Firefox::class)
        val driverFactory = WebDriverFactory()
        assertThat(driverFactory.getDriver() is Firefox)
    }

    @Test(enabled = false)
    fun testCanSpinRemoteDriver() {
        configuration = loadConfig(RemoteDriver::class)
        val driverFactory = WebDriverFactory()
        assertThat(driverFactory.getDriver() is Firefox)
    }

    @Test
    fun testCanRecreateDriverAfterQuit() {
        open(url)
        Kirk.at(::StartPage).browser.quit()
        open(url)
        Kirk.at(::StartPage).header shouldHave text("Kirk")
    }


    @Test
    fun testCanRecreateDriverAfterKirkQuit() {
        open(url)
        closeBrowser()
        open(url)
        Kirk.at(::StartPage).header shouldHave text("Kirk")
    }
}

interface Firefox : Configuration {
    @Config.DefaultValue("firefox")
    override fun browserName(): String
}

interface RemoteDriver : Configuration {

    @Config.DefaultValue("http://localhost:4444/wd/hub")
    override fun remoteUrl(): String

    @Config.DefaultValue("chrome")
    override fun browserName(): String
}