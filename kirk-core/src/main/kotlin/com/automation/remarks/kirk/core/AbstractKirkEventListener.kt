package com.automation.remarks.kirk.core

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

/**
 * Created by sergey on 30.07.17.
 */
open class AbstractKirkEventListener : KirkEventListener {
    override fun beforeQuit() {}

    override fun onFail(exception: Exception) {}

    override fun afterNavigation(url: String, driver: WebDriver) {}

    override fun beforeElementLocation(by: By, driver: WebDriver) {}

    override fun beforeNavigation(url: String, driver: WebDriver) {}

    override fun onStart() {}
}