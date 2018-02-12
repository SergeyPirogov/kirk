package com.automation.remarks.kirk.test

import com.automation.remarks.kirk.core.WebDriverFactory
import com.automation.remarks.kirk.ext.isAlive
import me.tatarka.assertk.assertions.isEqualTo
import org.testng.annotations.Test

/**
 * Created by sepi on 8/11/2017.
 */
class ExtensionsTest : BaseTest() {

    @Test
    fun testCanVerifyDriverInNotAliveAfterClose() {
        val driver = WebDriverFactory().getDriver()
        driver.quit()
        assertThat(driver.isAlive()).isEqualTo(false)
    }
}