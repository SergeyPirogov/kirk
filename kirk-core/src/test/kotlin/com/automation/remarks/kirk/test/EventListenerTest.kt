package com.automation.remarks.kirk.test

import com.automation.remarks.kirk.*
import com.automation.remarks.kirk.conditions.text
import com.automation.remarks.kirk.core.AbstractKirkEventListener
import com.automation.remarks.kirk.core.KirkEventListener
import com.automation.remarks.kirk.core.configuration
import com.automation.remarks.kirk.core.loadConfig
import org.aeonbits.owner.Config
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.testng.annotations.Test

/**
 * Created by sergey on 30.07.17.
 */
class EventListenerTest : BaseTest() {

    @Test
    fun testCanLogEvents() {
       Browser(listener = LoggerListenerKirk()).apply {
           to(url)
           element("#header").shouldHave(text("Kirk"))
       }.quit()
    }

    @Test
    fun testCatLoadListenerFromClass() {
        configuration = loadConfig(ListenerConfig::class)
        Kirk.drive {
            to(url)
            element("#header").shouldHave(text("Kirk"))
        }
    }
}

interface ListenerConfig : Configuration{
    @Config.DefaultValue("com.automation.remarks.kirk.test.ListenerFromClassPath")
    override fun listenerClass(): String
}

class ListenerFromClassPath : AbstractKirkEventListener() {
    override fun onStart() {
        print("Start from classpath listener")
    }
}

class LoggerListenerKirk : KirkEventListener {
    override fun beforeQuit() {}

    override fun onFail(exception: Exception) = println("Epic Fail")

    override fun beforeElementLocation(by: By, driver: WebDriver) = println("$by")

    override fun afterNavigation(url: String, driver: WebDriver) {}

    override fun onStart() = println("On start")

    override fun beforeNavigation(url: String, driver: WebDriver) = println("Navigation to $url")
}