package com.automation.remarks.kirk.test.oop

import com.automation.remarks.kirk.Browser
import com.automation.remarks.kirk.conditions.size
import com.automation.remarks.kirk.conditions.text
import com.automation.remarks.kirk.ex.WrongUrlException
import com.automation.remarks.kirk.test.BaseTest
import me.tatarka.assertk.assert
import me.tatarka.assertk.assertions.isEqualTo
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.testng.annotations.Test

/**
 * Created by sergey on 28.06.17.
 */
class BrowserUsageTest : BaseTest() {

    // tag::testСanRunBrowser[]
    @Test
    fun testСanRunBrowser() {
        val browser = Browser(ChromeDriver())
        browser.open(url)
        browser.element("#header").shouldHave(text("Kirk"))
    }
    // end::testСanRunBrowser[]

    @Test
    fun testCanSetNewDriver() {
        val firefox = Browser(FirefoxDriver())
        firefox.open(url)
        firefox.element(".paginator a").click()
        firefox.element("#header").shouldHave(text("Second page"))
    }

    @Test
    fun testCanOpenTwoBrowser() {
        Browser().apply {
            open(url)
            element("#header").shouldHave(text("Kirk"))
        }

        Browser(FirefoxDriver()).apply {
            open(url)
            all("li").shouldHave(size(10))
        }
    }

    @Test
    fun testCanGetCurrentUrl() {
        Browser().apply {
            open(url)
            assert(currentUrl).isEqualTo(url)
        }
    }

    @Test
    fun testCanOpenCanonicalUrl() {
        val chrome = Browser(FirefoxDriver()).apply {
            baseUrl = url
        }

        chrome.open("/")
        chrome.all("li").shouldHave(size(10))
    }

    @Test
    fun testCanOpenCanonicalUrlWithoutSpecifiedBase() {
        assertExceptionThrown(WrongUrlException::class) {
            Browser().open("/")
        }
    }

    @Test
    fun testBrowserInteract() {
        Browser().run {
            to(url)
            element("#header").shouldHave(text("Kirk"))
            all("li").shouldHave(size(10))
        }
    }
}