package com.automation.remarks.kirk.test

import com.automation.remarks.kirk.Kirk
import com.automation.remarks.kirk.core.WebDriverFactory
import com.automation.remarks.kirk.ext.isAlive
import me.tatarka.assertk.assertions.contains
import me.tatarka.assertk.assertions.isEqualTo
import org.openqa.selenium.JavascriptExecutor
import org.testng.annotations.Test


/**
 * Created by sepi on 8/11/2017.
 */
class ExtensionsTest : BaseTest() {

    @Test
    fun testCanVerifyDriverInNotAliveAfterClose() {
        val driver = WebDriverFactory().getDriver()
        driver.quit()
        me.tatarka.assertk.assert(driver.isAlive()).isEqualTo(false)
    }

    @Test fun testCanExecuteJS() {
        Kirk.drive {
            to(url)
            val version = (driver as JavascriptExecutor)
                    .executeScript("return navigator.userAgent.toLowerCase()") as String
            me.tatarka.assertk.assert(version).contains("chrome")
        }
    }

    @Test fun testCanExecuteAsyncJS() {
        Kirk.drive {
            to(url)
            val version = (driver as JavascriptExecutor)
                    .executeScript("return navigator.userAgent.toLowerCase()", true) as String
            me.tatarka.assertk.assert(version).contains("chrome")
        }
    }
}