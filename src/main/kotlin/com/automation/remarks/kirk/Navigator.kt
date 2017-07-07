package com.automation.remarks.kirk

import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver

/**
 * Created by sergey on 02.07.17.
 */
class Navigator(private val browser: BrowserHandler) {

    private val config = Browser.getConfig()
    private val driver: WebDriver
        get() = browser.driver

    fun <T : Page> at(pageClass: () -> T, closure: T.() -> Unit) {
        val page = pageClass()
        page.browser = browser
        page.closure()
    }

    private fun isAbsoluteUrl(url: String): Boolean {
        return (url.startsWith("http://") || url.startsWith("https://"))
    }

    fun back(): Navigator {
        driver.navigate().back()
        return this
    }

    fun forward(): Navigator {
        driver.navigate().forward()
        return this
    }

    fun refresh(): Navigator {
        driver.navigate().refresh()
        return this
    }

    fun quit() {
        driver.quit()
    }

    fun to(url: String) {
        if (config.startMaximized()) {
            driver.manage().window().maximize()
        } else {
            val screenSize = config.screenSize()
            driver.manage().window().size = Dimension(screenSize[0], screenSize[1])
        }
        if (isAbsoluteUrl(url)) {
            driver.navigate().to(url)
        } else {
            val baseUrl = config.baseUrl() ?:
                    throw IllegalStateException("Base url can't be null")
            driver.navigate().to(baseUrl + url)
        }
        if (config.autoClose()) {
            addAutoCloseHook()
        }
    }

    private fun addAutoCloseHook() {
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() = quit()
        })
    }
}