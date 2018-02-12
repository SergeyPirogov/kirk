package com.automation.remarks.kirk.test.oop

import com.automation.remarks.kirk.Browser
import com.automation.remarks.kirk.Configuration
import com.automation.remarks.kirk.conditions.size
import com.automation.remarks.kirk.test.BaseTest
import io.github.bonigarcia.wdm.ChromeDriverManager
import me.tatarka.assertk.assert
import me.tatarka.assertk.assertions.hasSize
import me.tatarka.assertk.assertions.isEqualTo
import me.tatarka.assertk.assertions.isInstanceOf
import org.aeonbits.owner.Config.*
import org.aeonbits.owner.ConfigFactory
import org.testng.annotations.Test

/**
 * Created by sergey on 09.07.17.
 */
class ConfigurationTest : BaseTest() {

    val config: Configuration = ConfigFactory.create(Cust::class.java, System.getProperties())

    @Test
    fun testCanSetCustomConfig() {
        assert(config.timeout()).isEqualTo(2000)
    }

    @Test
    fun testCanSetChromeOptions() {
        assert(config.chromeArgs()).isInstanceOf(List::class.java)
        assert(config.chromeArgs()).hasSize(3)
    }

    @Test fun testCanSetCustomBrowseConfig() {
        ChromeDriverManager.getInstance().setup()
        val browser = Browser().apply {
            baseUrl = url
        }

        browser.open("/")
        browser.all("li").shouldHave(size(10))
    }
}

@Sources("classpath:browser.properties")
interface Cust : Configuration {
    @DefaultValue("2000")
    @Key("firefox.timeout")
    override fun timeout(): Int

    @Key("autoClose")
    @DefaultValue("true")
    override fun autoClose(): Boolean

    @Separator(",")
    @DefaultValue("headless,disable-gpu,test")
    @Key("kirk.chrome.args")
    override fun chromeArgs(): List<String>
}