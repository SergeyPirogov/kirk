package com.automation.remarks.kirk

import com.automation.remarks.kirk.core.PageClass
import com.automation.remarks.kirk.core.configuration
import com.automation.remarks.kirk.core.getDriver
import org.openqa.selenium.WebDriver

/**
 * Created by sergey on 22.07.17.
 */
class Kirk {

    companion object {

        @JvmStatic
        val browser
            get() = Browser()

        @JvmStatic
        fun open(url: String) {
            browser.to(url)
        }

        @JvmStatic
        fun <T : Page> open(pageClass: PageClass<T>): T {
            return browser.to(pageClass)
        }

        @JvmStatic
        fun <T : Page> open(pageClass: PageClass<T>, block: T.() -> Unit) {
            open(pageClass).block()
        }

        @JvmStatic
        fun <T : Page> at(pageClass: PageClass<T>): T {
            return pageClass(browser)
        }

        @JvmStatic
        fun <T : Page> at(pageClass: PageClass<T>, block: T.() -> Unit) {
            at(pageClass).block()
        }

        @JvmStatic
        fun closeBrowser(){
            browser.quit()
        }

        fun drive(driver: WebDriver = getDriver(), block: Browser.() -> Unit): Browser {
            val browser = Browser(driver).apply { config = configuration }
            browser.block()
            return browser
        }
    }
}