package com.automation.remarks.kirk.test

import com.automation.remarks.kirk.Browser
import com.automation.remarks.kirk.conditions.attr
import com.automation.remarks.kirk.conditions.text
import com.automation.remarks.kirk.conditions.visible
import me.tatarka.assertk.assertions.hasClass
import me.tatarka.assertk.assertions.hasMessageStartingWith
import org.openqa.selenium.TimeoutException
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

/**
 * Created by sepi on 7/12/2017.
 */
class ElementConditionsTest : BaseTest() {

    lateinit var chrome: Browser

    @BeforeClass
    fun setUp() {
        chrome = Browser().apply {
            baseUrl = url
        }
        chrome.open(url)
    }

    @AfterClass
    fun closeBrowser(){
        chrome.quit()
    }

    @Test fun testTextConditionPositive() {
        chrome.element("#header").shouldHave(text("Kirk"))
    }

    @Test fun testNotCondition() {
        chrome.element("#input_invisible").shouldNotBe(visible)
    }

    @Test fun testConditionWaitUntil() {
        chrome.element("#header").waitUntil(visible, 5000)
    }

    @Test fun testWaitUtilTextCondition() {
        chrome.element("#header").waitUntil(text("Kirk"), 5000)
    }

    @Test fun testWaitUtilAttributeCondition() {
        chrome.element("[title='a']").waitUntil(attr("class", "a para"), 3000)
    }

    @Test fun testNotConditionText() {
        assertThat {
            chrome.element("#header").shouldNotHave(text("Kirk"))
        }.throwsError {
            it.hasClass(TimeoutException::class)
            it.hasMessageStartingWith("""
            failed while waiting 4 seconds
            to assert text
            for element located {By.cssSelector: #header}
            reason: condition did not match
                expected not: Kirk
                actual: Kirk
            """)
        }
    }

    @Test fun testTextConditionFailMassage() {
        assertThat {
            chrome.element("#header").shouldHave(text("irk"))
        }.throwsError {
            it.hasClass(TimeoutException::class)
            it.hasMessageStartingWith("""
            failed while waiting 4 seconds
            to assert text
            for element located {By.cssSelector: #header}
            reason: condition did not match
                expected: []irk
                actual: [K]irk
            """)
        }
    }

    @Test fun testElementVisibilityConditionFailMassage() {
        assertThat {
            chrome.element("#input_invisible").shouldBe(visible)
        }.throwsError {
            it.hasClass(TimeoutException::class)
            it.hasMessageStartingWith("""
            failed while waiting 4 seconds
            to assert visibility
            for element located {By.cssSelector: #input_invisible}
            reason: condition did not match
                expected: visible
                actual: invisible
            """)
        }
    }

    @Test fun testElementAttrConditionFailMassage() {
        assertThat {
            chrome.element(".paginator a").shouldHave(attr("href", "second_page.html"))
        }.throwsError {
            it.hasClass(TimeoutException::class)
            it.hasMessageStartingWith("""
            failed while waiting 4 seconds
            to assert attribute {href}
            for element located {By.cssSelector: .paginator a}
            reason: condition did not match
                expected: []second_page.html
                actual: [http://localhost:8086/]second_page.html
            """)
        }
    }

    @Test fun testConditionWaitUntilText() {
        assertThat {
            chrome.element("#input_invisible").waitUntil(visible, 6000)
        }.throwsError {
            it.hasClass(TimeoutException::class)
            it.hasMessageStartingWith("""
            failed while waiting 6 seconds
            to assert visibility
            for element located {By.cssSelector: #input_invisible}
            reason: condition did not match
                expected: visible
                actual: invisible
            """)
        }
    }
}