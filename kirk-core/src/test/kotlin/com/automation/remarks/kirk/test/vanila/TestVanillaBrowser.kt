package com.automation.remarks.kirk.test.vanila

import com.automation.remarks.kirk.Kirk.Companion.drive
import com.automation.remarks.kirk.conditions.size
import com.automation.remarks.kirk.conditions.text
import com.automation.remarks.kirk.ext.autoClose
import com.automation.remarks.kirk.ext.click
import com.automation.remarks.kirk.test.BaseTest
import com.automation.remarks.kirk.test.pages.SecondPage
import com.automation.remarks.kirk.test.pages.StartPage
import me.tatarka.assertk.assertions.isEqualTo
import org.openqa.selenium.Keys
import org.openqa.selenium.chrome.ChromeDriver
import org.testng.annotations.Test

/**
 * Created by sergey on 09.07.17.
 */
class TestVanillaBrowser : BaseTest() {

    @Test
    fun testCanGetUrl() {
        drive {
            to(url)
            me.tatarka.assertk.assert(currentUrl).isEqualTo(url)
        }
    }

    @Test fun testCanDriverBrowser() {
        drive {
            screenSize = listOf(640, 480)
            open(url)
            element("#header").shouldHave(text("Kirk"))
        }
    }

    @Test fun testCanDriverPage() {
        drive {
            baseUrl = url
            to(::StartPage) {
                list shouldHave size(3)
                link.click()
                at(::SecondPage).header.shouldHave(text("Second page"))
            }
            interact {
                keyDown(Keys.CONTROL)
                click(element(".genres").firstChild())
                click(element(".genres").lastChild())
                keyUp(Keys.CONTROL)
            }
        }
    }

    // tag::testCanDriveScripts[]
    @Test fun testCanDriveScripts() {
        drive {
            open(url)
            element("#header").shouldHave(text("Kirk"))
            element(".paginator a").click()
            element("#header").shouldHave(text("Second page"))
        }
    }
    // end::testCanDriveScripts[]

    @Test fun testDriverCanOpenSecondDriver() {
        val firstBrowser = drive {
            baseUrl = url
            open("/")
            element("#header").shouldHave(text("Kirk"))
        }
        drive(ChromeDriver().autoClose()) {
            open(url)
            element(".paginator a").click()
            at(::SecondPage).header.shouldHave(text("Second page"))
        }

        firstBrowser.to(::StartPage).link.click()
        firstBrowser.at(::SecondPage).header.shouldHave(text("Second page"))
    }
}