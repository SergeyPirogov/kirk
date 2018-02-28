package com.automation.remarks.kirk.test

import com.automation.remarks.kirk.Browser
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

class BDDConditionsTest :BaseTest() {


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

    @Test
    fun testTextConditionPositive() {
        chrome.element("#header").should.have.text("Kirk")
    }

    @Test
    fun testConditionVisiblePositive() {
        chrome.element("#header")
                .should
                .be
                .visible
                .and
                .have.text("Kirk")
    }

    @Test
    fun testInvertedTextConditionPositive() {
        chrome.element("#header").should.not.have.text("Kir")
    }
}